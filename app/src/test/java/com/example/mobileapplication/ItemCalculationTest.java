package com.example.mobileapplication;

import static org.junit.Assert.assertEquals;


import android.content.ClipData;

import junit.framework.TestCase;

import org.junit.Test;

public class ItemCalculationTest extends TestCase {
    @Test
    public void profcal() {
        double input = 170*1000-120*1000 - 1000;
        double output = 0;
        double expected = 49000;
        double delta = .1;

        ItemCalculation it = new ItemCalculation();
        assertEquals(expected, output, delta);
    }
}