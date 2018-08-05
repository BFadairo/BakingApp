package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakingapp.Fragments.StepDetailFragment;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

import static com.example.android.bakingapp.Fragments.StepFragment.STEP_EXTRAS;
import static com.example.android.bakingapp.MainActivity.RECIPE_EXTRAS;

public class StepActivity extends AppCompatActivity {

    private final static String LOG_TAG = StepActivity.class.getSimpleName();
    public static final String LIST_EXTRAS = "list_extras";
    private final Bundle argsToPass = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (savedInstanceState == null) {
            Step step = getIntent().getParcelableExtra(STEP_EXTRAS);
            Recipe recipe = getIntent().getParcelableExtra(RECIPE_EXTRAS);
            ArrayList<Step> stepList = recipe.getSteps();
            Log.v(LOG_TAG, step.getStepId() + "Current Step ID");
            argsToPass.putParcelable(STEP_EXTRAS, step);
            argsToPass.putParcelableArrayList(LIST_EXTRAS, stepList);
            argsToPass.putParcelable(RECIPE_EXTRAS, recipe);

            //Create a new StepDetailFragment
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setArguments(argsToPass);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_container, stepDetailFragment)
                    .commit();
        }
    }

}
