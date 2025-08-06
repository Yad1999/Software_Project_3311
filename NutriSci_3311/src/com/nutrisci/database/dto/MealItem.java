package com.nutrisci.database.dto;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing a meal item (food with quantity) within a logged meal.
 * Uses Builder pattern to handle complex object construction with many parameters.
 */
public class MealItem {
    
    private int itemId;
    private int mealId;
    private int foodId;
    private BigDecimal quantityGrams;
    private String foodName; // For display purposes when joining with FOOD_NAME table
    
    // Private constructor for Builder pattern
    private MealItem(Builder builder) {
        this.itemId = builder.itemId;
        this.mealId = builder.mealId;
        this.foodId = builder.foodId;
        this.quantityGrams = builder.quantityGrams;
        this.foodName = builder.foodName;
    }
    
    // Default constructor for frameworks/serialization
    public MealItem() {}
    
    /**
     * Creates a new Builder instance for constructing MealItem objects.
     * 
     * @return new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }
    
    /**
     * Builder class for constructing MealItem objects with fluent interface.
     * Follows the Builder pattern to handle complex object construction.
     */
    public static class Builder {
        private int itemId;
        private int mealId;
        private int foodId;
        private BigDecimal quantityGrams;
        private String foodName;
        
        private Builder() {}
        
        /**
         * Sets the meal item ID (typically for existing items from database).
         * 
         * @param itemId the meal item ID
         * @return this builder for method chaining
         */
        public Builder itemId(int itemId) {
            this.itemId = itemId;
            return this;
        }
        
        /**
         * Sets the meal ID this item belongs to (required field).
         * 
         * @param mealId the meal ID
         * @return this builder for method chaining
         */
        public Builder mealId(int mealId) {
            this.mealId = mealId;
            return this;
        }
        
        /**
         * Sets the food ID from the CNF database (required field).
         * 
         * @param foodId the food ID
         * @return this builder for method chaining
         */
        public Builder foodId(int foodId) {
            this.foodId = foodId;
            return this;
        }
        
        /**
         * Sets the quantity in grams (required field).
         * 
         * @param quantityGrams the quantity in grams
         * @return this builder for method chaining
         */
        public Builder quantityGrams(BigDecimal quantityGrams) {
            this.quantityGrams = quantityGrams;
            return this;
        }
        
        /**
         * Sets the quantity in grams using double value (convenience method).
         * 
         * @param quantityGrams the quantity in grams as double
         * @return this builder for method chaining
         */
        public Builder quantityGrams(double quantityGrams) {
            this.quantityGrams = BigDecimal.valueOf(quantityGrams);
            return this;
        }
        
        /**
         * Sets the food name for display purposes.
         * 
         * @param foodName the display name of the food
         * @return this builder for method chaining
         */
        public Builder foodName(String foodName) {
            this.foodName = foodName;
            return this;
        }
        
        /**
         * Builds and validates the MealItem object.
         * 
         * @return new MealItem instance
         * @throws IllegalStateException if required fields are missing
         */
        public MealItem build() {
            validateRequiredFields();
            return new MealItem(this);
        }
        
        /**
         * Validates that all required fields are set.
         * 
         * @throws IllegalStateException if validation fails
         */
        private void validateRequiredFields() {
            if (mealId <= 0) {
                throw new IllegalStateException("Valid meal ID is required");
            }
            if (foodId <= 0) {
                throw new IllegalStateException("Valid food ID is required");
            }
            if (quantityGrams == null || quantityGrams.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalStateException("Valid quantity (greater than 0) is required");
            }
        }
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