package com.example.mobileapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Create_item_Activity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE_PP = 1 ;
    EditText ItemName_input, ItemBrand_input, ItemCount_input, BuyPriceItem_input, SellPriceItem_input, ItemDescrip_input;
    public boolean isfieldsvalidated=false;
    String issetasdefault="0";
    Button back;
    MaterialButton addItemBtn;
    Bundle bundle;
    String ItemName,AddStatusMsg;
    File itemPhotoFile;


    @RequiresApi(api = VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        Log.d("workflow", "Add Items onCreate  method  Called");
        ItemName_input = findViewById(R.id.inp_itemname);
        ItemBrand_input = findViewById(R.id.inp_itembrand);
        ItemCount_input = findViewById(R.id.inp_itemcount);
        BuyPriceItem_input = findViewById(R.id.inp_itemBuyPrice);
        SellPriceItem_input = findViewById(R.id.inp_itemSellPrice);
        ItemDescrip_input = findViewById(R.id.inp_itemDescription);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.items);


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

    }



    @RequiresApi(api = VERSION_CODES.O)
        public void addItem(View view) {
        Log.d("workflow", "Add Item addItem  method  Called");
        isfieldsvalidated = CheckAllFields();

        if (isfieldsvalidated) {
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

//            Snackbar.make(view, "Record Added Succesfully", BaseTransientBottomBar.LENGTH_LONG).setAction("OK", null).show();

//            Toast.makeText(this, "Record Added Succesfully", Toast.LENGTH_SHORT).show();
//
//            Intent intent = new Intent(this, Items.class);
//            startActivity(intent);
//            Log.i("BTN Click", "Add Item Confirmation button clicked");

            Log.d("workflow", String.valueOf(val));
            AddStatusMsg = "Item added successful.";

//            if (val == -1) {
//                AddStatusMsg = "Item added unsuccessful.";
//            }
//            else {
//                AddStatusMsg = "Item added successful.";
//            }

            Intent intent = new Intent(this, Items.class)
                    .putExtra("passMessage", AddStatusMsg);
            startActivity(intent);
            Log.d("BTN Click", "Save item button clicked");

        }

    }

        private boolean CheckAllFields() {
            Log.d("workflow","Add Item CheckAllFields  method  Called");
            if (ItemName_input.length() == 0) {
                ItemName_input.setError("This field is required");
                return false;
            }

            if (ItemBrand_input.length() == 0) {
                ItemBrand_input.setError("This field is required");
                return false;
            }

            if (ItemCount_input.length() == 0) {
                ItemCount_input.setError("This field is required");
                return false;
            }
            if (BuyPriceItem_input.length() == 0) {
                BuyPriceItem_input.setError("This field is required");
                return false;
            }

            if (SellPriceItem_input.length() == 0) {
                SellPriceItem_input.setError("This field is required");
                return false;
            }

            if (ItemDescrip_input.length()  == 0 ) {
                ItemDescrip_input.setError("This field is required");
                return false;
            }

            return true;

        }


//    public void openItemsActivity() {
//        Intent intent = new Intent(this, Items.class);
//        startActivity(intent);
//    }

}