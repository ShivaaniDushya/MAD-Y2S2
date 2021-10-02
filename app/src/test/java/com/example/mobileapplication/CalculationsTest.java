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
        float input1=100;
        float input2=75;
        float output;
        float expected=25;
        double delta=.1;

        Calculations calculations=new Calculations();
        output=calculations.calcNewBal(input1, input2);
        assertEquals(expected,output,delta);
    }

    @Test
    public void testItemPrice(){
        float input1=100;
        float input2=2;
        float output;
        float expected=200;
        double delta=.1;

        Calculations calculations=new Calculations();
        output=calculations.calcPrice(input1, input2);
        assertEquals(expected,output,delta);
    }

}