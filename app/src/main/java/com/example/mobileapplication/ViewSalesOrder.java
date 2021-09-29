package com.example.mobileapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ViewSalesOrder extends AppCompatActivity {

    Bundle bundle;
    DBHelper db;
    TextView invno, cusid, invamt, balamt, deldate;
    String InvNo;
    Cursor cursor;
    Button btGenerate;
    ImageView ivOutput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sales_order);

        invno = findViewById(R.id.txtinv_view);
        cusid = findViewById(R.id.txtcus_view);
        invamt = findViewById(R.id.txtinvamt_view);
        balamt = findViewById(R.id.txtbal_view);
        deldate = findViewById(R.id.txtdeldate_view);

        btGenerate = findViewById(R.id.sales_qrgenerate_btn);
        ivOutput = findViewById(R.id.sales_iv_output);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);


        try {
            bundle = getIntent().getExtras();
            InvNo = bundle.getString("InvNo");
            Log.d("workflow", "Invoice id passed from Sales Orders activity " + InvNo);
            Log.i("TAG", "Thread ID " + Thread.currentThread().getId());
            loadSalesOrder(InvNo);
        } catch (Exception e) {
            finish();
        }


        btGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sText = invno.getText().toString().trim() + ", " +
                        cusid.getText().toString().trim() + ", " +
                        invamt.getText().toString().trim() + ", " +
                        balamt.getText().toString().trim() + ", " +
                        deldate.getText().toString().trim();
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    BitMatrix matrix = writer.encode(sText, BarcodeFormat.QR_CODE, 350, 350);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    ivOutput.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.sales);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.items:
                    startActivity(new Intent(getApplicationContext()
                            , Items.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.customers:
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
        });

    }

    private void loadSalesOrder(String ItemCode) {
        new Thread(() -> {
            Log.i("TAG", "Thread ID " + Thread.currentThread().getId());
            Log.d("workflow", "loadSales initiated");
            db = new DBHelper(getApplicationContext());
            cursor = db.readOneSalesOrder(InvNo);
            Log.d("workflow", "get row to cursor");
            if (cursor.getCount() == 0) {
                Log.d("workflow", "No Item");
            }
            else {
                if (cursor.moveToFirst()) {
                    invno.setText(cursor.getString(0));
                    cusid.setText(cursor.getString(1));
                    invamt.setText(cursor.getString(2));
                    balamt.setText(cursor.getString(3));
                    deldate.setText(cursor.getString(4));
                }
            }

        }).start();
    }
}