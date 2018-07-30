package com.example.android.bakingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Adapters.StepAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.StepActivity;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.util.List;

import static com.example.android.bakingapp.MainActivity.RECIPE_EXTRAS;
import static com.example.android.bakingapp.MainActivity.mTwoPane;

public class StepFragment extends Fragment implements StepAdapter.AdapterOnClick {

    //
    public final static String LOG_TAG = StepFragment.class.getSimpleName();
    public final static String STEP_EXTRAS = "step_extras";
    private StepAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Recipe recipe;
    private List<Step> mSteps;
    private Bundle passedArgs;
    public SendStepData stepInterface;


    public StepFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);

        recyclerView = rootView.findViewById(R.id.step_recycler_view);
        //Retrieve the Arguments from the parent activity
        passedArgs = getArguments();
        //Get the Recipe object from the Bundle
        recipe = passedArgs.getParcelable(RECIPE_EXTRAS);
        stepInterface = (SendStepData) getActivity();

        populateUI();
        return rootView;
    }


    public void retrieveSteps() {
        //Receive the steps from the current Recipe
        mSteps = recipe.getSteps();
    }

    public void populateUI() {
        retrieveSteps();
        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL the steps with associated recipe
        mAdapter = new StepAdapter(getContext(), mSteps, this);

        // Set the adapter on the RecyclerView
        recyclerView.setAdapter(mAdapter);
        recyclerView.setFocusable(false);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);

        //Create a LinearLayout manager
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(Step step) {
        if (!(mTwoPane)) {
            Intent stepActivity = new Intent(getContext(), StepActivity.class);
            stepActivity.putExtra(STEP_EXTRAS, step);
            stepActivity.putExtra(RECIPE_EXTRAS, recipe);
            startActivity(stepActivity);
        } else {
            stepInterface.sendSteps(step);
        }
    }

    public interface SendStepData {
        void sendSteps(Step step);
    }
}
