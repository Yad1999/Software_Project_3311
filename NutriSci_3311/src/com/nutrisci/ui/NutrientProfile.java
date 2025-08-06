package com.nutrisci.ui;

/**
 * Represents a nutritional profile consisting of calories, protein, carbs, and fat.
 */
public class NutrientProfile {

    private double calories;
    private double protein;
    private double carbs;
    private double fat;

    /**
     * Constructs a {@code NutrientProfile} with the specified nutrient values.
     *
     * @param calories total calories
     * @param protein total protein in grams
     * @param carbs total carbohydrates in grams
     * @param fat total fat in grams
     */
    public NutrientProfile(double calories, double protein, double carbs, double fat) {
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public double getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFat() {
        return fat;
    }

    @Override
    public String toString() {
        return String.format("Calories: %.2f, Protein: %.2fg, Carbs: %.2fg, Fat: %.2fg",
                calories, protein, carbs, fat);
    }
}
