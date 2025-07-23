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
    public int getMealId() { return mealId; }
    public void setMealId(int mealId) { this.mealId = mealId; }
    
    public MealType getMealType() { return mealType; }
    public void setMealType(MealType mealType) { this.mealType = mealType; }
    
    public LocalDate getMealDate() { return mealDate; }
    public void setMealDate(LocalDate mealDate) { this.mealDate = mealDate; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public LocalDateTime getLoggedAt() { return loggedAt; }
    public void setLoggedAt(LocalDateTime loggedAt) { this.loggedAt = loggedAt; }
    
    public List<MealItem> getMealItems() { return mealItems; }
    public void setMealItems(List<MealItem> mealItems) { 
        this.mealItems = mealItems != null ? mealItems : new ArrayList<>(); 
    }
    
    public void addMealItem(MealItem mealItem) {
        this.mealItems.add(mealItem);
    }
    
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