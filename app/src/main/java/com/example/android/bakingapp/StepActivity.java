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
    FragmentManager fragmentManager;
    private Step step;
    private Recipe recipe;
    public static final String LIST_EXTRAS = "list_extras";
    private StepDetailFragment stepDetailFragment;
    private Bundle argsToPass = new Bundle();
    private ArrayList<Step> stepList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (savedInstanceState == null) {
            step = getIntent().getParcelableExtra(STEP_EXTRAS);
            recipe = getIntent().getParcelableExtra(RECIPE_EXTRAS);
            stepList = recipe.getSteps();
            Log.v(LOG_TAG, step.getStepId() + "Current Step ID");
            argsToPass.putParcelable(STEP_EXTRAS, step);
            argsToPass.putParcelableArrayList(LIST_EXTRAS, stepList);
            argsToPass.putParcelable(RECIPE_EXTRAS, recipe);

            //Create a new StepDetailFragment
            stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setArguments(argsToPass);

            fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_container, stepDetailFragment)
                    .commit();
        }
    }

}
