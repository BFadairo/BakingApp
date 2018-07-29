package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.android.bakingapp.Fragments.IngredientFragment;
import com.example.android.bakingapp.Fragments.StepFragment;
import com.example.android.bakingapp.model.Recipe;

import static com.example.android.bakingapp.MainActivity.RECIPE_EXTRAS;

public class DetailActivity extends AppCompatActivity {

    private final static String LOG_TAG = DetailActivity.class.getSimpleName();
    private Recipe recipe;
    private LinearLayout twoPaneLayout;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        twoPaneLayout = findViewById(R.id.two_pane);

        //Get the intent that started the Activity and put it into a Recipe Object
        if (savedInstanceState == null) {
            recipe = getIntent().getParcelableExtra(RECIPE_EXTRAS);
            this.setTitle(recipe.getRecipeName());

            //Create a new Ingredient Fragment
            IngredientFragment ingredientFragment = new IngredientFragment();
            ingredientFragment.setArguments(getIntent().getExtras());
            //Create a new Step Fragment
            StepFragment stepFragment = new StepFragment();
            stepFragment.setArguments(getIntent().getExtras());

            //Get the support FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();

            //Add the fragments to the activities via unique ID
            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_container, ingredientFragment)
                    .commit();

            fragmentManager.beginTransaction()
                    .add(R.id.step_container, stepFragment)
                    .commit();
        } else {
            mTwoPane = false;
        }
    }
}
