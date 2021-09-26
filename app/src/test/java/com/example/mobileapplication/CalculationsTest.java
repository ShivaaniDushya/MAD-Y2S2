package com.example.mobileapplication;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalculationsTest {

    @Test
    public void textcalc(){
        double input=4;
        double output;
        double expected=1;
        double delta=.1;

        Calculations calculations=new Calculations();
        output=calculations.calcfuel(input);
        assertEquals(expected,output,delta);
    }

    @Test
    public void testNewBalDue(){
        assertEquals(750,1000-250);
    }

}