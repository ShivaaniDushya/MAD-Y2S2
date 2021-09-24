package com.example.mobileapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UpdatePayment extends AppCompatActivity {

    //AutoCompleteTextView invSpinner = (AutoCompleteTextView) findViewById(R.id.invSpinner);
    AutoCompleteTextView invid;
    TextInputEditText txtbal, txtpay, txtnewbal, txtpaydate;

    String inputinvid, inputpaydate, AddStatusMsg;
    Float inputbal, inputpay, inputnewbal;

    Cursor cursor;

    int PERMISSION_ID = 44;
    int REQUEST_CODE = 0;

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
                    //Float.valueOf(getIntent().getStringExtra("balance"));


            //inputpay = Float.parseFloat(txtpay.getText().toString());
            //inputnewbal = inputbal - inputpay;

            invid.setText(inputinvid);
            txtbal.setText(String.valueOf(inputbal));
            //txtnewbal.setText(String.valueOf(inputnewbal));
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

    public void Discard(View view) {
        Intent intent = new Intent(this, Sales.class);
        startActivity(intent);
    }

}