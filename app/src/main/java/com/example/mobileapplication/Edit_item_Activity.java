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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileapplication.database.DBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Edit_item_Activity extends AppCompatActivity {

    EditText edID, editItemName_input, editItemBrand_input, editItemCount_input, editBuyPriceItem_input, editSellPriceItem_input, editItemDescrip_input;
    public boolean isfieldsvalidated = false;
    String issetasdefault = "0";
    Button calculation;

    String itemCode, itemName, itemBrand, itemCount, itemBuyPrice, itemSellPrice, itemDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Log.d("workflow", "Edit_item_Activity onCreate method called");
        edID = findViewById(R.id.item_code_update);
        editItemName_input = findViewById(R.id.item_name_update);
        editItemBrand_input = findViewById(R.id.item_brand_update);
        editItemCount_input = findViewById(R.id.item_count_update);
        editBuyPriceItem_input = findViewById(R.id.item_buyprice_update);
        editSellPriceItem_input = findViewById(R.id.item_sellrice_update);
        editItemDescrip_input = findViewById(R.id.item_description_update);

        calculation = findViewById(R.id.btncal);

        calculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalculationActivity();
                Log.d("workflow","Calculation Button Clicked");
            }
        });

        getAndSetIntentData_Item();

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
                Log.d("workflow", "Edit Item Activity Item Bottom Nav method  Called");
                return false;
            }
        });
    }

    public void openCalculationActivity() {
        Intent intent = new Intent(this, ItemCalculation.class);
        startActivity(intent);
    }

    void getAndSetIntentData_Item() {

        Intent intent = new Intent();

        Log.d("workflow", "Modify Item getAndSetIntentData  method  Called");

        if (getIntent().hasExtra("itemCode") &&
                getIntent().hasExtra("itemName") &&
                getIntent().hasExtra("itemBrand") &&
                getIntent().hasExtra("itemCount") &&
                getIntent().hasExtra("itemBuyPrice") &&
                getIntent().hasExtra("itemSellPrice") &&
                getIntent().hasExtra("itemDescription"))// && getIntent().hasExtra("created") && getIntent().hasExtra("modified")
        {
            itemCode = getIntent().getStringExtra("itemCode");
            itemName = getIntent().getStringExtra("itemName");
            itemBrand = getIntent().getStringExtra("itemBrand");
            itemCount = getIntent().getStringExtra("itemCount");
            itemBuyPrice = getIntent().getStringExtra("itemBuyPrice");
            itemSellPrice = getIntent().getStringExtra("itemSellPrice");
            itemDescription = getIntent().getStringExtra("itemDescription");


            //  Log.d("mvalies",rid);
            edID.setText(itemCode);
            editItemName_input.setText(itemName);
            editItemBrand_input.setText(itemBrand);
            editItemCount_input.setText(itemCount);
            editBuyPriceItem_input.setText(itemBuyPrice);
            editSellPriceItem_input.setText(itemSellPrice);
            editItemDescrip_input.setText(itemDescription);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateItem(View view) {

        isfieldsvalidated = CheckAllFields();
        Log.d("workflow","Edit_Item_Activity updateItem  method  Called");

        Log.d("workflow",issetasdefault);
        if (isfieldsvalidated) {

            DBHelper dbHelper = new DBHelper(this);

            //   int val1=dbHelper.update_def_route();

            int val = dbHelper.updateItem(
                    edID.getText().toString(),
                    editItemName_input.getText().toString(),
                    editItemBrand_input.getText().toString(),
                    Integer.parseInt(editItemCount_input.getText().toString()),
                    Double.parseDouble(editBuyPriceItem_input.getText().toString()),
                    Double.parseDouble(editSellPriceItem_input.getText().toString()),
                    editItemDescrip_input.getText().toString());

            Toast.makeText(this, "Record Updated Succesfully ", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, Items.class);
            startActivity(intent);
            Log.i("BTN Click", "Update item Confirmation button clicked");
        }
    }

    private boolean CheckAllFields() {
        Log.d("workflow","Edit_Item_Activity CheckAllFields  method  Called");
        if (editItemName_input.length() == 0) {
            editItemName_input.setError("This field is required");
            return false;
        }

        if (editItemBrand_input.length() == 0) {
            editItemBrand_input.setError("This field is required");
            return false;
        }

        if (editItemCount_input.length() == 0) {
            editItemCount_input.setError("This field is required");
            return false;
        }
        if (editBuyPriceItem_input.length() == 0) {
            editBuyPriceItem_input.setError("This field is required");
            return false;
        }

        if (editSellPriceItem_input.length() == 0) {
            editSellPriceItem_input.setError("This field is required");
            return false;
        }

        if (editItemDescrip_input.length() == 0 ) {
            editItemDescrip_input.setError("This field is required");
            return false;
        }

        return true;

    }
}