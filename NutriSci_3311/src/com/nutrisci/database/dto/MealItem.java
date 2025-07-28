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
    
    /**
     * Gets the unique meal item identifier.
     * 
     * @return the item ID, or 0 if not yet assigned
     */
    public int getItemId() { return itemId; }
    
    /**
     * Sets the unique meal item identifier.
     * This is typically set automatically by the database when adding items to a meal.
     * 
     * @param itemId the item ID to set
     */
    public void setItemId(int itemId) { this.itemId = itemId; }
    
    /**
     * Gets the ID of the meal this item belongs to.
     * 
     * @return the meal ID this item is associated with
     */
    public int getMealId() { return mealId; }
    
    /**
     * Sets the ID of the meal this item belongs to.
     * Used to establish the relationship between meal items and their parent meal.
     * 
     * @param mealId the meal ID to set
     */
    public void setMealId(int mealId) { this.mealId = mealId; }
    
    /**
     * Gets the ID of the food from the CNF database.
     * 
     * @return the food ID referencing the Canadian Nutrient File
     */
    public int getFoodId() { return foodId; }
    
    /**
     * Sets the ID of the food from the CNF database.
     * This creates a reference to nutritional information in the food database.
     * 
     * @param foodId the food ID to set
     */
    public void setFoodId(int foodId) { this.foodId = foodId; }
    
    /**
     * Gets the quantity of food consumed in grams.
     * 
     * @return the quantity in grams as a precise decimal value
     */
    public BigDecimal getQuantityGrams() { return quantityGrams; }
    
    /**
     * Sets the quantity of food consumed in grams.
     * Used for accurate nutritional calculations based on portion size.
     * 
     * @param quantityGrams the quantity in grams to set
     */
    public void setQuantityGrams(BigDecimal quantityGrams) { this.quantityGrams = quantityGrams; }
    
    /**
     * Gets the human-readable name of the food.
     * This is typically populated when joining with the FOOD_NAME table for display purposes.
     * 
     * @return the food name, or null if not loaded from database
     */
    public String getFoodName() { return foodName; }
    
    /**
     * Sets the human-readable name of the food.
     * Used for UI display to show meaningful food names instead of just IDs.
     * 
     * @param foodName the food name to set
     */
    public void setFoodName(String foodName) { this.foodName = foodName; }
    
    /**
     * Returns a string representation of the MealItem object.
     * Includes item details, food reference, quantity, and name if available.
     * 
     * @return string representation of the meal item
     */
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