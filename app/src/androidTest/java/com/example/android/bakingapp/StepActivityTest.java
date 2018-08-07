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
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class StepActivityTest {
    private static int currentRecipe = 0;
    private static int stepLength;

    @Rule
    public final ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);


   /* @Test
    public void checkExoPlayerView(){
        onView(withId(R.id.recycler_view_master))
                .perform(RecyclerViewActions.actionOnItemAtPosition(currentRecipe, click()));
    }*/

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
    public void checkStepButtonVisibility() {
        //Change this variable to 0 or StepLength to check the previous and next buttons at the beginning and end of the list
        //Next button should not be visible at the end
        //Previous button should not be visible at the beginning
        int position = stepLength;
        onView(withId(R.id.recycler_view_master))
                .perform(RecyclerViewActions.actionOnItemAtPosition(currentRecipe, click()));
        onView(withId(R.id.step_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, scrollTo())).perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));
        if (position == 0) {
            onView(withId(R.id.previous_step))
                    .check(matches(not(isDisplayed())));
            onView(withId(R.id.next_step))
                    .check(matches(isDisplayed()));
        }
        if (position == stepLength) {
            onView(withId(R.id.next_step))
                    .check(matches(not(isDisplayed())));
            onView(withId(R.id.previous_step))
                    .check(matches(isDisplayed()));
        }
    }


    @Test
    public void checkPreviousStepButtons() {
        onView(withId(R.id.recycler_view_master))
                .perform(RecyclerViewActions.actionOnItemAtPosition(currentRecipe, click()));
        onView(withId(R.id.step_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(stepLength, scrollTo())).perform(RecyclerViewActions.actionOnItemAtPosition(stepLength, click()));
        for (int i = stepLength; i > 0; i--) {
            if (i == 0) {
                pressBack();
            } else {
                onView(withId(R.id.previous_step)).check(matches(isDisplayed())).perform(click());
            }
        }
    }

    @Test
    public void checkNextStepButtons() {
        onView(withId(R.id.recycler_view_master))
                .perform(RecyclerViewActions.actionOnItemAtPosition(currentRecipe, click()));
        onView(withId(R.id.step_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        for (int i = 0; i < stepLength; i++) {
            if (i >= 0 && !(i == stepLength)) {
                onView(withId(R.id.next_step)).check(matches(isDisplayed())).perform(click());
            }
        }
    }
}
