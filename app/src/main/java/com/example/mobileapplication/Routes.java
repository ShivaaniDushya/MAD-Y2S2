package com.example.mobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Routes extends AppCompatActivity {
    private ImageButton imageButton;
    private Button button;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.routes);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.items:
                        startActivity(new Intent(getApplicationContext()
                                , Items.class));
                        overridePendingTransition(0, 0);
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

                return false;
            }
        });

        imageButton= findViewById(R.id.btn_view_route);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewRoute();
            }
        });
        //start code for Add new route button
        //pls replace below code after float button in UI

        button = (Button) findViewById(R.id.btn_add_new_route);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddNewRoute();
            }
        });

    }   //close the tag pls place over this any code related to on start

    //function to load the add route page.

    public void openAddNewRoute() {
        Intent intent = new Intent(this, AddRoute.class);
        startActivity(intent);
        Log.i("Lifecycle", "Add route button clicked");
    }

    //finish code for Add new Route


    //start code for view route button

    public void openViewRoute()
    {
        Intent intent = new Intent(this,ModifyRoute.class);
        startActivity(intent);
        Log.i("Lifecycle","Add route button clicked");
    }

    //finish code for Add new Route
}



