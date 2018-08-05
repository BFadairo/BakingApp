package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.android.bakingapp.Fragments.IngredientFragment;
import com.example.android.bakingapp.Fragments.StepDetailFragment;
import com.example.android.bakingapp.Fragments.StepFragment;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

import static com.example.android.bakingapp.MainActivity.RECIPE_EXTRAS;
import static com.example.android.bakingapp.MainActivity.mTwoPane;
import static com.example.android.bakingapp.StepActivity.LIST_EXTRAS;

public class DetailActivity extends AppCompatActivity implements StepFragment.SendStepData {

    private StepDetailFragment stepDetailFragment;
    private final Bundle argsToPass = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        LinearLayout twoPaneLayout = findViewById(R.id.two_pane);


        //Get the intent that started the Activity and put it into a Recipe Object
        if (savedInstanceState == null) {
            Recipe recipe = getIntent().getParcelableExtra(RECIPE_EXTRAS);
            ArrayList<Step> stepList = recipe.getSteps();

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

            if (mTwoPane && stepDetailFragment == null) {
                argsToPass.putParcelableArrayList(LIST_EXTRAS, stepList);
                argsToPass.putParcelable(RECIPE_EXTRAS, recipe);
                stepDetailFragment = new StepDetailFragment();
                stepDetailFragment.setArguments(argsToPass);
                fragmentManager.beginTransaction()
                        .add(R.id.step_detail_container, stepDetailFragment)
                        .commit();
            }
        }
    }

    @Override
    public void sendSteps(Step step) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        stepDetailFragment.setArguments(argsToPass);
        fragmentManager.beginTransaction()
                .replace(R.id.step_detail_container, stepDetailFragment)
                .commit();

        stepDetailFragment.receiveStepInterface(this, step);
    }
}
