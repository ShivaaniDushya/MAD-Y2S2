package com.example.mobileapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class View_item_Activity extends AppCompatActivity {

    TextView icode,ibrand, iname, icount, ibuy, isell, idesc;
    Bundle bundle;
    DBHelper db;
    String ItemCode;
    Cursor cursor;
    Button btGenerate;
    ImageView ivOutput;
    String itemCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        icode = findViewById(R.id.item_code_view);
        ibrand = findViewById(R.id.item_brand_view);
        iname = findViewById(R.id.item_name_view);
        icount = findViewById(R.id.item_count_view);
        ibuy = findViewById(R.id.item_buyprice_view);
        isell = findViewById(R.id.item_sellprice_view);
        idesc = findViewById(R.id.item_description_view);
        btGenerate = findViewById(R.id.bt_generate);
        ivOutput = findViewById(R.id.iv_output);

        btGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = icode.getText().toString().trim() + " , " +
                        iname.getText().toString().trim() + " , " +
                        ibrand.getText().toString().trim() + " , " +
                        icount.getText().toString().trim() + " , " +
                        ibuy.getText().toString().trim() + " , " +
                        isell.getText().toString().trim() + " , " +
                        idesc.getText().toString().trim()  ;

//                String text = iname.getText().toString().trim();

                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 350, 350);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    ivOutput.setImageBitmap(bitmap);
//                    InputMethodManager manager = (InputMethodManager) getSystemService(
//                            Context.INPUT_METHOD_SERVICE
//                    );
//                    manager.hideSoftInputFromWindow(iname.getApplicationWindowToken(), 0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });



        try {
            bundle = getIntent().getExtras();
            ItemCode = bundle.getString("ItemCode");
            Log.d("workflow", "get item code in earlier activity " + ItemCode);
            Log.i("TAG", "Thread ID " + Thread.currentThread().getId());
            loadItem(ItemCode);
        } catch (Exception e) {
            finish();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.items);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.items:
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
        });

    }

    private void loadItem(String itemCode) {
        new Thread(() -> {
            Log.i("TAG", "Thread ID " + Thread.currentThread().getId());
            Log.d("workflow", "loadItem initiated");
            db = new DBHelper(getApplicationContext());
            cursor = db.readOneItem(itemCode);
            Log.d("workflow", "get row to cursor");
            if (cursor.getCount() == 0) {
                Log.d("workflow", "No items");
            }
            else {
                if (cursor.moveToFirst()) {
                    icode.setText(cursor.getString(0));
                    ibrand.setText(cursor.getString(1));
                    iname.setText(cursor.getString(2));
                    icount.setText(cursor.getString(3));
                    ibuy.setText(cursor.getString(4));
                    isell.setText(cursor.getString(5));
                    idesc.setText(cursor.getString(6));

                }
            }

        }).start();
    }

}
