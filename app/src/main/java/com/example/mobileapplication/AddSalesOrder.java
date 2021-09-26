package com.example.mobileapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
//import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AddSalesOrder extends AppCompatActivity {

    AutoCompleteTextView cusid;
    //String cusname;
    //TextView cusText = (TextView) cusid.getSelectedView();
    EditText deldate;
    Switch urgswitch;
    boolean isfieldsvalidated=false;
    String isurgent = "No";
    TextView balance;
    float totamount = 0;

    private List<Product> productList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProductAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales_order);

        Log.d("workflow","SalesOrder onCreate method called");
        cusid = findViewById(R.id.cusSpinner);
        deldate = findViewById(R.id.delDate);
        balance = findViewById(R.id.totamount);
        urgswitch = findViewById(R.id.urgswitch);

        balance.setText(String.valueOf(totamount));

        urgswitch.setOnCheckedChangeListener((compoundButton, b) ->
                isurgent="Yes"
        );

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

        AutoCompleteTextView cusSpinner = (AutoCompleteTextView) findViewById(R.id.cusSpinner);

        DBHelper db = new DBHelper(getApplicationContext());
        List<String> Customers = db.getAllCustomers();
        Log.d("workflow","getAllCustomers method called");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Customers);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cusSpinner.setAdapter(dataAdapter);

        AutoCompleteTextView acproduct = (AutoCompleteTextView)findViewById(R.id.acproduct);

        //ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Products);
        //acproduct.setAdapter(productAdapter);

        acproduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //if (s.toString().contains("@")){
                    List<Product> Products = db.getAllProducts(s.toString());
                    TextProductAdapter textUserAdapter = new TextProductAdapter(getApplicationContext(), Products);
                    acproduct.setAdapter(textUserAdapter);
                //}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        acproduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = (Product) adapterView.getItemAtPosition(i);
                acproduct.setText(product.getItemName());

                product.setQty(1);
                productList.add(product);
                pAdapter.notifyDataSetChanged();

                hideSoftKeyboard(acproduct);
                acproduct.setText("");
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.itemList);

        pAdapter = new ProductAdapter(productList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);

    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addSalesOrder(View view) {
        Log.d("workflow","addSalesOrder method called");
        isfieldsvalidated = CheckAllFields();

        if (isfieldsvalidated) {
            DBHelper dbHelper = new DBHelper(this);

            long val = dbHelper.addSalesOrder(
                    Integer.parseInt(cusid.getText().toString()),
                    deldate.getText().toString(),
                    balance.getText().toString(),
                    balance.getText().toString(),
                    isurgent
            );

            Toast.makeText(this, "Record added successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ViewSalesOrders.class);
            startActivity(intent);
            Log.i("BTN Click", "addSalesOrderConfirmation button clicked");

        }
    }

    private boolean CheckAllFields() {

        Log.d("workflow","addSalesOrder CheckAllFields method called");
        if (cusid == null) {
            return false;
        }

        if (deldate.length() == 0) {
            deldate.setError(getString(R.string.error_msg_mandatory));
            return false;
        }

        return true;

    }
}