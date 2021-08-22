package com.example.mobileapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class AddRoute extends AppCompatActivity {
    EditText etstartloc,etendloc,etdistance;
    boolean isfieldsvalidated=false;  //check all field validations
    Switch aSwitch;
    String issetasdefault="0";

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        Log.d("workflow","Add Route onCreate  method  Called");
        etstartloc=findViewById(R.id.inp_startloc);
        etendloc=findViewById(R.id.inp_endloc);
        etdistance=findViewById(R.id.inp_distance);
        aSwitch=findViewById(R.id.switch_mod);

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

            Snackbar.make(view,"Record Added Succesfully", BaseTransientBottomBar.LENGTH_LONG).setAction("OK",null).show();

            Toast.makeText(this, "Record Added Succesfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, Routes.class);
            startActivity(intent);
            Log.i("BTN Click", "Add route Confirmation button clicked");
        }
    }

    private void removedefault(String issetasdefault) {
        Log.d("workflow","Add Route removedefault  method  Called");
        if(Integer.parseInt(issetasdefault)==1){
            DBHelper dbHelper = new DBHelper(this);
            int res=dbHelper.update_def_route_on_create();
            Toast.makeText(this, "Default Route Changed Succesfully", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean CheckAllFields() {
        Log.d("workflow","Add Route CheckAllFields  method  Called");
        if (etstartloc.length() == 0) {
            etstartloc.setError("This field is required");
            return false;
        }

        if (etendloc.length() == 0) {
            etendloc.setError("This field is required");
            return false;
        }

        if (etdistance.length() == 0) {
            etdistance.setError("This field is required");
            return false;
        }
        if (etstartloc.length() > 10) {
            etstartloc.setError("Maximum Characters can be entered is 10");
            return false;
        }

        if (etendloc.length() > 10) {
            etendloc.setError("Maximum Characters can be entered is 10");
            return false;
        }

        if (etdistance.length() > 4) {
            etdistance.setError("Maximum value can be entered is 999");
            return false;
        }

        return true;

    }

   }