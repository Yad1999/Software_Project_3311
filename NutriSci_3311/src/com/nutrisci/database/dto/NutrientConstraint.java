package com.nutrisci.database.dto;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing nutrient constraints for searching replacement foods.
 * Used in searchReplacementFoods method to specify nutrient criteria.
 */
public class NutrientConstraint {
    
    private int nutrientId;
    private BigDecimal minValue;
    private BigDecimal maxValue;
    
    // Default constructor
    public NutrientConstraint() {}
    
    // Full constructor
    public NutrientConstraint(int nutrientId, BigDecimal minValue, BigDecimal maxValue) {
        this.nutrientId = nutrientId;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
    
    // Getters and Setters
    public int getNutrientId() { return nutrientId; }
    public void setNutrientId(int nutrientId) { this.nutrientId = nutrientId; }
    
    public BigDecimal getMinValue() { return minValue; }
    public void setMinValue(BigDecimal minValue) { this.minValue = minValue; }
    
    public BigDecimal getMaxValue() { return maxValue; }
    public void setMaxValue(BigDecimal maxValue) { this.maxValue = maxValue; }
    
    @Override
    public String toString() {
        return "NutrientConstraint{" +
                "nutrientId=" + nutrientId +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                '}';
    }
}