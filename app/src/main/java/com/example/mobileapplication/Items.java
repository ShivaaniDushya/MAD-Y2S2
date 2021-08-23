package com.example.mobileapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Items extends AppCompatActivity {


//    DBHelper db;
//    ArrayList<String> Item_Code, Item_Name,Item_Brand,Item_Count,BuyPrice_Item,SellPrice_Item,Item_Descrip;
//    ItemAdapter itemAdapter;
//    private Notification.Builder empty_imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        RecyclerView recyclerView = null;

        View view_item_btn;
        FloatingActionButton create_item_btn;
        ImageButton imgbtn;



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.items);

//        Log.d("workflow","Items on_create method Called");
//
//        db =new DBHelper(this);
//        Item_Code = new ArrayList<>();
//
//        Item_Name = new ArrayList<>();
//        Item_Brand = new ArrayList<>();
//        Item_Count = new ArrayList<>();
//        BuyPrice_Item = new ArrayList<>();
//        SellPrice_Item = new ArrayList<>();
//        Item_Descrip = new ArrayList<>();

//        storeDataInArrays();
//        Log.d("workflow","Items storeDataInArrays method Called");
//
//        itemAdapter = new ItemAdapter(this,this, Item_Code,
//                Item_Name,
//                Item_Brand,
//                Item_Count,
//                BuyPrice_Item,
//                SellPrice_Item,
//                Item_Descrip);
//
//        recyclerView.setAdapter(itemAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1){
            recreate();    //refresh if data not fetched
            Log.d("workflow","Items onActivityResult method Called");
        }
    }

//    @SuppressLint("WrongConstant")
//    private void storeDataInArrays() {
//        Log.d("workflow","Items storeDataInArrays method Called");
//        Cursor cursor=db.readAllItems();
//        Notification.Builder no_data = null;
//        if(cursor.getCount()==0){
//            empty_imageview.setVisibility(View.VISIBLE);
//            no_data.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            while(cursor.moveToNext()){
//                Item_Code.add(cursor.getString(0));
//                Item_Name.add(cursor.getString(1));
//                Item_Brand.add(cursor.getString(2));
//                Item_Count.add(cursor.getString(3));
//                BuyPrice_Item.add(cursor.getString(4));
//                SellPrice_Item.add(cursor.getString(5));
//                Item_Descrip.add(cursor.getString(6));
//            }
//            empty_imageview.setVisibility(View.GONE);
//            no_data.setVisibility(View.GONE);
//        }
   // }


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