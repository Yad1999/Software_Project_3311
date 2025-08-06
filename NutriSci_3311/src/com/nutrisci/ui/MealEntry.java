package com.nutrisci.ui;

import java.util.List;

/**
 * Represents a single meal entry containing nutritional data and a list of ingredients.
 *
 * <p><b>Note:</b> This class is currently used for testing and demonstration purposes only.
 * It is not integrated into the UI, but it serves as a reference for how a potential
 * backend model might represent a logged meal.</p>
 */
public class MealEntry {

    /** The date of the meal in the format YYYY-MM-DD. */
    private String date;

    /** The type of meal (e.g., Breakfast, Lunch, Dinner, Snack). */
    private String mealType;

    /** A list of ingredient entries associated with the meal. */
    private List<IngredientEntry> ingredients;

    /** The nutritional profile of the meal. */
    private NutrientProfile nutrientProfile;

    /**
     * Constructs a {@code MealEntry} with the specified date, meal type,
     * ingredient list, and nutrient profile.
     *
     * @param date the date of the meal (YYYY-MM-DD)
     * @param mealType the type of the meal (e.g., "Lunch")
     * @param ingredients list of {@link IngredientEntry} instances in the meal
     * @param nutrientProfile the {@link NutrientProfile} containing nutrient totals
     */
    public MealEntry(String date, String mealType, List<IngredientEntry> ingredients, NutrientProfile nutrientProfile) {
        this.date = date;
        this.mealType = mealType;
        this.ingredients = ingredients;
        this.nutrientProfile = nutrientProfile;
    }

    /**
     * Returns the date of the meal.
     *
     * @return the meal date in format YYYY-MM-DD
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the type of the meal.
     *
     * @return the meal type (e.g., "Dinner")
     */
    public String getMealType() {
        return mealType;
    }

    /**
     * Returns the list of ingredients for this meal.
     *
     * @return list of {@link IngredientEntry} objects
     */
    public List<IngredientEntry> getIngredients() {
        return ingredients;
    }

    /**
     * Returns the nutrient profile for the meal.
     *
     * @return {@link NutrientProfile} object
     */
    public NutrientProfile getNutrientProfile() {
        return nutrientProfile;
    }

    /**
     * Returns the total calories for the meal.
     *
     * @return total calories
     */
    public double getTotalCalories() {
        return nutrientProfile.getCalories();
    }

    /**
     * Returns the total protein for the meal.
     *
     * @return total protein in grams
     */
    public double getTotalProtein() {
        return nutrientProfile.getProtein();
    }

    /**
     * Returns the total carbohydrates for the meal.
     *
     * @return total carbs in grams
     */
    public double getTotalCarbs() {
        return nutrientProfile.getCarbs();
    }

    /**
     * Returns the total fat for the meal.
     *
     * @return total fat in grams
     */
    public double getTotalFat() {
        return nutrientProfile.getFat();
    }

    /**
     * Returns a string representation of the meal entry including date,
     * meal type, and total calories.
     *
     * @return a summary string of the meal
     */
    @Override
    public String toString() {
        return String.format("%s - %s: %.2f kcal", date, mealType, getTotalCalories());
    }
}
