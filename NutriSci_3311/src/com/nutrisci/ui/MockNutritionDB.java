package com.nutrisci.ui;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code MockNutritionDB} class simulates a lightweight nutrition database
 * for use in prototyping and testing the NutriSci application.
 *
 * <p>It maps food item names (as lowercase strings) to basic nutritional information
 * such as calories, protein, carbohydrates, and fat. This mock database is not
 * connected to an actual data source and should be replaced in production with a real backend.</p>
 *
 * <p>Nutrition data is represented using the {@link NutritionInfo} inner class.</p>
 */
public class MockNutritionDB {

    /** A map of food item names to their nutritional information. */
    private HashMap<String, NutritionInfo> nutritionMap;

    /**
     * Constructs a new mock nutrition database and loads hardcoded nutritional data.
     */
    public MockNutritionDB() {
        nutritionMap = new HashMap<>();
        loadMockData();
    }

    /**
     * Loads mock nutrition values for a set of common food items.
     * The values used are approximate and for demonstration purposes only.
     */
    private void loadMockData() {
        nutritionMap.put("apple", new NutritionInfo(52, 0.3, 14, 0.2));
        nutritionMap.put("banana", new NutritionInfo(96, 1.3, 27, 0.3));
        nutritionMap.put("bread", new NutritionInfo(265, 9, 49, 3.2));
        nutritionMap.put("egg", new NutritionInfo(155, 13, 1.1, 11));
        nutritionMap.put("chicken", new NutritionInfo(239, 27, 0, 14));
        nutritionMap.put("tomato", new NutritionInfo(18, 0.9, 3.9, 0.2));
        // Additional food items can be added here as needed.
    }

    /**
     * Retrieves the nutritional information for a given food item.
     *
     * @param foodItem the name of the food item (case-insensitive).
     * @return a {@link NutritionInfo} object containing nutrient values, or {@code null} if not found.
     */
    public NutritionInfo getNutrition(String foodItem) {
        return nutritionMap.getOrDefault(foodItem.toLowerCase(), null);
    }
}

/**
 * The {@code NutritionInfo} class holds basic nutrient values for a food item.
 *
 * <p>Each instance represents the approximate values per 100 grams:
 * - calories (kcal)
 * - protein (g)
 * - carbohydrates (g)
 * - fat (g)</p>
 */
class NutritionInfo {

    private double calories;
    private double protein;
    private double carbs;
    private double fat;

    /**
     * Constructs a {@code NutritionInfo} object with the given nutrient values.
     *
     * @param calories the number of calories in kcal.
     * @param protein  the amount of protein in grams.
     * @param carbs    the amount of carbohydrates in grams.
     * @param fat      the amount of fat in grams.
     */
    public NutritionInfo(double calories, double protein, double carbs, double fat) {
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    /**
     * Returns the calorie content in kcal.
     *
     * @return the calorie value.
     */
    public double getCalories() {
        return calories;
    }

    /**
     * Returns the protein content in grams.
     *
     * @return the protein value.
     */
    public double getProtein() {
        return protein;
    }

    /**
     * Returns the carbohydrate content in grams.
     *
     * @return the carbohydrate value.
     */
    public double getCarbs() {
        return carbs;
    }

    /**
     * Returns the fat content in grams.
     *
     * @return the fat value.
     */
    public double getFat() {
        return fat;
    }
}
