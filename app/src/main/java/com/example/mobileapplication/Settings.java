package com.example.mobileapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {

    RadioGroup langRadio;
    RadioButton radioButton;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        langRadio = findViewById(R.id.langRadioGroup);
        setCheckedBtn();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.clearFocus();

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
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
        });
    }

    @SuppressLint("NonConstantResourceId")
    public void changeLanguage(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.lang_en:
                if (checked) {
                    localeHelper.setLocale(this, "en");
                    setCheckedBtn();
                    recreate();
                    break;
                }
            case R.id.lang_si:
                if (checked) {
                    localeHelper.setLocale(this, "si");
                    setCheckedBtn();
                    recreate();
                    break;
                }
            case R.id.lang_tm:
                if (checked) {
                    localeHelper.setLocale(this, "ta");
                    setCheckedBtn();
                    recreate();
                    break;
                }

        }
    }
    public void setCheckedBtn() {
        String lang = localeHelper.loadSelectedLocale(this);
        Log.d("workflow", lang);
        if (lang.equals("en")) {
            radioButton = findViewById(R.id.lang_en);
            radioButton.setChecked(true);
        }
        if (lang.equals("si")) {
            radioButton = findViewById(R.id.lang_si);
            radioButton.setChecked(true);
        }
        if (lang.equals("ta")) {
            radioButton = findViewById(R.id.lang_tm);
            radioButton.setChecked(true);
        }
    }
}