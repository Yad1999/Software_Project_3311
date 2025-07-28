package loginprofile.test;

import java.util.HashMap;
import java.util.Map;

public class MockNutritionDB {

    private HashMap<String, NutritionInfo> nutritionMap;

    public MockNutritionDB() {
        nutritionMap = new HashMap<>();
        loadMockData();
    }

    private void loadMockData() {
        nutritionMap.put("apple", new NutritionInfo(52, 0.3, 14, 0.2));
        nutritionMap.put("banana", new NutritionInfo(96, 1.3, 27, 0.3));
        nutritionMap.put("bread", new NutritionInfo(265, 9, 49, 3.2));
        nutritionMap.put("egg", new NutritionInfo(155, 13, 1.1, 11));
        nutritionMap.put("chicken", new NutritionInfo(239, 27, 0, 14));
        nutritionMap.put("tomato", new NutritionInfo(18, 0.9, 3.9, 0.2));
        // Add more food items as needed
    }

    public NutritionInfo getNutrition(String foodItem) {
        return nutritionMap.getOrDefault(foodItem.toLowerCase(), null);
    }
}  

class NutritionInfo {
    private double calories;
    private double protein;
    private double carbs;
    private double fat;

    public NutritionInfo(double calories, double protein, double carbs, double fat) {
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
} 
