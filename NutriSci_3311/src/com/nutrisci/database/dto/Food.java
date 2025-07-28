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
    
    /**
     * Gets the unique food identifier from the CNF database.
     * 
     * @return the food ID as used in the Canadian Nutrient File
     */
    public int getFoodId() { return foodId; }
    
    /**
     * Sets the unique food identifier from the CNF database.
     * This ID corresponds to the FoodID in the FOOD_NAME table.
     * 
     * @param foodId the food ID to set
     */
    public void setFoodId(int foodId) { this.foodId = foodId; }
    
    /**
     * Gets the human-readable name of the food.
     * 
     * @return the food description/name from the CNF database
     */
    public String getFoodName() { return foodName; }
    
    /**
     * Sets the human-readable name of the food.
     * This corresponds to the FoodDescription field in the FOOD_NAME table.
     * 
     * @param foodName the food name to set
     */
    public void setFoodName(String foodName) { this.foodName = foodName; }
    
    /**
     * Gets the list of nutrients and their values for this food.
     * 
     * @return list of food nutrients, never null
     */
    public List<FoodNutrient> getNutrients() { return nutrients; }
    
    /**
     * Sets the list of nutrients and their values for this food.
     * If null is provided, an empty list is created to prevent null pointer exceptions.
     * 
     * @param nutrients the list of food nutrients to set
     */
    public void setNutrients(List<FoodNutrient> nutrients) { 
        this.nutrients = nutrients != null ? nutrients : new ArrayList<>(); 
    }
    
    /**
     * Adds a single nutrient value to this food.
     * Convenience method for building food objects incrementally.
     * 
     * @param nutrient the food nutrient to add
     */
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