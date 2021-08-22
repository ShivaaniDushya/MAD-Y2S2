package com.example.mobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Sales extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

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


    }

    public void addSalesOrder(View view) {
        Intent intent = new Intent(Sales.this,AddSalesOrder.class);
        startActivity(intent);
    }

    public void viewSalesOrders(View view) {
        Intent intent = new Intent(Sales.this,ViewSalesOrders.class);
        startActivity(intent);
    }

    public void UpdatePayment(View view) {
        Intent intent = new Intent(Sales.this,UpdatePayment.class);
        startActivity(intent);
    }

    public void SalesSummary(View view) {
        Intent intent = new Intent(Sales.this,SalesSummary.class);
        startActivity(intent);
    }
}