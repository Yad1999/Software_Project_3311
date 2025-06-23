package controller;

import model.FoodItem;
import model.Meal;

import java.util.*;

public class NutritionCalculator {

    public static class NutritionSummary {
        public double calories = 0;
        public double protein = 0;
        public double fat = 0;
        public double carbs = 0;
        public double fiber = 0;

        public void add(FoodItem item) {
            double q = item.getQuantity();
            this.calories += item.getCalories() * q;
            this.protein += item.getProtein() * q;
            this.fat += item.getFat() * q;
            this.carbs += item.getCarbs() * q;
            this.fiber += item.getFiber() * q;
        }

        public void add(NutritionSummary other) {
            this.calories += other.calories;
            this.protein += other.protein;
            this.fat += other.fat;
            this.carbs += other.carbs;
            this.fiber += other.fiber;
        }

        @Override
        public String toString() {
            return String.format(
                    "Calories: %.2f, Protein: %.2fg, Fat: %.2fg, Carbs: %.2fg, Fiber: %.2fg",
                    calories, protein, fat, carbs, fiber
            );
        }
    }

    // 1. Calculate a single meal’s nutrition
    public static NutritionSummary calculateMealNutrition(Meal meal) {
        NutritionSummary summary = new NutritionSummary();
        for (FoodItem item : meal.getFoodItems()) {
            summary.add(item);
        }
        return summary;
    }

    // 2. Calculate total nutrition across multiple meals
    public static NutritionSummary calculateTotalNutrition(List<Meal> meals) {
        NutritionSummary total = new NutritionSummary();
        for (Meal meal : meals) {
            total.add(calculateMealNutrition(meal));
        }
        return total;
    }

    // 3. Get nutrition per day (grouped by date string)
    public static Map<String, NutritionSummary> calculateDailyTotals(List<Meal> meals) {
        Map<String, NutritionSummary> dailyMap = new HashMap<>();
        for (Meal meal : meals) {
            String date = meal.getDate();
            dailyMap.putIfAbsent(date, new NutritionSummary());
            NutritionSummary summary = calculateMealNutrition(meal);
            dailyMap.get(date).add(summary);
        }
        return dailyMap;
    }

    // Main method for testing
    public static void main(String[] args) {

        FoodItem apple = new FoodItem("Apple", 1.0, 0.52, 0.03, 0.17, 0.024, 0.14);
        FoodItem bread = new FoodItem("Bread", 2.0, 2.6, 0.09, 0.11, 0.08, 0.5);

        Meal breakfast = new Meal("2025-06-20", "breakfast");
        breakfast.addFoodItem(apple);
        breakfast.addFoodItem(bread);

        NutritionSummary result = calculateMealNutrition(breakfast);
        System.out.println("Meal Nutrition: " + result);
    }
}

