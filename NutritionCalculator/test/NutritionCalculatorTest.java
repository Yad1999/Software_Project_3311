package test;

import controller.NutritionCalculator;
import controller.NutritionCalculator.NutritionSummary;
import model.FoodItem;
import model.Meal;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NutritionCalculatorTest {

    @Test
    public void testSingleMealNutritionCalculation() {
        FoodItem egg = new FoodItem("Egg", 1.0, 1.43, 0.13, 0.10, 0.01, 0.01);
        FoodItem toast = new FoodItem("Toast", 2.0, 2.0, 0.06, 0.02, 0.01, 0.20);

        Meal breakfast = new Meal("2025-06-22", "breakfast");
        breakfast.addFoodItem(egg);
        breakfast.addFoodItem(toast);

        NutritionSummary summary = NutritionCalculator.calculateMealNutrition(breakfast);

        assertEquals(5.43, summary.calories, 0.001);
        assertEquals(0.25, summary.protein, 0.001);
        assertEquals(0.14, summary.fat, 0.001);
        assertEquals(0.03, summary.fiber, 0.001);
        assertEquals(0.41, summary.carbs, 0.001);
    }

    @Test
    public void testTotalNutritionMultipleMeals() {
        FoodItem apple = new FoodItem("Apple", 1.0, 0.52, 0.03, 0.17, 0.02, 0.14);
        FoodItem rice = new FoodItem("Rice", 1.5, 1.3, 0.02, 0.01, 0.005, 0.28);

        Meal lunch = new Meal("2025-06-21", "lunch");
        lunch.addFoodItem(apple);
        lunch.addFoodItem(rice);

        Meal dinner = new Meal("2025-06-21", "dinner");
        dinner.addFoodItem(rice);

        List<Meal> meals = Arrays.asList(lunch, dinner);
        NutritionSummary total = NutritionCalculator.calculateTotalNutrition(meals);

        assertEquals(3.72, total.calories, 0.001);
        assertEquals(0.07, total.protein, 0.001);
        assertEquals(0.20, total.fat, 0.001);
        assertEquals(0.035, total.fiber, 0.001);
        assertEquals(0.70, total.carbs, 0.001);
    }

    @Test
    public void testDailyTotalsGroupedByDate() {
        FoodItem yogurt = new FoodItem("Yogurt", 1.0, 1.0, 0.04, 0.03, 0.01, 0.10);

        Meal m1 = new Meal("2025-06-20", "breakfast");
        m1.addFoodItem(yogurt);

        Meal m2 = new Meal("2025-06-20", "dinner");
        m2.addFoodItem(yogurt);

        Meal m3 = new Meal("2025-06-21", "lunch");
        m3.addFoodItem(yogurt);

        var dailyTotals = NutritionCalculator.calculateDailyTotals(Arrays.asList(m1, m2, m3));

        assertEquals(2.0, dailyTotals.get("2025-06-20").calories, 0.001);
        assertEquals(1.0, dailyTotals.get("2025-06-21").calories, 0.001);
    }
}

