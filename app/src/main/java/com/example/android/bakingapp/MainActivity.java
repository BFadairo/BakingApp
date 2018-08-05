package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.Fragments.MasterListFragment;
import com.example.android.bakingapp.IdlingResource.SimpleIdlingResource;

public class MainActivity extends AppCompatActivity {

    public static String RECIPE_EXTRAS = "recipes_extras";

    public static boolean mTwoPane;

    @Nullable
    private SimpleIdlingResource mIdlingResource;


    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIdlingResource();

        //To be used throughout the App to pass recipe extras in Intents
        RECIPE_EXTRAS = this.getResources().getString(R.string.recipe_extras);

        //If the below view is Null the user is using a smaller phone (i.e not a tablet)
        if (findViewById(R.id.master_list_container) != null) {
            mTwoPane = true;
            //If View is found create a get the support fragment Manager
            FragmentManager fragmentManager = getSupportFragmentManager();
            //Create a new MasterListFragment and add it to the MainView
            MasterListFragment masterListFragment = new MasterListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.master_list_container, masterListFragment)
                    .commit();
        } else {
            //If not found set to false
            mTwoPane = false;
        }

        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }

        //Set the Title of the Activity to Recipe List
        this.setTitle(getResources().getString(R.string.recipe_list));
    }

}
