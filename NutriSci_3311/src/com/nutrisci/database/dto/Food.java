package com.nutrisci.database.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object representing a food item from the Canadian Nutrient File (CNF) database.
 * Uses Builder pattern to handle complex object construction with many parameters.
 */
public class Food {
    
    private int foodId;
    private String foodName;
    private List<FoodNutrient> nutrients;
    
    // Private constructor for Builder pattern
    private Food(Builder builder) {
        this.foodId = builder.foodId;
        this.foodName = builder.foodName;
        this.nutrients = builder.nutrients != null ? builder.nutrients : new ArrayList<>();
    }
    
    // Default constructor for frameworks/serialization
    public Food() {
        this.nutrients = new ArrayList<>();
    }
    
    /**
     * Creates a new Builder instance for constructing Food objects.
     * 
     * @return new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }
    
    /**
     * Builder class for constructing Food objects with fluent interface.
     * Follows the Builder pattern to handle complex object construction.
     */
    public static class Builder {
        private int foodId;
        private String foodName;
        private List<FoodNutrient> nutrients;
        
        private Builder() {
            this.nutrients = new ArrayList<>();
        }
        
        /**
         * Sets the food ID from the CNF database (required field).
         * 
         * @param foodId the food ID from Canadian Nutrient File
         * @return this builder for method chaining
         */
        public Builder foodId(int foodId) {
            this.foodId = foodId;
            return this;
        }
        
        /**
         * Sets the food name/description (required field).
         * 
         * @param foodName the food name or description
         * @return this builder for method chaining
         */
        public Builder foodName(String foodName) {
            this.foodName = foodName;
            return this;
        }
        
        /**
         * Sets the list of nutrients for this food.
         * 
         * @param nutrients the list of food nutrients
         * @return this builder for method chaining
         */
        public Builder nutrients(List<FoodNutrient> nutrients) {
            this.nutrients = nutrients != null ? new ArrayList<>(nutrients) : new ArrayList<>();
            return this;
        }
        
        /**
         * Adds a single nutrient to the food.
         * 
         * @param nutrient the nutrient to add
         * @return this builder for method chaining
         */
        public Builder addNutrient(FoodNutrient nutrient) {
            if (this.nutrients == null) {
                this.nutrients = new ArrayList<>();
            }
            this.nutrients.add(nutrient);
            return this;
        }
        
        /**
         * Builds and validates the Food object.
         * 
         * @return new Food instance
         * @throws IllegalStateException if required fields are missing
         */
        public Food build() {
            validateRequiredFields();
            return new Food(this);
        }
        
        /**
         * Validates that all required fields are set.
         * 
         * @throws IllegalStateException if validation fails
         */
        private void validateRequiredFields() {
            if (foodId <= 0) {
                throw new IllegalStateException("Valid food ID is required");
            }
            if (foodName == null || foodName.trim().isEmpty()) {
                throw new IllegalStateException("Food name is required");
            }
        }
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