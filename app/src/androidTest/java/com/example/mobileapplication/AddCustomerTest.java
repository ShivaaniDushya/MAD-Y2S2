package com.example.mobileapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.provider.MediaStore;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AddCustomerTest {

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(Customers.class);

    private ActivityScenario scenario = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(AddCustomer.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        scenario = rule.getScenario();
    }

    @Test
    public void testLaunchOfCameraActivityOnButtonClick() {
        assertNotNull(scenario.onActivity(activity -> {
            activity.findViewById(R.id.add_customer);
        }));

        onView(withId(R.id.add_customer)).perform(click());

        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(secondActivity);
        secondActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        scenario = null;
    }
}