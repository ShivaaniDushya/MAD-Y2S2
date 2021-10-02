package com.example.mobileapplication;

import java.text.DecimalFormat;

public class Calculations {

    protected double calcfuel(double entdist) {
        double avgdist = 4.00, fuelamount;
        DecimalFormat df_obj = new DecimalFormat("#.##");
        fuelamount=entdist/avgdist;
        return Double.parseDouble(df_obj.format(fuelamount));
    }

    protected double calculateExpectedProfit(double buy, double sell, double count, double cost) {

        return (sell*count) - (buy*count) - cost;
    }

    protected float calcNewBal(float bal, float pay) {
        float newbal = bal - pay;
        return newbal;
    }
}
