package com.example.mobileapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
    TextInputEditText etrouteid,etstartloc,etendloc,etdistance,etcreateddate,etmodifieddate,etcxcount;

    String rid,startloc,endloc,distance,created,modified,issetdefault,cxcount;
    String setStatusMsg,issetasdefault="0";

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
        etcxcount=findViewById(R.id.view_cxcount);
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
            cxcount=getIntent().getStringExtra("cxcount");



            //  Log.d("mvalies",rid);
            etrouteid.setText(rid);
            etstartloc.setText(startloc);
            etendloc.setText(endloc);
            etdistance.setText(distance);
            etcreateddate.setText(created);
            etmodifieddate.setText(modified);
            etcxcount.setText(cxcount);

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



            int val = dbHelper.updateRoute(etrouteid.getText().toString(), etstartloc.getText().toString(),
                    etendloc.getText().toString(),
                    Float.parseFloat(etdistance.getText().toString()),issetasdefault);



            if (val == -1) {
                setStatusMsg = getString(R.string.msg_route_update_unsuccesfull);
            }
            else {
                setStatusMsg = getString(R.string.msg_route_update_succesfull);
            }

            Intent intent = new Intent(this, Routes.class).putExtra("status", setStatusMsg);
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

            //  Toast.makeText(this, "Default Route Changed Succesfully", Toast.LENGTH_SHORT).show();
            //try to have a notification
            issetasdefault="1";
        }
        else{
            issetasdefault="0";
        }
    }

    private boolean CheckAllFields() {
        Log.d("workflow","Modify Route CheckAllFields  method  Called");

        //if values are changed pls change in add route as well
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

    public void deleteRoute(View view){
        if(Integer.parseInt(cxcount)==0) {
            confirmDialog();
            Log.d("workflow", "Modify Route deleteRoute  method  Called");
        }
        else{
            errorDialog();
        }

    }

    private void errorDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.msg_oops));
        builder.setMessage((getString(R.string.label_route))
                +" "+
                rid
                +" "+
                (getString(R.string.msg_unable_delete))
                +"."+
                (getString(R.string.msg_retry_delete)));
        builder.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    void confirmDialog() {

        Log.d("workflow","Modify Route confirmDialog  method  Called");
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.msg_are_u_sure));
        builder.setMessage((getString(R.string.msg_confirm_delete))
                +" "+
                (getString(R.string.label_route))
                +" "+
                rid
                +" ? "+
                (getString(R.string.msg_confirm_delete_canot_be_undone)));
        builder.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHelper dbHelper=new DBHelper(ModifyRoute.this);
                        int val= dbHelper.deleteRoute(etrouteid.getText().toString());
                        if (val == 1) {
                            setStatusMsg = getString(R.string.msg_route_delete_succesfull);

                        }
                        else {
                            setStatusMsg = getString(R.string.msg_route_delete_unsuccesfull);

                        }
                        Intent intent = new Intent(ModifyRoute.this,Routes.class).putExtra("status", setStatusMsg);
                        startActivity(intent);
                        Log.i("BTN Click", "Add route Confirmation button clicked");


                    }

                }

        );


        builder.setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}