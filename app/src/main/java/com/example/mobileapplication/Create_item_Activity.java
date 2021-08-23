package com.example.mobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Create_item_Activity extends AppCompatActivity {

    EditText ItemName_input, ItemBrand_input, ItemCount_input, BuyPriceItem_input, SellPriceItem_input, ItemDescrip_input;
    ImageButton back;
    Button create;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        //create a back button
        back = (ImageButton) findViewById(R.id.imageButton2_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openItemsActivity();
            }
        });

//        ItemName_input = findViewById(R.id.Item_Name);
//        ItemBrand_input = findViewById(R.id.Item_Brand);
//        ItemCount_input = findViewById(R.id.Item_Count);
//        BuyPriceItem_input = findViewById(R.id.Item_Description);
//        SellPriceItem_input = findViewById(R.id.Item_Description);
//        ItemDescrip_input = findViewById(R.id.Item_Description);
//        create = findViewById(R.id.btn_create);
//
//        create.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ItemsTable itemsTable = new ItemsTable(Create_item_Activity.this);
//                itemsTable.addItem(ItemName_input.getText().toString().trim(),
//                        ItemBrand_input.getText().toString().trim(),
//                        Integer.parseInt(ItemCount_input.getText().toString().trim()),
//                        Double.parseDouble(BuyPriceItem_input.getText().toString().trim()),
//                        Double.parseDouble(SellPriceItem_input.getText().toString().trim()),
//                        ItemDescrip_input.getText().toString().trim());
//            }
//        });

    }

    public void openItemsActivity() {
        Intent intent = new Intent(this, Items.class);
        startActivity(intent);
    }

}