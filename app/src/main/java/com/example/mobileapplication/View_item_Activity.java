package com.example.mobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class View_item_Activity extends AppCompatActivity {

    ImageButton back;
    Create_item_Activity a = new Create_item_Activity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

//        //create a back button
//        back = (ImageButton) findViewById(R.id.backButon2);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                a.openItemsActivity();
//            }
//        });


    }



}