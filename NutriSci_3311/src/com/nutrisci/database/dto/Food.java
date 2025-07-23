package com.nutrisci.database.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object representing a food item from the Canadian Nutrient File (CNF) database.
 */
public class Food {
    
    private int foodId;
    private String foodName;
    private List<FoodNutrient> nutrients;
    
    // Default constructor
    public Food() {
        this.nutrients = new ArrayList<>();
    }
    
    // Constructor with basic food info
    public Food(int foodId, String foodName) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.nutrients = new ArrayList<>();
    }
    
    // Full constructor
    public Food(int foodId, String foodName, List<FoodNutrient> nutrients) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.nutrients = nutrients != null ? nutrients : new ArrayList<>();
    }
    
    // Getters and Setters
    public int getFoodId() { return foodId; }
    public void setFoodId(int foodId) { this.foodId = foodId; }
    
    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }
    
    public List<FoodNutrient> getNutrients() { return nutrients; }
    public void setNutrients(List<FoodNutrient> nutrients) { 
        this.nutrients = nutrients != null ? nutrients : new ArrayList<>(); 
    }
    
    public void addNutrient(FoodNutrient nutrient) {
        this.nutrients.add(nutrient);
    }
    
    @Override
    public String toString() {
        return "Food{" +
                "foodId=" + foodId +
                ", foodName='" + foodName + '\'' +
                ", nutrients=" + nutrients +
                '}';
    }
}