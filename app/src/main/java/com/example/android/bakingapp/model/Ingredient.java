package com.example.android.bakingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("quantity")
    @Expose
    public Integer quantity;
    @SerializedName("measure")
    @Expose
    public String measure;
    @SerializedName("ingredient")
    @Expose
    public String ingredient;

    /**
     * No args constructor for use in serialization
     */
    public Ingredient() {
    }

    /**
     * @param measure
     * @param ingredient
     * @param quantity
     */
    public Ingredient(Integer quantity, String measure, String ingredient) {
        super();
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    /**
     * Getter used to get Ingredient Quantity
     */
    public int getIngredientQuantity() {
        return quantity;
    }

    /**
     * Getter used to get Measure Quantity
     */
    public String getMeasureQuantiity() {
        return measure;
    }

    /**
     * Getter used to get Recipe Ingredient
     */
    public String getRecipeIngredient() {
        return ingredient;
    }
}
