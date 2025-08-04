package com.nutrisci.ui;

/**
 * The {@code IngredientEntry} class represents an individual ingredient used in a meal,
 * including its name and quantity in grams.
 *
 * <p>This class is typically used within a {@code MealEntry} or {@code MealLogUI}
 * to store and display detailed ingredient information for nutrient calculations.</p>
 */
public class IngredientEntry {

    /** The name of the ingredient (e.g., "apple", "bread"). */
    private String name;

    /** The quantity of the ingredient in grams. */
    private double quantityInGrams;

    /**
     * Constructs an {@code IngredientEntry} with the specified name and quantity.
     *
     * @param name the name of the ingredient.
     * @param quantityInGrams the amount of the ingredient in grams.
     */
    public IngredientEntry(String name, double quantityInGrams) {
        this.name = name;
        this.quantityInGrams = quantityInGrams;
    }

    /**
     * Returns the name of the ingredient.
     *
     * @return the ingredient name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the quantity of the ingredient in grams.
     *
     * @return the quantity in grams.
     */
    public double getQuantityInGrams() {
        return quantityInGrams;
    }

    /**
     * Returns a string representation of the ingredient entry.
     * Format: {@code "<name> - <quantity>g"} (e.g., "Apple - 100g").
     *
     * @return a human-readable string describing the ingredient.
     */
    @Override
    public String toString() {
        return name + " - " + quantityInGrams + "g";
    }
}
