package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private final List<Ingredient> ingredientList;

    public IngredientAdapter(Context context, List<Ingredient> ingredients) {
        Context mContext = context;
        this.ingredientList = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        //Create a new view
        LayoutInflater inflater = LayoutInflater.from(context);
        View textView =
                inflater.inflate(R.layout.ingredient_list, parent, false);


        return new ViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Ingredient currentIngredient = ingredientList.get(position);

        TextView ingredientName = holder.ingredientName;

        TextView ingredientQuantity = holder.ingredientQuantity;

        TextView ingredientMeasure = holder.ingredientMeasurement;

        ingredientName.setText(currentIngredient.getRecipeIngredient());

        ingredientQuantity.setText(String.valueOf(currentIngredient.getIngredientQuantity()));

        ingredientMeasure.setText(currentIngredient.getMeasureQuantiity());

    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView ingredientName;
        final TextView ingredientQuantity;
        final TextView ingredientMeasurement;
        final View layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            ingredientName = itemView.findViewById(R.id.ingredient_view);
            ingredientQuantity = itemView.findViewById(R.id.quantity_view);
            ingredientMeasurement = itemView.findViewById(R.id.measure_view);
        }
    }
}
