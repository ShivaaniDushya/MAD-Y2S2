package com.example.mobileapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Items extends AppCompatActivity {

      RecyclerView recyclerView;
      FloatingActionButton fab2;
      ImageView empty_imageview;
      TextView no_data2;

      DBHelper db;
      ArrayList<String> Item_Code, Item_Name,Item_Brand,Item_Count,BuyPrice_Item,SellPrice_Item,Item_Descrip;
      ItemAdapter itemAdapter;
    MaterialToolbar materialToolbar;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        Log.d("workflow","Items on_create method Called");

        recyclerView=findViewById(R.id.recyclerView3);
        empty_imageview=findViewById(R.id.empty_image2);
        no_data2 = findViewById(R.id.no_data2);


        db = new DBHelper(Items.this);

        Item_Code = new ArrayList<>();
        Item_Name = new ArrayList<>();
        Item_Brand = new ArrayList<>();
        Item_Count = new ArrayList<>();
        BuyPrice_Item = new ArrayList<>();
        SellPrice_Item = new ArrayList<>();
        Item_Descrip = new ArrayList<>();


        storeDataInArrays_Items();
        Log.d("workflow","Items storeDataInArrays_Items method Called");

        itemAdapter = new ItemAdapter(Items.this,Items.this, Item_Code,
                Item_Name,
                Item_Brand,
                Item_Count,
                BuyPrice_Item,
                SellPrice_Item,
                Item_Descrip
                );


        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Items.this));

        materialToolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(materialToolbar);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.items);


        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
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
        });


        fab2 = findViewById(R.id.btn_add_item);

        fab2.setOnClickListener(view -> {
            openActivityCreateItem();
            Log.d("workflow","Items Float Button Clicked");
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(getApplicationContext()
                    , com.example.mobileapplication.Settings.class));
            overridePendingTransition(0, 0);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1){
            recreate();    //refresh if data not fetched
            Log.d("workflow","Items onActivityResult method Called");
        }
    }


    private void storeDataInArrays_Items() {
        Log.d("workflow","Items storeDataInArrays method Called");
        Cursor cursor=db.readAllItems();
        if(cursor.getCount()==0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data2.setVisibility(View.VISIBLE);
        }
        else
        {
            while(cursor.moveToNext()){
                Item_Code.add(cursor.getString(0));
                Item_Name.add(cursor.getString(1));
                Item_Brand.add(cursor.getString(2));
                Item_Count.add(cursor.getString(3));
                BuyPrice_Item.add(cursor.getString(4));
                SellPrice_Item.add(cursor.getString(5));
                Item_Descrip.add(cursor.getString(6));

            }
            empty_imageview.setVisibility(View.GONE);
            no_data2.setVisibility(View.GONE);
        }
    }


    public void openActivityCreateItem() {
        Intent intent = new Intent(this, Create_item_Activity.class);
        startActivity(intent);
    }


}