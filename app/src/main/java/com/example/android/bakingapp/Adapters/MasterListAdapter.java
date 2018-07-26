package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.ViewHolder> {

    private final static String LOG_TAG = MasterListAdapter.class.getSimpleName();

    // Keeps track of the context and images
    private Context mContext;
    private List<Recipe> mRecipes;
    private AdapterOnClick clickHandler;

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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        //Create a new view
        LayoutInflater inflater = LayoutInflater.from(context);
        View textView =
                inflater.inflate(R.layout.recipe_list, parent, false);

        return new ViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe currentRecipe = mRecipes.get(position);

        TextView recipeName = holder.recipeName;

        ImageView recipeImage = holder.recipeImage;

        ArrayList<Integer> listImages = new ArrayList<>();

        listImages.add(R.drawable.brownies);

        listImages.add(R.drawable.cheesecake);


        // TODO: To Be Implemented once other images for Recipes are found
//        int imagePosition = listImages.get(position);

        //recipeImage.setImageResource(imagePosition);
        recipeName.setText(currentRecipe.getRecipeName());
/*        switch (currentRecipe.getRecipeName()) {
            case "Brownies":
                recipeImage.setImageResource(R.drawable.brownies);
                break;
            case "Nutella Pie":
                break;
            case "Yellow Cake":
                break;
            case "Cheesecake":
                break;
        }*/
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
