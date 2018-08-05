package com.example.android.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Adapters.IngredientAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

import static com.example.android.bakingapp.MainActivity.RECIPE_EXTRAS;

public class IngredientFragment extends Fragment {

    //Variables used
    private Recipe recipe;
    private List<Ingredient> ingredientList;
    private RecyclerView recyclerView;

    public IngredientFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        recyclerView = rootView.findViewById(R.id.ingredient_recycler_view);

        Bundle passedArgs = getArguments();

        recipe = passedArgs.getParcelable(RECIPE_EXTRAS);

        retrieveIngredients();

        return rootView;
    }

    private void populateUI() {
        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL the steps with associated recipe
        IngredientAdapter mAdapter = new IngredientAdapter(getContext(), ingredientList);

        // Set the adapter on the RecyclerView
        recyclerView.setAdapter(mAdapter);
        recyclerView.setFocusable(false);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);

        //Create a LinearLayout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void retrieveIngredients() {
        ingredientList = recipe.getIngredients();
        populateUI();
    }

}
