package com.example.android.bakingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.bakingapp.Adapters.MasterListAdapter;
import com.example.android.bakingapp.DetailActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Utils.Networking.GetRecipeData;
import com.example.android.bakingapp.Utils.Networking.RetrofitInstance;
import com.example.android.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.bakingapp.MainActivity.RECIPE_EXTRAS;

public class MasterListFragment extends Fragment implements MasterListAdapter.AdapterOnClick {

    private final static String LOG_TAG = MasterListFragment.class.getSimpleName();

    private List<Recipe> mRecipes = new ArrayList<>();

    private RecyclerView recyclerView;

    private MasterListAdapter mAdapter;

    private RecyclerView.LayoutManager layoutManager;

    // Mandatory empty constructor
    public MasterListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        // Get a reference to the RecyclerView in the fragment_master_list xml layout file
        recyclerView = rootView.findViewById(R.id.recycler_view_master);
        //Perform the network request and populate the UI
        retrieveJson();

        // Return the root view
        return rootView;
    }

    private void retrieveJson() {
        populateUi();
        GetRecipeData recipeData = new RetrofitInstance().getRetrofitInstance().create(GetRecipeData.class);

        Call<List<Recipe>> call = recipeData.getRecipes();
        //Used to determine if link is set-up correctly
        //Log.v(LOG_TAG, call.request().toString() + "Url")

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    mRecipes.clear();
                    mRecipes = response.body();
                    //Log.v(LOG_TAG, mRecipes + "jsonData");
                    mAdapter.setRecipeData(mRecipes);
                    //for (int i = 0; i < mRecipes.size(); i++){
                    //    Log.v(LOG_TAG, mRecipes.get(i).name);
                    //}
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.v(LOG_TAG, t.getMessage());
            }
        });
    }

    public void populateUi() {
        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL recipes currently in the Json
        //TODO: Complete the rest of this
        mAdapter = new MasterListAdapter(getContext(), mRecipes, this);

        // Set the adapter on the RecyclerView
        recyclerView.setAdapter(mAdapter);

        //Create a LinearLayout manager
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

    }


    @Override
    public void onClick(Recipe recipe) {
        Intent detailActivity = new Intent(getContext(), DetailActivity.class);
        detailActivity.putExtra(RECIPE_EXTRAS, recipe);
        startActivity(detailActivity);
    }
}
