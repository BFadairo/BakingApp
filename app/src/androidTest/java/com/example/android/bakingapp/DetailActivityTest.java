package com.example.android.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
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

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {
    @Rule
    public final ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;
    private String[] RECIPE_NAMES = new String[]{"Nutella Pie", "Brownies", "Yellow Cake", "Cheesecake"};
    private int currentRecipe = 3;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void checkStepButtons() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recycler_view_master))
                .perform(RecyclerViewActions.actionOnItemAtPosition(currentRecipe, click()));
        for (int i = 0; i < 6; i++) {
            onView(withId(R.id.step_recycler_view))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, click()));
            onView(withId(R.id.step_detail_description)).check(matches(isDisplayed())).perform(pressBack());
            onView(withId(R.id.step_recycler_view))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i + 1, scrollTo()));
        }
    }

    @Test
    public void checkIfIngredientsLoaded() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recycler_view_master))
                .perform(RecyclerViewActions.actionOnItemAtPosition(currentRecipe, click()));

        onView(withId(R.id.ingredient_recycler_view))
                .check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(mIdlingResource);
    }
}
