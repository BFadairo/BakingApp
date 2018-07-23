package com.example.android.bakingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("ingredients")
    @Expose
    public List<Ingredient> ingredients = new ArrayList<>();
    @SerializedName("steps")
    @Expose
    public List<Step> steps = new ArrayList<>();
    @SerializedName("servings")
    @Expose
    public Integer servings;
    @SerializedName("image")
    @Expose
    public String image;

    /**
     * No args constructor for use in serialization
     */
    public Recipe() {
    }

    /**
     * @param ingredients
     * @param id
     * @param servings
     * @param name
     * @param image
     * @param steps
     */
    public Recipe(Integer id, String name, List<Ingredient> ingredients, List<Step> steps, Integer servings, String image) {
        super();
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    /**
     * Getter used to get Recipe ID
     */
    public int getRecipeId() {
        return id;
    }

    /**
     * Getter used to get Recipe Name
     */
    public String getRecipeName() {
        return name;
    }

    /**
     * Getter used to get the Ingredient List
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Used to get the steps of the recipe
     */
    public List<Step> getSteps() {
        return steps;
    }

    /**
     * Used to get the serving size of the recipe
     */
    public int getServingSize() {
        return servings;
    }

    /**
     * Used to get the Image of the food item
     */
    public String getFoodImage() {
        return image;
    }
}
