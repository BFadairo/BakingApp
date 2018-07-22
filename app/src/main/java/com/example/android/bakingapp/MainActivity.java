package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakingapp.Utils.Json.JsonUtils;
import com.example.android.bakingapp.Utils.Networking.GetRecipeData;
import com.example.android.bakingapp.Utils.Networking.RetrofitInstance;
import com.example.android.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public final static String LOG_TAG = MainActivity.class.getSimpleName();

    private List<Recipe> mRecipes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(LOG_TAG, mRecipes + "");

        JsonUtils.getSampleData();

    }


    private void getJson() {

        GetRecipeData recipeData = new RetrofitInstance().getRetrofitInstance().create(GetRecipeData.class);

        Call<List<Recipe>> call = recipeData.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                mRecipes = response.body();
                Log.v(LOG_TAG, mRecipes + "jsonData");
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.v(LOG_TAG, "Error on network");
            }
        });
    }
}
