package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.ViewHolder> {

    private final static String LOG_TAG = MasterListAdapter.class.getSimpleName();

    private Context mContext;
    private List<Recipe> mRecipes;
    private final AdapterOnClick clickHandler;

    public MasterListAdapter(Context mContext, List<Recipe> recipes, AdapterOnClick onClickHandler) {
        this.mContext = mContext;
        this.mRecipes = recipes;
        this.clickHandler = onClickHandler;
    }

    public void setRecipeData(List<Recipe> recipes) {
        this.mRecipes = recipes;
        //Log.v(LOG_TAG, recipes + "");
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        //Create a new view
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View textView =
                inflater.inflate(R.layout.recipe_list, parent, false);

        return new ViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe currentRecipe = mRecipes.get(position);

        TextView recipeName = holder.recipeName;

        ImageView recipeImage = holder.recipeImage;

        if (!(currentRecipe.getFoodImage().isEmpty())) {
            Picasso.get().load(currentRecipe.getFoodImage()).into(recipeImage);
            recipeImage.setVisibility(View.VISIBLE);
        } else {
            recipeImage.setVisibility(View.GONE);
        }
        recipeName.setText(currentRecipe.getRecipeName());
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public interface AdapterOnClick {

        void onClick(Recipe recipe);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView recipeName;
        final ImageView recipeImage;
        final View layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeImage = itemView.findViewById(R.id.recipe_master_image);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipes.get(adapterPosition);
            clickHandler.onClick(recipe);
        }
    }
}
