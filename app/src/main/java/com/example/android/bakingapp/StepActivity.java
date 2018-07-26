package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.android.bakingapp.Fragments.StepDetailFragment;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.util.List;

import static com.example.android.bakingapp.Fragments.StepFragment.STEP_EXTRAS;
import static com.example.android.bakingapp.MainActivity.RECIPE_EXTRAS;

public class StepActivity extends AppCompatActivity {

    private final static String LOG_TAG = StepActivity.class.getSimpleName();
    FragmentManager fragmentManager;
    private Step step;
    private Recipe recipe;
    private Button nextStep, previousStep;
    private List<Step> stepList;
    private StepDetailFragment stepDetailFragment;
    private Bundle argsToPass = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        nextStep = findViewById(R.id.next_step);

        previousStep = findViewById(R.id.previous_step);


        if (savedInstanceState == null) {
            step = getIntent().getParcelableExtra(STEP_EXTRAS);
            recipe = getIntent().getParcelableExtra(RECIPE_EXTRAS);
            argsToPass.putParcelable(STEP_EXTRAS, step);
            getNextStep();
            getPreviousStep();
            stepList = recipe.getSteps();

            this.setTitle(step.getStepShortDescription());

            //Create a new StepDetailFragment
            stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setArguments(argsToPass);

            fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_container, stepDetailFragment)
                    .commit();
        }
    }

    public void getPreviousStep() {
        previousStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentStep = step.getStepId();
                if (!(currentStep <= 0)) {
                    step = stepList.get(currentStep - 1);
                    argsToPass.putParcelable(STEP_EXTRAS, step);
                    stepDetailFragment = new StepDetailFragment();
                    stepDetailFragment.setArguments(argsToPass);
                    fragmentManager.beginTransaction()
                            .replace(R.id.step_detail_container, stepDetailFragment)
                            .commit();
                    Log.v(LOG_TAG, step + "+");
                }
            }
        });
    }

    public void getNextStep() {
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentStep = step.getStepId();
                if (!(currentStep >= stepList.size() - 1)) {
                    step = stepList.get(currentStep + 1);
                    argsToPass.putParcelable(STEP_EXTRAS, step);
                    stepDetailFragment = new StepDetailFragment();
                    stepDetailFragment.setArguments(argsToPass);
                    fragmentManager.beginTransaction()
                            .replace(R.id.step_detail_container, stepDetailFragment)
                            .commit();
                    Log.v(LOG_TAG, step + "+");
                }
            }
        });
    }
}
