package com.example.mobileapplication;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;


public class ItemCalculation extends AppCompatActivity {

    TextInputEditText ibuypr, isellpr, itcount, icost;
    TextView profit;
    Button profitbtn;
    Calculations calculations;


    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_cal);

        ibuypr = findViewById(R.id.ibuyp);
        isellpr = findViewById(R.id.isellp);
        itcount = findViewById(R.id.icount);
        icost = findViewById(R.id.cost);
        profit = findViewById(R.id.expected_prof_amount);
        profitbtn = findViewById(R.id.Expected_profit);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.items);


        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.items:
                    return true;

                case R.id.customers:
                    startActivity(new Intent(getApplicationContext()
                            , Customers.class));
                    overridePendingTransition(0, 0);
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
            Log.d("workflow", "Add Item Bottom Navigation  method  Called");
            return false;
        });

        profitbtn.setOnClickListener(v -> calculateExpectedProfit());

    }

    private void calculateExpectedProfit() {

        try {
        double buy = Double.parseDouble(Objects.requireNonNull(ibuypr.getText()).toString());
        double sell = Double.parseDouble(Objects.requireNonNull(isellpr.getText()).toString());
        double count = Double.parseDouble(Objects.requireNonNull(itcount.getText()).toString());
        double cost = Double.parseDouble(Objects.requireNonNull(icost.getText()).toString());
        double profitex = calculations.calculateExpectedProfit(buy, sell, count, cost);

        profit.setText(String.valueOf(profitex));

    }catch (Exception e) {
        Toast.makeText(this, "Please add all mandatory fields", Toast.LENGTH_SHORT).show();
    }

    }


//    protected void calculateExpectedProfit() {
//

//
//    }


}
