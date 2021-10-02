package com.example.mobileapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
//import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public final static String CHANNEL_ID = "Channel1";

    final Calendar myCalendar = Calendar.getInstance();

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

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        deldate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddSalesOrder.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                pAdapter.calculateTotal(balance);
                hideSoftKeyboard(acproduct);
                acproduct.setText("");
                acproduct.setAdapter(null);
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

            for (int i = 0; i < productList.size(); i++) {
                Product pro = productList.get(i);
                dbHelper.addSalesOrderItem(val,pro.getItemCode(),pro.getQty(),Float.toString(pro.getPrice()));
            }

            Toast.makeText(this, "Record added successfully", Toast.LENGTH_SHORT).show();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = getString(R.string.channel_name);
                String description = getString(R.string.channel_description);
                int importance = NotificationManager.IMPORTANCE_DEFAULT;

                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);

                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }

            Intent intent = new Intent(this, Sales.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            NotificationCompat.Builder builder = new
                    NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Sales Order")
                    .setContentText("New sales order created. Tap to view.")

                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, builder.build());

            Intent intent1 = new Intent(this, Sales.class);
            startActivity(intent1);
            Log.d("workflow","Navigated to sales page");

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String datePattern = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern, Locale.getDefault());

        deldate.setText(sdf.format(myCalendar.getTime()));
    }
}