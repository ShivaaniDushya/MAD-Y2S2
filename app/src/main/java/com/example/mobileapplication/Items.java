package com.example.mobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Items extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        RecyclerView recyclerView;

        View view_item_btn;
        FloatingActionButton create_item_btn;
        ImageButton imgbtn;

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.items);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.items:
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


        //recylcler view
        recyclerView = findViewById(R.id.recyclerViewItem);


        //create button
        create_item_btn = (FloatingActionButton) findViewById(R.id.create_item_button);
        create_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityCreateItem();
            }
        });

        //"View an Item" button
        view_item_btn = (View) findViewById(R.id.view_action1);
        view_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityViewItem();
            }

        });

        //edit pencil icon
        imgbtn = (ImageButton) findViewById(R.id.imageButton);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityEditItem();
            }
        });
    }

    public void openActivityCreateItem() {
        Intent intent = new Intent(this, Create_item_Activity.class);
        startActivity(intent);
    }

    private void openActivityViewItem() {
        Intent intent = new Intent(this, View_item_Activity.class);
        startActivity(intent);
    }

    public void openActivityEditItem() {
        Intent intent = new Intent(this, Edit_item_Activity.class);
        startActivity(intent);
    }


}