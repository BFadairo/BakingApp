package com.example.android.bakingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ingredient implements Serializable {

    @SerializedName("quantity")
    @Expose
    private double quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    /**
     * No args constructor for use in serialization
     */
    public Ingredient() {
    }

    /**
     * @param measure size for the Ingredient
     * @param ingredient name of the ingredient
     * @param quantity amount of the ingredient
     */
    public Ingredient(Double quantity, String measure, String ingredient) {
        super();
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    /**
     * Getter used to get Ingredient Quantity
     */
    public double getIngredientQuantity() {
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
