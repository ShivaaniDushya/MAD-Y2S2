package com.example.mobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Customers extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_customer;
    CustomerAdapter customerAdapter;
    TextView no_customer_text;
    ImageView no_customer;
    Bundle bundle;
    MaterialToolbar materialToolbar;

    DBHelper db;
    ArrayList<String> customer_id, customer_name, store_name, address, pp_img_uri, sp_img_uri;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        try {
            bundle = getIntent().getExtras();
            String addCustomerStatus = bundle.getString("passMessage");
            Snackbar snackbar = Snackbar.make(findViewById(R.id.routesLayout), addCustomerStatus, Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(getString(R.string.btn_ok), v -> snackbar.dismiss());
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.show();
        } catch (Exception ignore) {
        }

        recyclerView = findViewById(R.id.recycleView);
        no_customer = findViewById(R.id.no_customer);
        no_customer_text = findViewById(R.id.no_customer_text);

        db = new DBHelper(Customers.this);
        customer_id = new ArrayList<>();
        customer_name = new ArrayList<>();
        store_name = new ArrayList<>();
        address = new ArrayList<>();
        pp_img_uri = new ArrayList<>();
        sp_img_uri = new ArrayList<>();

        Log.i("TAG", "Thread ID " + Thread.currentThread().getId());
        storeCustomerDataInArray();

        customerAdapter = new CustomerAdapter(Customers.this, customer_id, customer_name, store_name, address, pp_img_uri, sp_img_uri);
        recyclerView.setAdapter(customerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Customers.this));

        materialToolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(materialToolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.customers);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.items:
                    startActivity(new Intent(getApplicationContext()
                            , Items.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.customers:
                    return true;

                case R.id.home:
                    startActivity(new Intent(getApplicationContext()
                            , MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.routes:
                    startActivity(new Intent(getApplicationContext()
                            , Routes.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.sales:
                    startActivity(new Intent(getApplicationContext()
                            , Sales.class));
                    overridePendingTransition(0, 0);
                    return true;
            }

            return false;
        });

        add_customer = findViewById(R.id.add_customer);
        add_customer.setOnClickListener(v -> {
            Intent intent = new Intent(Customers.this, AddCustomer.class);
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(getApplicationContext()
                    , com.example.mobileapplication.Settings.class));
            overridePendingTransition(0, 0);
        }
        return true;
    }

    public void storeCustomerDataInArray() {
        new Thread(() -> {
            Log.i("TAG", "Thread ID " + Thread.currentThread().getId());
            Cursor cursor = db.readAllCustomers();
            if (cursor.getCount() == 0) {
                Log.d("workflow", "No customers");
            } else {
                no_customer.setVisibility(View.GONE);
                no_customer_text.setVisibility(View.GONE);
                while (cursor.moveToNext()) {
                    customer_id.add(cursor.getString(0));
                    customer_name.add(cursor.getString(1));
                    store_name.add(cursor.getString(2));
                    address.add(cursor.getString(2) + " " + getString(R.string.owned) + " " + cursor.getString(1) + " " + getString(R.string.located) + " " + cursor.getString(3) + " " + getString(R.string.in) + " " + cursor.getString(4) + " " + getString(R.string.hint_cus_city));
                    pp_img_uri.add(cursor.getString(5));
                    sp_img_uri.add(cursor.getString(6));
                }
            }

        }).start();
    }
}