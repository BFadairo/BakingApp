package com.example.android.bakingapp.Fragments;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.bakingapp.Adapters.MasterListAdapter;
import com.example.android.bakingapp.DetailActivity;
import com.example.android.bakingapp.IdlingResource.SimpleIdlingResource;
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
import static com.example.android.bakingapp.MainActivity.mTwoPane;

public class MasterListFragment extends Fragment implements MasterListAdapter.AdapterOnClick {

    private final static String LOG_TAG = MasterListFragment.class.getSimpleName();

    private List<Recipe> mRecipes = new ArrayList<>();

    private RecyclerView recyclerView;

    private MasterListAdapter mAdapter;

    private Intent widgetIntent;

    @Nullable
    private SimpleIdlingResource mIdlingResource;


    @VisibleForTesting
    @NonNull
    private IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    // Mandatory empty constructor
    public MasterListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        // Get a reference to the RecyclerView in the fragment_master_list xml layout file
        recyclerView = rootView.findViewById(R.id.recycler_view_master);
        getIdlingResource();
        //Perform the network request and populate the UI
        if (savedInstanceState != null) {
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPE_EXTRAS);
            populateUi();
        } else {
            retrieveJson();
        }
        //Create a new Intent to be broadcasted to the Widget
        widgetIntent = new Intent();

        // Return the root view
        return rootView;
    }

    private void retrieveJson() {
        populateUi();
        GetRecipeData recipeData = new RetrofitInstance().getRetrofitInstance().create(GetRecipeData.class);

        Call<List<Recipe>> call = recipeData.getRecipes();
        //Used to determine if link is set-up correctly

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    mRecipes.clear();
                    mRecipes = response.body();
                    mAdapter.setRecipeData(mRecipes);
                    Log.v(LOG_TAG, "Retrofit called");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.v(LOG_TAG, t.getMessage());
            }
        });
    }

    private void populateUi() {
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }
        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL recipes currently in the Json
        mAdapter = new MasterListAdapter(getContext(), mRecipes, this);

        // Set the adapter on the RecyclerView
        recyclerView.setAdapter(mAdapter);

        //Create a LinearLayout manager if a twopane layout is not needed
        RecyclerView.LayoutManager layoutManager;
        if (mTwoPane) {
            int numberOfColumns = 3;
            layoutManager = new GridLayoutManager(getContext(), numberOfColumns);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(LOG_TAG, "onSaveInstanceState called");
        outState.putParcelableArrayList(RECIPE_EXTRAS, (ArrayList<Recipe>) mRecipes);
    }


    @Override
    public void onClick(Recipe recipe) {
        Intent detailActivity = new Intent(getContext(), DetailActivity.class);
        detailActivity.putExtra(RECIPE_EXTRAS, recipe);
        startActivity(detailActivity);
        widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        widgetIntent.putExtra("Extra", recipe);
        if (recipe != null) {
            getActivity().sendBroadcast(widgetIntent);
        }
    }

}
