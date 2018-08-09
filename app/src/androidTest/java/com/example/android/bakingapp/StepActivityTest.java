package com.example.android.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class StepActivityTest {
    private static int currentRecipe = 0;
    private static int stepLength;

    @Rule
    public final ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);


    @BeforeClass
    public static void setupLength() {
        int nutellaLength = 6;
        int brownieLength = 9;
        int yellowCakeLength = 12;
        int cheesecakeLength = 12;
        switch (currentRecipe) {
            case 0:
                stepLength = nutellaLength;
                break;
            case 1:
                stepLength = brownieLength;
                break;
            case 2:
                stepLength = yellowCakeLength;
                break;
            case 3:
                stepLength = cheesecakeLength;
        }
    }

    @Before
    public void waitForLoad() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkExoPlayerView() {
        onView(withId(R.id.recycler_view_master))
                .perform(RecyclerViewActions.actionOnItemAtPosition(currentRecipe, click()));
    }
}
