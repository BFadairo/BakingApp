package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.android.bakingapp.Fragments.IngredientFragment;
import com.example.android.bakingapp.model.Recipe;

import static com.example.android.bakingapp.MainActivity.RECIPE_EXTRAS;

public class DetailActivity extends AppCompatActivity {

    private final static String LOG_TAG = DetailActivity.class.getSimpleName();
    Recipe recipe;
    private TextView quantityView, measureView, ingredientView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Get the intent that started the Activity and put it into a Movie Object
        if (savedInstanceState == null) {
            recipe = getIntent().getParcelableExtra(RECIPE_EXTRAS);

            //Create a new Ingredient Fragment
            IngredientFragment ingredientFragment = new IngredientFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_container, ingredientFragment)
                    .commit();
        }
        Log.v(LOG_TAG, recipe.getRecipeName());
    }

    public void populateUI() {
        IngredientFragment fragment = new IngredientFragment();
    }
}
