package com.example.mobileapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.material.textfield.TextInputEditText;

public class ModifyRoute extends AppCompatActivity {
    TextInputEditText etrouteid,etstartloc,etendloc,etdistance,etcreateddate,etmodifieddate;

    String rid,startloc,endloc,distance,created,modified,issetdefault;
    String issetasdefault="0";

    boolean isfieldsvalidated=false;

    Switch aSwitch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_route);
        Log.d("workflow","Modify Route onCreate method  Called");
        etrouteid=findViewById(R.id.inp_rid1);
        etstartloc=findViewById(R.id.inp_startlocup1);
        etendloc=findViewById(R.id.inp_endlocup1);
        etdistance=findViewById(R.id.inp_distanceup1);
        etcreateddate =findViewById(R.id.inp_created1);
        etmodifieddate=findViewById(R.id.inp_modified1);
        aSwitch=findViewById(R.id.switch_mod);




        getAndSetIntentData();




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
                Log.d("workflow","Modify Route Bottom Nav method  Called");
                return false;
            }
        });

    }




    void getAndSetIntentData() {

        Intent intent=new Intent();

        Log.d("workflow","Modify Route getAndSetIntentData  method  Called");

        if(getIntent().hasExtra("rid") &&
                getIntent().hasExtra("startloc") &&
                getIntent().hasExtra("endloc") &&
                getIntent().hasExtra("distance"))// && getIntent().hasExtra("created") && getIntent().hasExtra("modified")
        {
            rid = getIntent().getStringExtra("rid");
            startloc = getIntent().getStringExtra("startloc");
            endloc = getIntent().getStringExtra("endloc");
            distance = getIntent().getStringExtra("distance");
            created = getIntent().getStringExtra("created");
            modified = getIntent().getStringExtra("modified");
            issetdefault=getIntent().getStringExtra("isdefaullt");

          //  Log.d("mvalies",rid);
            etrouteid.setText(rid);
            etstartloc.setText(startloc);
            etendloc.setText(endloc);
            etdistance.setText(distance);
            etcreateddate.setText(created);
            etmodifieddate.setText(modified);

            if(Integer.parseInt(issetdefault)==1){
                aSwitch.setChecked(true);
            }
        }
        else{
            Toast.makeText(this, "No data Available", Toast.LENGTH_SHORT).show();
        }
   }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateRoute(View view) {


        isfieldsvalidated = CheckAllFields();
        Log.d("workflow","Modify Route updateRoute  method  Called");
        removedefault();
        Log.d("workflow",issetasdefault);
        if (isfieldsvalidated) {

            DBHelper dbHelper = new DBHelper(this);

         //   int val1=dbHelper.update_def_route();

            int val = dbHelper.updateRoute(etrouteid.getText().toString(), etstartloc.getText().toString(),
                    etendloc.getText().toString(),
                    Float.parseFloat(etdistance.getText().toString()),issetasdefault);

            Toast.makeText(this, "Record Updated Succesfully ", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, Routes.class);
            startActivity(intent);
            Log.i("BTN Click", "Update route Confirmation button clicked");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void removedefault() {
        Log.d("workflow","UPDATE Route removedefault  method  Called");
        if(aSwitch.isChecked()){
            DBHelper dbHelper = new DBHelper(this);
            int res;
            res = dbHelper.update_def_route();
            Toast.makeText(this, "Default Route Changed Succesfully", Toast.LENGTH_SHORT).show();
            issetasdefault="1";
        }
        else{
            issetasdefault="0";
        }
    }

    private boolean CheckAllFields() {
        Log.d("workflow","Modify Route CheckAllFields  method  Called");
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

    public void deleteRoute(View view){

        confirmDialog();
        Log.d("workflow","Modify Route deleteRoute  method  Called");
    }

     void confirmDialog() {

         Log.d("workflow","Modify Route confirmDialog  method  Called");
         AlertDialog.Builder builder=new AlertDialog.Builder(this);
         builder.setTitle("Are You Sure?");
         builder.setMessage("Do you really want to delete this Route " +rid+" ?This Process cannot be undone.");
         builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 DBHelper dbHelper=new DBHelper(ModifyRoute.this);
                 dbHelper.deleteRoute(etrouteid.getText().toString());
                 finish();
             }
         });
         builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {

             }
         });
        builder.create().show();
    }
}