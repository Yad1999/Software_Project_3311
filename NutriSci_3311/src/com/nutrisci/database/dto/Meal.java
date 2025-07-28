package com.nutrisci.database.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object representing a logged meal with its associated meal items.
 */
public class Meal {
    
    private int mealId;
    private MealType mealType;
    private LocalDate mealDate;
    private int userId;
    private LocalDateTime loggedAt;
    private List<MealItem> mealItems;
    
    // Default constructor
    public Meal() {
        this.mealItems = new ArrayList<>();
    }
    
    // Constructor for creating new meal (without mealId and loggedAt)
    public Meal(MealType mealType, LocalDate mealDate, int userId) {
        this.mealType = mealType;
        this.mealDate = mealDate;
        this.userId = userId;
        this.mealItems = new ArrayList<>();
    }
    
    // Full constructor
    public Meal(int mealId, MealType mealType, LocalDate mealDate, int userId, 
                LocalDateTime loggedAt, List<MealItem> mealItems) {
        this.mealId = mealId;
        this.mealType = mealType;
        this.mealDate = mealDate;
        this.userId = userId;
        this.loggedAt = loggedAt;
        this.mealItems = mealItems != null ? mealItems : new ArrayList<>();
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