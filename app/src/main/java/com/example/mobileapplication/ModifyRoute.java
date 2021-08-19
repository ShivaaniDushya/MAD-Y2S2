package com.example.mobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ModifyRoute extends AppCompatActivity {
    EditText etrouteid,etstartloc,etendloc,etdistance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_route);

        etrouteid=findViewById(R.id.inp_rid);
        etstartloc=findViewById(R.id.inp_startlocup);
        etendloc=findViewById(R.id.inp_endlocup);
        etdistance=findViewById(R.id.inp_distanceup);

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

                return false;
            }
        });

    }
    public void updateRoute(View view){
        DBHelper dbHelper=new DBHelper(this);

        int val=dbHelper.updateRoute(etrouteid.getText().toString(),etstartloc.getText().toString(),
                etendloc.getText().toString(),
                Float.parseFloat(etdistance.getText().toString()));

        Toast.makeText(this, "Record Updated Succesfully", Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(this, Routes.class);
        startActivity(intent);
        Log.i("BTN Click", "Update route Confirmation button clicked");
    }

    public void deleteRoute(View view){
        DBHelper dbHelper=new DBHelper(this);

         dbHelper.deleteRoute(etrouteid.getText().toString());

        Toast.makeText(this, "Record Deleted Succesfully", Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(this, Routes.class);
        startActivity(intent);
        Log.i("BTN Click", "Delete route Confirmation button clicked");
    }
}
