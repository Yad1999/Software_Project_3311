package com.nutrisci.database.dto;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing nutrient constraints for searching replacement foods.
 * Used in food search and ingredient swap operations to specify acceptable ranges
 * for specific nutrients when finding food alternatives.
 * 
 * <p>This class enables sophisticated food searching by allowing users to specify
 * minimum and maximum values for nutrients of interest. For example, finding foods
 * with high protein (min 20g) and low sodium (max 200mg).</p>
 * 
 * @author NutriSci Development Team
 * @version 1.0
 * @since 1.0
 */
public class NutrientConstraint {
    
    private int nutrientId;
    private BigDecimal minValue;
    private BigDecimal maxValue;
    
    /**
     * Default constructor for creating an empty NutrientConstraint object.
     */
    public NutrientConstraint() {}
    
    /**
     * Full constructor for creating a NutrientConstraint with all properties.
     * 
     * @param nutrientId the unique identifier for the nutrient from CNF database
     * @param minValue the minimum acceptable value for the nutrient (inclusive)
     * @param maxValue the maximum acceptable value for the nutrient (inclusive)
     */
    public NutrientConstraint(int nutrientId, BigDecimal minValue, BigDecimal maxValue) {
        this.nutrientId = nutrientId;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
    
    // Getters and Setters
    
    /**
     * Gets the unique nutrient identifier.
     * 
     * @return the nutrient ID from the CNF NUTRIENT_NAME table
     */
    public int getNutrientId() { return nutrientId; }
    
    /**
     * Sets the unique nutrient identifier.
     * This should correspond to a valid NutrientID in the CNF database.
     * 
     * @param nutrientId the nutrient ID to set
     */
    public void setNutrientId(int nutrientId) { this.nutrientId = nutrientId; }
    
    /**
     * Gets the minimum acceptable value for the nutrient.
     * 
     * @return the minimum value (inclusive), or null if no minimum constraint
     */
    public BigDecimal getMinValue() { return minValue; }
    
    /**
     * Sets the minimum acceptable value for the nutrient.
     * Set to null to indicate no minimum constraint.
     * 
     * @param minValue the minimum value to set (inclusive)
     */
    public void setMinValue(BigDecimal minValue) { this.minValue = minValue; }
    
    /**
     * Gets the maximum acceptable value for the nutrient.
     * 
     * @return the maximum value (inclusive), or null if no maximum constraint
     */
    public BigDecimal getMaxValue() { return maxValue; }
    
    /**
     * Sets the maximum acceptable value for the nutrient.
     * Set to null to indicate no maximum constraint.
     * 
     * @param maxValue the maximum value to set (inclusive)
     */
    public void setMaxValue(BigDecimal maxValue) { this.maxValue = maxValue; }
    
    /**
     * Returns a string representation of the NutrientConstraint object.
     * Useful for debugging food search operations and constraint validation.
     * 
     * @return string representation of the nutrient constraint
     */
    @Override
    public String toString() {
        return "NutrientConstraint{" +
                "nutrientId=" + nutrientId +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                '}';
    }
}