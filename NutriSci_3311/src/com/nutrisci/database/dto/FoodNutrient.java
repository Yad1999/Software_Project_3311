package com.nutrisci.database.dto;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing a nutrient with its amount and unit for a specific food.
 */
public class FoodNutrient {
    
    private int nutrientId;
    private String nutrientName;
    private BigDecimal amount;
    private String unit;
    
    // Default constructor
    public FoodNutrient() {}
    
    // Full constructor
    public FoodNutrient(int nutrientId, String nutrientName, BigDecimal amount, String unit) {
        this.nutrientId = nutrientId;
        this.nutrientName = nutrientName;
        this.amount = amount;
        this.unit = unit;
    }
    
    // Getters and Setters
    public int getNutrientId() { return nutrientId; }
    public void setNutrientId(int nutrientId) { this.nutrientId = nutrientId; }
    
    public String getNutrientName() { return nutrientName; }
    public void setNutrientName(String nutrientName) { this.nutrientName = nutrientName; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    
    @Override
    public String toString() {
        return "FoodNutrient{" +
                "nutrientId=" + nutrientId +
                ", nutrientName='" + nutrientName + '\'' +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                '}';
    }
}