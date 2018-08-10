package com.example.android.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class StepActivityTest {
    private static final int currentRecipe = 0;
    private static int stepLength;

    @Rule
    public final ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void waitForLoad() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkStepDetailActivity() {
        onView(withId(R.id.recycler_view_master))
                .perform(RecyclerViewActions.actionOnItemAtPosition(currentRecipe, click()));

        onView(withId(R.id.step_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.step_detail_description)).check(matches(isDisplayed()));

        onView(withId(R.id.exo_player_container)).check(matches(isDisplayed()));
    }
}
