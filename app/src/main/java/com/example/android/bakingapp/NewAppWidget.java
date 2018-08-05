package com.example.android.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;

import java.util.ArrayList;

import static com.example.android.bakingapp.MainActivity.RECIPE_EXTRAS;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    private static final String LOG_TAG = NewAppWidget.class.getSimpleName();

    private static Recipe intentRecipe;

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Recipe recipe) {

        //Create variables to store the recipe names
        if (recipe != null) {
            intentRecipe = recipe;
            //For the sake of time, use a String Builder to create a string with the below information
            String recipeName = recipe.getRecipeName();
            String ingredientName;
            String ingredientQuantity;
            String ingredientMeasure;
            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<Ingredient> ingredients = recipe.getIngredients();
            //Loop through the array to retrieve all of the values
            for (int i = 0; i < ingredients.size(); i++) {
                ingredientName = ingredients.get(i).getRecipeIngredient();
                ingredientQuantity = String.valueOf(ingredients.get(i).getIngredientQuantity());
                ingredientMeasure = ingredients.get(i).getMeasureQuantiity();
                stringBuilder.append(ingredientQuantity + " " + ingredientMeasure + " " + ingredientName + "\n");
            }
            Log.v(LOG_TAG, stringBuilder.toString());

            //Create a new Intent to Launch the DetailActivity when Clicked
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(RECIPE_EXTRAS, recipe);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            Log.v("Widget Activity", "Recipe: " + recipe);
            //Set the text of the remote views to the example Text
            views.setTextViewText(R.id.widget_recipe_name, recipeName);
            views.setTextViewText(R.id.widget_ingredient_list, stringBuilder.toString());
            //Set the On Click pending Intent to the Recipe name
            views.setOnClickPendingIntent(R.id.widget_recipe_name, pendingIntent);


            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, intentRecipe);
        }
        Log.v(LOG_TAG, "OnUpdated Called");

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        //Check if the action in the intent is Updating the Widget
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            //Get the Extras from the intent and put them into a Bundle
            Bundle bundle = intent.getExtras();
            //Get the Widget Ids for the Application
            int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, NewAppWidget.class));
            intentRecipe = bundle.getParcelable("Extra");
            //this.onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
            Log.v(LOG_TAG, "Eat Feed: " + intentRecipe);
            this.onUpdate(context, AppWidgetManager.getInstance(context), ids);
        }
    }
}

