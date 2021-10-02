package com.example.mobileapplication;
import static org.junit.Assert.*;
import org.junit.Test;

public class ItemCalculationTest {
    @Test
    public void textcalc(){
        double input1=10;
        double input2=12;
        double input3=10;
        double input4=10;
        double output;
        double expected=10;
        double delta=.1;

        Calculations calculations=new Calculations();
        output=calculations.calculateExpectedProfit(input1, input2, input3, input4);
        assertEquals(expected,output,delta);
    }

}