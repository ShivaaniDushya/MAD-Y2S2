package com.example.mobileapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoarding extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotLayout;
    OnBoardAdapter onBoardAdapter;
    TextView[] dots;
    int currentPos;
    Button getStarted;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        viewPager = findViewById(R.id.splash_view_pager);
        dotLayout = findViewById(R.id.dots);
        nextBtn = (Button) findViewById(R.id.btn_on_board_next);
        getStarted = (Button) findViewById(R.id.btn_on_board_started);
        Log.d("workflow", "ane mekawath weda karanna" + nextBtn);

        onBoardAdapter = new OnBoardAdapter(this);
        viewPager.setAdapter(onBoardAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    public void skip(View view) {
        startActivity(new Intent(OnBoarding.this, MainActivity.class));
        finish();
    }

    public void next(View view) {
        viewPager.setCurrentItem(currentPos+1);
    }

    public void addDots(int position) {
        dots = new TextView[3];
        dotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(40);
            dots[i].setGravity(Gravity.CENTER);
            dots[i].setTextColor(getResources().getColor(R.color.beau_blue));

            dotLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.teal_700));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPos = position;
            if (position == 0) {
                nextBtn.setVisibility(View.VISIBLE);
                getStarted.setVisibility(View.GONE);
            }
            else if (position == 1) {
                nextBtn.setVisibility(View.VISIBLE);
                getStarted.setVisibility(View.GONE);
            }
            else {
                nextBtn.setVisibility(View.GONE);
                getStarted.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}