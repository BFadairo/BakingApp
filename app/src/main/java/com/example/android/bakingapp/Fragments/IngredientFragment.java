package com.example.android.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.android.bakingapp.MainActivity.RECIPE_EXTRAS;

public class IngredientFragment extends Fragment {

    //Variables used to store Recipe information
    Recipe recipe;
    //Variables to store view fields
    private TextView quantityView, measureView, ingredientView;

    public IngredientFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        ingredientView = rootView.findViewById(R.id.ingredient_view);
        quantityView = rootView.findViewById(R.id.quantity_view);
        measureView = rootView.findViewById(R.id.measure_view);

        populateUI();

        return rootView;
    }

    public void populateUI() {
        recipe = getActivity().getIntent().getParcelableExtra(RECIPE_EXTRAS);

        List<Ingredient> ingredientsLiist = recipe.getIngredients();

        DecimalFormat decimalFormat = new DecimalFormat("0.0");

        for (int i = 0; i < ingredientsLiist.size(); i++) {
            Ingredient ingredient = ingredientsLiist.get(i);
            ingredientView.append(ingredient.getRecipeIngredient() + "\n");
            double ingredientQuant = ingredient.getIngredientQuantity();
            quantityView.append(ingredientQuant + "\n");
            measureView.append(ingredient.getMeasureQuantiity() + "\n");
        }
    }

}