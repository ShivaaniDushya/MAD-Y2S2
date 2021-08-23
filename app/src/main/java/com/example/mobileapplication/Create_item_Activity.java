package com.example.mobileapplication;

import android.content.Intent;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class Create_item_Activity extends AppCompatActivity {

    EditText ItemName_input, ItemBrand_input, ItemCount_input, BuyPriceItem_input, SellPriceItem_input, ItemDescrip_input;
    ImageButton back;
    //Button create;
    public boolean isfieldsvalidated=false;
    String issetasdefault="0";


    @RequiresApi(api = VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        Log.d("workflow", "Add Items onCreate  method  Called");
        ItemName_input = findViewById(R.id.Item_Name);
        ItemBrand_input = findViewById(R.id.Item_Brand);
        ItemCount_input = findViewById(R.id.Item_Count);
        BuyPriceItem_input = findViewById(R.id.Buy_price);
        SellPriceItem_input = findViewById(R.id.Sell_price);
        ItemDescrip_input = findViewById(R.id.Item_Description);
        //create = findViewById(R.id.btn_create);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.routes);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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
            }
        });

        //create a back button
//        back = findViewById(R.id.imageButton2_back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openItemsActivity();
//            }
//        });
    }



        @RequiresApi(api = VERSION_CODES.O)
        public void addItem(View view) {
            Log.d("workflow","Add Item addItem  method  Called");
            //isfieldsvalidated = CheckAllFields();

          //  if (isfieldsvalidated) {
                DBHelper dbHelper = new DBHelper(this);
                //removedefault(issetasdefault);

                long val;
                val = dbHelper.addItem(ItemName_input.getText().toString(),
                        ItemBrand_input.getText().toString(),
                        Integer.parseInt(ItemCount_input.getText().toString()),
                        Double.parseDouble(BuyPriceItem_input.getText().toString()),
                        Double.parseDouble(SellPriceItem_input.getText().toString()),
                        ItemDescrip_input.getText().toString()
                        );

                Snackbar.make(view,"Record Added Succesfully", BaseTransientBottomBar.LENGTH_LONG).setAction("OK",null).show();

                Toast.makeText(this, "Record Added Succesfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, Items.class);
                startActivity(intent);
                Log.i("BTN Click", "Add Item Confirmation button clicked");
            }
      //  }

//        private void removedefault(String issetasdefault) {
//            Log.d("workflow","Add Item removedefault  method  Called");
//            if(Integer.parseInt(issetasdefault)==1){
//                DBHelper dbHelper = new DBHelper(this);
//                int res=dbHelper.update_def_item_on_create();
//                Toast.makeText(this, "Default Item Changed Succesfully", Toast.LENGTH_SHORT).show();
//            }
//        }

//        private boolean CheckAllFields() {
//            Log.d("workflow","Add Item CheckAllFields  method  Called");
//            if (ItemName_input.length() == 0) {
//                ItemName_input.setError("This field is required");
//                return false;
//            }
//
//            if (ItemBrand_input.length() == 0) {
//                ItemBrand_input.setError("This field is required");
//                return false;
//            }
//
//            if (ItemCount_input.length() == 0) {
//                ItemCount_input.setError("This field is required");
//                return false;
//            }
//            if (BuyPriceItem_input.length() > 10) {
//                BuyPriceItem_input.setError("Maximum Characters can be entered is 10");
//                return false;
//            }
//
//            if (SellPriceItem_input.length() > 10) {
//                SellPriceItem_input.setError("Maximum Characters can be entered is 10");
//                return false;
//            }
//
//            if (ItemDescrip_input.length() > 4) {
//                ItemDescrip_input.setError("Maximum value can be entered is 999");
//                return false;
//            }
//
//            return true;
//
//        }


//    public void openItemsActivity() {
//        Intent intent = new Intent(this, Items.class);
//        startActivity(intent);
//    }

}