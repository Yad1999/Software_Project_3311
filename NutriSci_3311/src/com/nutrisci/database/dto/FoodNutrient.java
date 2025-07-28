package com.nutrisci.database.dto;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing a nutrient with its amount and unit for a specific food.
 * This class encapsulates nutritional information from the Canadian Nutrient File (CNF) database,
 * specifically the relationship between foods and their nutrient content.
 * 
 * <p>Each FoodNutrient represents one row from the NUTRIENT_AMOUNT table joined with
 * the NUTRIENT_NAME table to provide both the numeric value and descriptive information.</p>
 * 
 * @author NutriSci Development Team
 * @version 1.0
 * @since 1.0
 */
public class FoodNutrient {
    
    private int nutrientId;
    private String nutrientName;
    private BigDecimal amount;
    private String unit;
    
    /**
     * Default constructor for creating an empty FoodNutrient object.
     */
    public FoodNutrient() {}
    
    /**
     * Full constructor for creating a FoodNutrient with all properties.
     * 
     * @param nutrientId the unique identifier for the nutrient from CNF database
     * @param nutrientName the human-readable name of the nutrient
     * @param amount the quantity of the nutrient in the specified unit
     * @param unit the unit of measurement (e.g., "g", "mg", "μg", "IU")
     */
    public FoodNutrient(int nutrientId, String nutrientName, BigDecimal amount, String unit) {
        this.nutrientId = nutrientId;
        this.nutrientName = nutrientName;
        this.amount = amount;
        this.unit = unit;
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
     * This corresponds to the NutrientID in the CNF database.
     * 
     * @param nutrientId the nutrient ID to set
     */
    public void setNutrientId(int nutrientId) { this.nutrientId = nutrientId; }
    
    /**
     * Gets the human-readable name of the nutrient.
     * 
     * @return the nutrient name (e.g., "Protein", "Vitamin C", "Calcium")
     */
    public String getNutrientName() { return nutrientName; }
    
    /**
     * Sets the human-readable name of the nutrient.
     * This corresponds to the NutrientName field in the NUTRIENT_NAME table.
     * 
     * @param nutrientName the nutrient name to set
     */
    public void setNutrientName(String nutrientName) { this.nutrientName = nutrientName; }
    
    /**
     * Gets the amount of the nutrient per 100g of food.
     * 
     * @return the nutrient amount as a precise decimal value
     */
    public BigDecimal getAmount() { return amount; }
    
    /**
     * Sets the amount of the nutrient per 100g of food.
     * Uses BigDecimal for precise decimal arithmetic in nutritional calculations.
     * 
     * @param amount the nutrient amount to set
     */
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    /**
     * Gets the unit of measurement for the nutrient amount.
     * 
     * @return the unit (e.g., "g", "mg", "μg", "IU", "kcal")
     */
    public String getUnit() { return unit; }
    
    /**
     * Sets the unit of measurement for the nutrient amount.
     * Common units include grams (g), milligrams (mg), micrograms (μg), 
     * International Units (IU), and kilocalories (kcal).
     * 
     * @param unit the unit to set
     */
    public void setUnit(String unit) { this.unit = unit; }
    
    /**
     * Returns a string representation of the FoodNutrient object.
     * Includes nutrient details, amount, and unit for debugging and logging.
     * 
     * @return string representation of the food nutrient
     */
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