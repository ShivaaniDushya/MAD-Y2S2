package com.example.mobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class ViewCustomer extends AppCompatActivity {
    Bundle bundle;
    DBHelper db;
    TextView customer_id, customer_name, store_name, mobile, address, created_date, modified_date, modified_date_t, route;
    MaterialButton delBtn, editBtn;
    ShapeableImageView storePPImg;
    ImageView storeSPImg;
    String customerID;
    Cursor cursor;
    MaterialToolbar topAppBar;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);

        customer_id = findViewById(R.id.customer_id_v);
        customer_name = findViewById(R.id.customer_name_v);
        store_name = findViewById(R.id.store_name_v);
        mobile = findViewById(R.id.mobile_v);
        address = findViewById(R.id.address_v);
        created_date = findViewById(R.id.created_date_v);
        modified_date = findViewById(R.id.modified_date_v);
        modified_date_t = findViewById(R.id.modified_date_vt);
        delBtn = findViewById(R.id.delete_v);
        editBtn = findViewById(R.id.edit_v);
        storeSPImg = findViewById(R.id.store_pic_v);
        storePPImg = findViewById(R.id.customer_pic_v);
        route = findViewById(R.id.route_v);
        topAppBar = findViewById(R.id.topAppBar);

        try {
            bundle = getIntent().getExtras();
            customerID = bundle.getString("CustomerID");
            Log.d("workflow", "get customer id in earlier activity " + customerID);
            Log.i("TAG", "Thread ID " + Thread.currentThread().getId());
            loadCustomer(customerID);
            topAppBar.setTitle("#CID" + customerID);
        } catch (Exception e) {
            finish();
        }

        setSupportActionBar(topAppBar);

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

        delBtn.setOnClickListener(v -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Are you sure?");
            dialogBuilder.setMessage("Do you really want to delete this customer? This Process cannot be undone.");
            dialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
                DBHelper dbHelper = new DBHelper(ViewCustomer.this);
                long result = dbHelper.deleteCustomer(customerID);
                String delMsg;
                if (result < 1) {
                    delMsg = "Customer deleted unsuccessful.";
                } else {
                    delMsg = "Customer deleted successful.";
                }
                Log.d("TAG", "snack started");
                Intent intent = new Intent(ViewCustomer.this, Customers.class)
                        .putExtra("passMessage", delMsg);
                startActivity(intent);
            });
            dialogBuilder.setNegativeButton("No", (dialog, which) -> {

            });
            dialogBuilder.show();
        });

        editBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ViewCustomer.this, AddCustomer.class)
                    .putExtra("CustomerID", customerID);
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

    @SuppressLint("SetTextI18n")
    private void loadCustomer(String customerID) {
        new Thread(() -> {
            Log.i("TAG", "Thread ID " + Thread.currentThread().getId());
            Log.d("workflow", "loadCustomer initiated");
            db = new DBHelper(getApplicationContext());
            cursor = db.readOneCustomer(customerID);
            Log.d("workflow", "get row to cursor");
            if (cursor.getCount() == 0) {
                Log.d("workflow", "No customer");
            } else {
                if (cursor.moveToFirst()) {
                    customer_id.setText(cursor.getString(0));
                    customer_name.setText(cursor.getString(1));
                    store_name.setText(cursor.getString(2));
                    mobile.setText("+94" + cursor.getString(3));
                    address.setText(cursor.getString(4) + ", " + cursor.getString(5));
                    if (cursor.getString(10) != null) {
                        route.setText(cursor.getString(10) + " - " + cursor.getString(11));
                    } else {
                        route.setText("-");
                    }
                    created_date.setText(cursor.getString(6));
                    if (!(String.valueOf(cursor.getString(7)).equals("null"))) {
                        modified_date.setText(cursor.getString(7));
                    } else {
                        modified_date.setVisibility(View.GONE);
                        modified_date_t.setVisibility(View.GONE);
                    }
                    if (!(String.valueOf(cursor.getString(8)).equals("null"))) {
                        storePPImg.setImageURI(Uri.parse(cursor.getString(8)));
                    }
                    if (!(String.valueOf(cursor.getString(9)).equals("null"))) {
                        storeSPImg.setImageURI(Uri.parse(cursor.getString(9)));
                    }
                }
            }

        }).start();
    }
}