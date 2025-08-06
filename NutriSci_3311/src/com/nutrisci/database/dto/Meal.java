package com.nutrisci.database.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object representing a logged meal with its associated meal items.
 * Uses Builder pattern to handle complex object construction with many parameters.
 */
public class Meal {
    
    private int mealId;
    private MealType mealType;
    private LocalDate mealDate;
    private int userId;
    private LocalDateTime loggedAt;
    private List<MealItem> mealItems;
    
    // Private constructor for Builder pattern
    private Meal(Builder builder) {
        this.mealId = builder.mealId;
        this.mealType = builder.mealType;
        this.mealDate = builder.mealDate;
        this.userId = builder.userId;
        this.loggedAt = builder.loggedAt;
        this.mealItems = builder.mealItems != null ? builder.mealItems : new ArrayList<>();
    }
    
    // Default constructor for frameworks/serialization
    public Meal() {
        this.mealItems = new ArrayList<>();
    }
    
    /**
     * Creates a new Builder instance for constructing Meal objects.
     * 
     * @return new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }
    
    /**
     * Builder class for constructing Meal objects with fluent interface.
     * Follows the Builder pattern to handle complex object construction.
     */
    public static class Builder {
        private int mealId;
        private MealType mealType;
        private LocalDate mealDate;
        private int userId;
        private LocalDateTime loggedAt;
        private List<MealItem> mealItems;
        
        private Builder() {
            this.mealItems = new ArrayList<>();
        }
        
        /**
         * Sets the meal ID (typically for existing meals from database).
         * 
         * @param mealId the meal ID
         * @return this builder for method chaining
         */
        public Builder mealId(int mealId) {
            this.mealId = mealId;
            return this;
        }
        
        /**
         * Sets the meal type (required field).
         * 
         * @param mealType the type of meal (breakfast, lunch, dinner, snack)
         * @return this builder for method chaining
         */
        public Builder mealType(MealType mealType) {
            this.mealType = mealType;
            return this;
        }
        
        /**
         * Sets the meal date (required field).
         * 
         * @param mealDate the date when the meal was consumed
         * @return this builder for method chaining
         */
        public Builder mealDate(LocalDate mealDate) {
            this.mealDate = mealDate;
            return this;
        }
        
        /**
         * Sets the user ID (required field).
         * 
         * @param userId the ID of the user who logged this meal
         * @return this builder for method chaining
         */
        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }
        
        /**
         * Sets the logged timestamp (typically set by database).
         * 
         * @param loggedAt when the meal was logged in the system
         * @return this builder for method chaining
         */
        public Builder loggedAt(LocalDateTime loggedAt) {
            this.loggedAt = loggedAt;
            return this;
        }
        
        /**
         * Sets the list of meal items.
         * 
         * @param mealItems the food items in this meal
         * @return this builder for method chaining
         */
        public Builder mealItems(List<MealItem> mealItems) {
            this.mealItems = mealItems != null ? new ArrayList<>(mealItems) : new ArrayList<>();
            return this;
        }
        
        /**
         * Adds a single meal item to the meal.
         * 
         * @param mealItem the meal item to add
         * @return this builder for method chaining
         */
        public Builder addMealItem(MealItem mealItem) {
            if (this.mealItems == null) {
                this.mealItems = new ArrayList<>();
            }
            this.mealItems.add(mealItem);
            return this;
        }
        
        /**
         * Builds and validates the Meal object.
         * 
         * @return new Meal instance
         * @throws IllegalStateException if required fields are missing
         */
        public Meal build() {
            validateRequiredFields();
            return new Meal(this);
        }
        
        /**
         * Validates that all required fields are set.
         * 
         * @throws IllegalStateException if validation fails
         */
        private void validateRequiredFields() {
            if (mealType == null) {
                throw new IllegalStateException("Meal type is required");
            }
            if (mealDate == null) {
                throw new IllegalStateException("Meal date is required");
            }
            if (userId <= 0) {
                throw new IllegalStateException("Valid user ID is required");
            }
        }
    }
    
    // Getters and Setters
    
    /**
     * Gets the unique meal identifier.
     * 
     * @return the meal ID, or 0 if not yet assigned
     */
    public int getMealId() { return mealId; }
    
    /**
     * Sets the unique meal identifier.
     * This is typically set automatically by the database when logging a new meal.
     * 
     * @param mealId the meal ID to set
     */
    public void setMealId(int mealId) { this.mealId = mealId; }
    
    /**
     * Gets the type of meal (breakfast, lunch, dinner, snack).
     * 
     * @return the meal type enum value
     */
    public MealType getMealType() { return mealType; }
    
    /**
     * Sets the type of meal.
     * Used for categorizing meals and nutritional analysis.
     * 
     * @param mealType the meal type to set
     */
    public void setMealType(MealType mealType) { this.mealType = mealType; }
    
    /**
     * Gets the date when the meal was consumed.
     * 
     * @return the meal date
     */
    public LocalDate getMealDate() { return mealDate; }
    
    /**
     * Sets the date when the meal was consumed.
     * Used for daily nutritional tracking and meal history.
     * 
     * @param mealDate the meal date to set
     */
    public void setMealDate(LocalDate mealDate) { this.mealDate = mealDate; }
    
    /**
     * Gets the ID of the user who logged this meal.
     * 
     * @return the user ID who owns this meal
     */
    public int getUserId() { return userId; }
    
    /**
     * Sets the ID of the user who logged this meal.
     * Used for data ownership and access control.
     * 
     * @param userId the user ID to set
     */
    public void setUserId(int userId) { this.userId = userId; }
    
    /**
     * Gets the timestamp when the meal was logged in the system.
     * 
     * @return the logging timestamp
     */
    public LocalDateTime getLoggedAt() { return loggedAt; }
    
    /**
     * Sets the timestamp when the meal was logged in the system.
     * This is typically set automatically by the database.
     * 
     * @param loggedAt the logging timestamp to set
     */
    public void setLoggedAt(LocalDateTime loggedAt) { this.loggedAt = loggedAt; }
    
    /**
     * Gets the list of food items in this meal.
     * 
     * @return list of meal items, never null
     */
    public List<MealItem> getMealItems() { return mealItems; }
    
    /**
     * Sets the list of food items in this meal.
     * If null is provided, an empty list is created to prevent null pointer exceptions.
     * 
     * @param mealItems the list of meal items to set
     */
    public void setMealItems(List<MealItem> mealItems) { 
        this.mealItems = mealItems != null ? mealItems : new ArrayList<>(); 
    }
    
    /**
     * Adds a single food item to this meal.
     * Convenience method for building meals incrementally.
     * 
     * @param mealItem the meal item to add
     */
    public void addMealItem(MealItem mealItem) {
        this.mealItems.add(mealItem);
    }
    
    /**
     * Returns a string representation of the Meal object.
     * Includes all meal details and associated food items.
     * 
     * @return string representation of the meal
     */
    @Override
    public String toString() {
        return "Meal{" +
                "mealId=" + mealId +
                ", mealType=" + mealType +
                ", mealDate=" + mealDate +
                ", userId=" + userId +
                ", loggedAt=" + loggedAt +
                ", mealItems=" + mealItems +
                '}';
    }
}