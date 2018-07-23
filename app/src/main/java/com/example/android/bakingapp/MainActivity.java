package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

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

        //Use below method to perform Retrofit
        retrieveJson();


    }

    private void retrieveJson() {
        GetRecipeData recipeData = new RetrofitInstance().getRetrofitInstance().create(GetRecipeData.class);

        Call<List<Recipe>> call = recipeData.getRecipes();
        //Used to determine if link is set-up correctly
        //Log.v(LOG_TAG, call.request().toString() + "Url")

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    mRecipes.clear();
                    mRecipes.addAll(response.body());
                    Log.v(LOG_TAG, mRecipes + "jsonData");
                    //for (int i = 0; i < mRecipes.size(); i++){
                    //    Log.v(LOG_TAG, mRecipes.get(i).name);
                    //}
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.v(LOG_TAG, t.getMessage());
            }
        });
    }
}
