package com.nutrisci.database.exceptions;

/**
 * Exception thrown when a meal item is not found in the database.
 * This exception indicates that a requested meal item does not exist in the MEAL_ITEMS table,
 * typically when attempting to modify or delete specific food items within a logged meal.
 * 
 * <p>This exception is commonly thrown during:</p>
 * <ul>
 *   <li>Removing specific food items from logged meals</li>
 *   <li>Updating quantities of non-existent meal items</li>
 *   <li>Authorization checks for meal item access</li>
 *   <li>Bulk operations where some items may have been deleted</li>
 * </ul>
 * 
 * <p>This exception provides granular error handling for meal item operations,
 * allowing applications to distinguish between meal-level and item-level errors.</p>
 * 
 * @author NutriSci Development Team
 * @version 1.0
 * @since 1.0
 */
public class MealItemNotFoundException extends Exception {
    
    /**
     * Constructs a new MealItemNotFoundException with the specified detail message.
     * 
     * @param message the detail message explaining which meal item was not found
     */
    public MealItemNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new MealItemNotFoundException with the specified detail message
     * and underlying cause.
     * 
     * @param message the detail message explaining which meal item was not found
     * @param cause the underlying cause of the exception
     */
    public MealItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}