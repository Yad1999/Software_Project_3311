package com.nutrisci.database.dto;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing a meal item (food with quantity) within a logged meal.
 */
public class MealItem {
    
    private int itemId;
    private int mealId;
    private int foodId;
    private BigDecimal quantityGrams;
    private String foodName; // For display purposes when joining with FOOD_NAME table
    
    // Default constructor
    public MealItem() {}
    
    // Constructor for creating new meal item (without itemId)
    public MealItem(int mealId, int foodId, BigDecimal quantityGrams) {
        this.mealId = mealId;
        this.foodId = foodId;
        this.quantityGrams = quantityGrams;
    }
    
    // Constructor with food name for display
    public MealItem(int mealId, int foodId, BigDecimal quantityGrams, String foodName) {
        this.mealId = mealId;
        this.foodId = foodId;
        this.quantityGrams = quantityGrams;
        this.foodName = foodName;
    }
    
    // Full constructor
    public MealItem(int itemId, int mealId, int foodId, BigDecimal quantityGrams, String foodName) {
        this.itemId = itemId;
        this.mealId = mealId;
        this.foodId = foodId;
        this.quantityGrams = quantityGrams;
        this.foodName = foodName;
    }
    
    // Getters and Setters
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    
    public int getMealId() { return mealId; }
    public void setMealId(int mealId) { this.mealId = mealId; }
    
    public int getFoodId() { return foodId; }
    public void setFoodId(int foodId) { this.foodId = foodId; }
    
    public BigDecimal getQuantityGrams() { return quantityGrams; }
    public void setQuantityGrams(BigDecimal quantityGrams) { this.quantityGrams = quantityGrams; }
    
    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }
    
    @Override
    public String toString() {
        return "MealItem{" +
                "itemId=" + itemId +
                ", mealId=" + mealId +
                ", foodId=" + foodId +
                ", quantityGrams=" + quantityGrams +
                ", foodName='" + foodName + '\'' +
                '}';
    }
}