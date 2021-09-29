package com.example.mobileapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;


import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;


public class AddRoute extends AppCompatActivity {
    EditText etstartloc,etendloc,etdistance;
    boolean isfieldsvalidated=false;  //check all field validations
    Switch aSwitch;
    String setStatusMsg,issetasdefault="0";
    TextView bdistance;
    Calculations calc=new Calculations();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        Log.d("workflow","Add Route onCreate  method  Called");
        etstartloc=findViewById(R.id.inp_startloc);
        etendloc=findViewById(R.id.inp_endloc);
        etdistance=findViewById(R.id.inp_distance);
        aSwitch=findViewById(R.id.switch_mod);
        bdistance=findViewById(R.id.setdistance);
        bdistance.setVisibility(View.GONE);

        etdistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if (start>count) {
                    double entdist, fuelamount ;
                    bdistance.setVisibility(View.VISIBLE);
                    bdistance.setText(charSequence.toString());
                    entdist = Double.parseDouble(charSequence.toString());

                    fuelamount=calc.calcfuel(entdist);
                    bdistance.setText(getString(R.string.label_to_travel) +
                            " " +
                            charSequence.toString() +
                            " " +
                            getString(R.string.label_you_need) +
                            " " +
                            (String.valueOf(fuelamount)) +
                            " " +
                            getString(R.string.label_fuel_need));
                    Log.d("TXTchange","start:"+start);
                    Log.d("TXTchange","count:"+count);
                    Log.d("TXTchange","after:"+after);
                    //To travel 50Kms you will need 12 literes of fuel on average.*/
                }
                else
                    bdistance.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        aSwitch.setOnCheckedChangeListener((compoundButton, b) ->
                issetasdefault="1"
        );

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.routes);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.items:
                        startActivity(new Intent(getApplicationContext() , Items.class));

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
                        return true;

                    case R.id.sales:
                        startActivity(new Intent(getApplicationContext()
                                , Sales.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                Log.d("workflow","Add Route Bottom Navigation  method  Called");
                return false;
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addRoute(View view) {
        Log.d("workflow","Add Route addRoute  method  Called");
        isfieldsvalidated = CheckAllFields();

        if (isfieldsvalidated) {
            DBHelper dbHelper = new DBHelper(this);
            removedefault(issetasdefault);

            long val = dbHelper.addRoutes(etstartloc.getText().toString(),
                    etendloc.getText().toString(),
                    Float.parseFloat(etdistance.getText().toString()),issetasdefault);

            if (val == -1) {
                setStatusMsg = getString(R.string.msg_route_add_unsuccesfull);
            }
            else {
                setStatusMsg = getString(R.string.msg_route_add_succesfull);
            }

            Intent intent = new Intent(this, Routes.class).putExtra("status", setStatusMsg);
            startActivity(intent);
            Log.i("BTN Click", "Add route Confirmation button clicked");
        }
    }

    public void removedefault(String issetasdefault) {
        Log.d("workflow","Add Route removedefault  method  Called");
        if(Integer.parseInt(issetasdefault)==1){
            DBHelper dbHelper = new DBHelper(this);
            int res=dbHelper.update_def_route_on_create();
            // Toast.makeText(this, "Default Route Changed Succesfully", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean CheckAllFields() {

        //if values are changed pls change in modify route as well

        int maxchar=10;
        double maxdistance=999.99;

        Log.d("workflow","Add Route CheckAllFields  method  Called");
        if (etstartloc.length() == 0) {
            etstartloc.setError(getString(R.string.error_msg_mandatory));
            return false;
        }

        if (etendloc.length() == 0) {
            etendloc.setError(getString(R.string.error_msg_mandatory));
            return false;
        }

        if (etdistance.length() == 0) {
            etdistance.setError(getString(R.string.error_msg_mandatory));
            return false;
        }
        if (etstartloc.length() > maxchar) {

            etstartloc.setError(getString(R.string.error_msg_max_characters)+" "+maxchar);

            return false;
        }

        if (etendloc.length() > maxchar) {
            etendloc.setError(getString(R.string.error_msg_max_characters)+" "+maxchar);
            return false;
        }

        if (Double.parseDouble(String.valueOf(etdistance.getText())) > maxdistance) {
            etdistance.setError(getString(R.string.error_msg_max_distance)+" "+maxdistance+" "+getString(R.string.label_unit_distance));
            return false;
        }

        return true;

    }

}
