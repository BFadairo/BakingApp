package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.android.bakingapp.Fragments.MasterListFragment;

public class MainActivity extends AppCompatActivity {

    public static String RECIPE_EXTRAS = "recipes_extras";

    public static boolean mTwoPane;
    private FrameLayout twoPaneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RECIPE_EXTRAS = this.getResources().getString(R.string.recipe_extras);

        if (findViewById(R.id.master_list_container) != null) {
            mTwoPane = true;
            FragmentManager fragmentManager = getSupportFragmentManager();
            MasterListFragment masterListFragment = new MasterListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.master_list_container, masterListFragment)
                    .commit();
        } else {
            mTwoPane = false;
        }

        this.setTitle("Recipe List");
    }

}
