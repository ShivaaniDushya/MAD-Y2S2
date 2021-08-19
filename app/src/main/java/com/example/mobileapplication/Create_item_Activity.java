package com.example.mobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Create_item_Activity extends AppCompatActivity {

    ImageButton back;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

//        //create a back button
//        back = (ImageButton) findViewById(R.id.imageButton2);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openItemsActivity();
//            }
//        });


    }

    public void openItemsActivity() {
        Intent intent = new Intent(this, Items.class);
        startActivity(intent);
    }



}