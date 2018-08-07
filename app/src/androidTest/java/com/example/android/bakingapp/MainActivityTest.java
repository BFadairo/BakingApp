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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public final ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource;
    public String RECIPE_NAME = "Yellow Cake";
    private String[] RECIPE_NAMES = new String[]{"Nutella Pie", "Brownies", "Yellow Cake", "Cheesecake"};

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void checkIfRecipeIsLoaded() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recycler_view_master))
                .perform(RecyclerViewActions.scrollToPosition(0));
        onView(withText(RECIPE_NAME)).check(matches(isDisplayed()));
    }

    @Test
    public void checkRecipeListOpenDetailActivity() {
        for (int i = 0; i < RECIPE_NAMES.length; i++) {
            onView(withId(R.id.recycler_view_master))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, click()));
            onView(withText(RECIPE_NAMES[i])).check(matches(isDisplayed())).perform(pressBack());
        }
    }


    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(mIdlingResource);
    }
}