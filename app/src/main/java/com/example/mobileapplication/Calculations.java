package com.example.mobileapplication;

import java.text.DecimalFormat;

public class Calculations {

    protected double calcfuel(double entdist) {
        double avgdist = 4.00, fuelamount = 0.00;
        DecimalFormat df_obj = new DecimalFormat("#.##");
        fuelamount=entdist/avgdist;
        return Double.valueOf(df_obj.format(fuelamount));
    }


}
