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

public class ItemProfitMargin extends AppCompatActivity{

    TextView btotal , stotal, prof;
    String ItemCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_profit_margin);

        btotal = findViewById(R.id.bprice_total);
        stotal = findViewById(R.id.sprice_total);
        prof = findViewById(R.id.prof);


//        try {
//            Bundle bundle = getIntent().getExtras();
//            ItemCode = bundle.getString("ItemCode");
//            Log.d("workflow", "get item code in earlier activity " + ItemCode);
//            Log.i("TAG", "Thread ID " + Thread.currentThread().getId());
//            loadItem(ItemCode);
//        } catch (Exception e) {
//            finish();
//        }

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

//    private void loadItem(String itemCode) {
//
//        new Thread(() -> {
//            Log.i("TAG", "Thread ID " + Thread.currentThread().getId());
//            Log.d("workflow", "loadItem initiated");
//            DBHelper db = new DBHelper(getApplicationContext());
//            Cursor x = db.readOneItem(itemCode);
//            Log.d("workflow", "get row to cursor");
//            if (cursor.getCount() == 0) {
//                Log.d("workflow", "No items");
//            }
//            else {
//                if (cursor.moveToFirst()) {
//                    icode.setText(cursor.getString(0));
//                    ibrand.setText(cursor.getString(1));
//                    iname.setText(cursor.getString(2));
//                    icount.setText(cursor.getString(3));
//                    ibuy.setText(cursor.getString(4));
//                    isell.setText(cursor.getString(5));
//                    idesc.setText(cursor.getString(6));
//
//                }
//            }
//
//        }).start();
//    }

}
