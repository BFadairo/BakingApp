package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public final static String LOG_TAG = MainActivity.class.getSimpleName();

    public static String RECIPE_EXTRAS = "recipes_extras";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RECIPE_EXTRAS = this.getResources().getString(R.string.recipe_extras);

        this.setTitle("Recipe List");
        if (savedInstanceState == null) {

        }
    }

}
