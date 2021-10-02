package com.example.mobileapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class UpdatePayment extends AppCompatActivity {

    //AutoCompleteTextView invSpinner = (AutoCompleteTextView) findViewById(R.id.invSpinner);
    AutoCompleteTextView invid;
    TextInputEditText txtbal, txtpay, txtnewbal, txtpaydate;

    String inputinvid, inputpaydate, AddStatusMsg;
    Float inputbal, inputpay, inputnewbal;

    Cursor cursor;

    boolean isfieldsvalidated=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_payment);

        Log.d("workflow", "updatePayment onCreate method called");
        invid = findViewById(R.id.invSpinner);
        txtbal = findViewById(R.id.txtbal);
        txtpay = findViewById(R.id.txtpay);
        txtpaydate = findViewById(R.id.txtpaydate);
        txtnewbal = findViewById(R.id.txtnewbal);

        getPaymentDue();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.sales);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.items:
                        startActivity(new Intent(getApplicationContext()
                                , Items.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.customers:
                        startActivity(new Intent(getApplicationContext()
                                , Customers.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.routes:
                        startActivity(new Intent(getApplicationContext()
                                , Routes.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.sales:
                        startActivity(new Intent(getApplicationContext()
                                , Sales.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        AutoCompleteTextView invSpinner = (AutoCompleteTextView) findViewById(R.id.invSpinner);
        //ArrayList<String> listInvoices = DBHelper.getAllInvoice();

        DBHelper db = new DBHelper(getApplicationContext());
        List<String> Invoices = db.getAllInvoices();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Invoices);

        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        invid.setAdapter(dataAdapter);

        txtnewbal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                inputpay = Float.parseFloat(txtpay.getText().toString());
                inputnewbal = inputbal - inputpay;
                txtnewbal.setText(String.valueOf(inputnewbal));
            }
        });

    }

    void getPaymentDue() {
        Intent intent = new Intent();

        Log.d("workflow", "Payment getPaymentDue method called");

        if (getIntent().hasExtra("inv_id") &&
            getIntent().hasExtra("balance")) {

            inputinvid = getIntent().getStringExtra("inv_id");
            inputbal = Float.parseFloat(getIntent().getStringExtra("balance"));

            invid.setText(inputinvid);
            txtbal.setText(String.valueOf(inputbal));

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addPayment(View view) {
        Log.d("workflow","Payment addPayment method called");
        isfieldsvalidated = CheckAllFields();

        if (isfieldsvalidated) {
            DBHelper dbHelper = new DBHelper(this);

            long val = dbHelper.addPayment(invid.getText().toString(),
                    Float.parseFloat(txtpay.getText().toString()),
                    txtpaydate.getText().toString(),
                    Float.parseFloat(txtnewbal.getText().toString()));

            Log.d("workflow","Payment addPayment method called with values " + invid.getText().toString() +
                    Float.parseFloat(txtpay.getText().toString()) + txtpaydate.getText().toString() +
                    Float.parseFloat(txtnewbal.getText().toString()));

            if (val == -1) {
                //AddStatusMsg = "Payment unsuccessful.";
                Toast.makeText(this, "Payment unsuccessful.", Toast.LENGTH_SHORT).show();
            }
            else {
                //AddStatusMsg = "Payment updated successfully.";
                Toast.makeText(this, "Payment updated successfully.", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(this, Sales.class);
            //.putExtra("passMessage", AddStatusMsg);
            startActivity(intent);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addPaymentAndPrintReceipt(View view) {
        addPayment(view);
        printReceipt(view);
    }

    private boolean CheckAllFields() {

        Log.d("workflow","Payment CheckAllFields method called");
        if (txtpay.length() == 0) {
            txtpay.setError(getString(R.string.error_msg_mandatory));
            return false;
        }

        if (txtpaydate.length() == 0) {
            txtpaydate.setError(getString(R.string.error_msg_mandatory));
            return false;
        }

        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void printReceipt(View view)
    {
        printInvoice(inputinvid, Float.parseFloat(txtpay.getText().toString()), txtpaydate.getText().toString(), txtnewbal.getText().toString());
        Log.d("workflow", "printReceipt method called");
        Log.d("workflow", "parameters " + Float.parseFloat(txtpay.getText().toString()) + txtpaydate.getText().toString() +
                Float.parseFloat(txtnewbal.getText().toString()));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void printInvoice(String invid, Float txtpay, String txtpaydate, String txtnewbal) {
        Log.d("workflow", "printInvoice method called with parameters " + invid + txtpay + txtpaydate + txtnewbal);

        PdfDocument pdfInvoice = new PdfDocument();
        Paint invPaint = new Paint();

        String[] columns = {"Invoice No", "Payment", "Date"};

        PdfDocument.PageInfo invPageInfo = new PdfDocument.PageInfo.Builder(1000, 900, 1).create();
        PdfDocument.Page invPage = pdfInvoice.startPage(invPageInfo);
        Canvas canvas = invPage.getCanvas();

        invPaint.setTextSize(100);
        canvas.drawText("DStock", 30, 100, invPaint);

        invPaint.setTextSize(40);
        canvas.drawText("123, Kollupitiya, Colombo 03", 30, 150, invPaint);

        invPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Invoice No", canvas.getWidth() - 40, 60, invPaint);
        canvas.drawText(invid, canvas.getWidth() - 40, 120, invPaint);

        invPaint.setTextAlign(Paint.Align.LEFT);
        invPaint.setColor(Color.rgb(195, 195, 195));
        canvas.drawRect(30, 170, canvas.getWidth() - 30, 180, invPaint);

        invPaint.setColor(Color.BLACK);
        canvas.drawText("Date", 50, 240, invPaint);
        canvas.drawText(txtpaydate, 450, 255, invPaint);

        canvas.drawText("Payment Amount", 50, 300, invPaint);
        canvas.drawText(txtpay.toString(), 450, 300, invPaint);

        canvas.drawText("Balance Due", 50, 350, invPaint);
        canvas.drawText(txtnewbal, 450, 350, invPaint);

        invPaint.setTextAlign(Paint.Align.LEFT);
        invPaint.setColor(Color.rgb(195, 195, 195));
        canvas.drawRect(30, 400, canvas.getWidth() - 30, 410, invPaint);

        invPaint.setColor(Color.BLACK);
        canvas.drawText("Thank you for the payment", 50, 460, invPaint);

        pdfInvoice.finishPage(invPage);

        String myFilePath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS + "/dstock/").getAbsolutePath() + "invoice.pdf";
        File myFile = new File(myFilePath);
        Log.d("workflow", myFile.getAbsolutePath());
        try {
            pdfInvoice.writeTo(new FileOutputStream(myFile));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        pdfInvoice.close();

        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(UpdatePayment.this, myFilePath);
            printManager.print("Document", printDocumentAdapter, new PrintAttributes.Builder().build());
        } catch (Exception e) {
            Log.d("workflow", "" + e.getMessage());
        }
    }

    public void Discard(View view) {
        Intent intent = new Intent(this, Sales.class);
        startActivity(intent);
    }

}