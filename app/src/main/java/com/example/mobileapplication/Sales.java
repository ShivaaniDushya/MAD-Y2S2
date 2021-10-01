package com.example.mobileapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Sales extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    ImageView empty_imageview;
    TextView no_data;
    Bundle bundle;

    DBHelper db;
    ArrayList<String> inv_id, cus_id, balance, del_date, created_date, isurgent;
    SalesOrderAdapter salesAdapter;
    MaterialToolbar materialToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        try {
            bundle = getIntent().getExtras();
            String getstatus = bundle.getString("status");
            Snackbar snackbar = Snackbar.make(findViewById(R.id.salesLayout), getstatus, Snackbar.LENGTH_LONG);
            snackbar.setAction("OKAY", v -> snackbar.dismiss());
            snackbar.show();
        } catch (Exception ignore) { }

        Log.d("workflow","Sales onCreate method called");

        recyclerView=findViewById(R.id.so_list);
        empty_imageview=findViewById(R.id.empty_image);
        no_data = findViewById(R.id.no_so);

        db =new DBHelper(this);
        inv_id = new ArrayList<>();
        balance = new ArrayList<>();
        cus_id = new ArrayList<>();
        del_date = new ArrayList<>();
        created_date = new ArrayList<>();
        isurgent = new ArrayList<>();

        storeDataInArrays();
        Log.d("workflow","Sales storeDataInArrays method called");

        salesAdapter = new SalesOrderAdapter(this,this,inv_id,
                cus_id,
                inv_id,
                balance,
                del_date,
                created_date,
                isurgent);

        recyclerView.setAdapter(salesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        materialToolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(materialToolbar);

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

        //Code for Add new sales order button

        fab = findViewById(R.id.btn_add_so);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                addSalesOrder();
                Log.d("workflow","Add sales order button clicked");
            }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1){
            recreate();    //refresh if data not fetched
            Log.d("workflow","Sales onActivityResult method called");
        }
    }

    private void storeDataInArrays() {
        Log.d("workflow","Sales storeDataInArrays method called");
        Cursor cursor=db.readAllSalesOrders();
        if(cursor.getCount()==0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }
        else
        {
            while(cursor.moveToNext()){
                inv_id.add(cursor.getString(0));
                cus_id.add(cursor.getString(1));
                balance.add(cursor.getString(2));
                del_date.add(cursor.getString(3));
                created_date.add(cursor.getString(4));
                isurgent.add(cursor.getString(5));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }


    //function to navigate to add sales order page

    public void addSalesOrder() {
        Log.d("workflow","Sales addSalesOrder method called");
        Intent intent = new Intent(this, AddSalesOrder.class);
        startActivity(intent);
        Log.i("Lifecycle", "Add sales order button clicked");
    }


    //function to navigate to update payment page

    public void updatePayment()
    {
        Log.i("workflow","Update Payment button clicked");
        Intent intent = new Intent(this,UpdatePayment.class);
        startActivity(intent);
        Log.d("workflow","Sales updatePayment method called");
    }

}