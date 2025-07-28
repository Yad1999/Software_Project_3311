package com.nutrisci.database.exceptions;

/**
 * Exception thrown when a food item is not found in the CNF (Canadian Nutrient File) database.
 * This exception indicates that a requested food item does not exist in the food database,
 * typically when searching by food ID or food description during meal logging operations.
 * 
 * <p>This exception is commonly thrown during:</p>
 * <ul>
 *   <li>Adding food items to meals with invalid food IDs</li>
 *   <li>Searching for foods that don't exist in the CNF database</li>
 *   <li>Loading nutritional information for deleted or invalid foods</li>
 *   <li>Ingredient swap operations with non-existent foods</li>
 * </ul>
 * 
 * <p>The CNF database contains standardized nutritional information for Canadian foods,
 * and this exception helps ensure data integrity when referencing food items.</p>
 * 
 * @author NutriSci Development Team
 * @version 1.0
 * @since 1.0
 */
public class FoodNotFoundException extends Exception {
    
    /**
     * Constructs a new FoodNotFoundException with the specified detail message.
     * 
     * @param message the detail message explaining which food was not found
     */
    public FoodNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new FoodNotFoundException with the specified detail message
     * and underlying cause.
     * 
     * @param message the detail message explaining which food was not found
     * @param cause the underlying cause of the exception
     */
    public FoodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}