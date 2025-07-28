package com.nutrisci.database.exceptions;

/**
 * Exception thrown when a meal is not found in the database.
 * This exception indicates that a requested meal does not exist in the LOGGED_MEALS table,
 * typically when searching by meal ID or when accessing meals that have been deleted.
 * 
 * <p>This exception is commonly thrown during:</p>
 * <ul>
 *   <li>Loading meal details by meal ID</li>
 *   <li>Updating or deleting non-existent meals</li>
 *   <li>Adding items to meals that no longer exist</li>
 *   <li>Authorization checks for meal access</li>
 * </ul>
 * 
 * <p>This exception helps distinguish between database access errors and
 * legitimate cases where the requested meal simply doesn't exist.</p>
 * 
 * @author NutriSci Development Team
 * @version 1.0
 * @since 1.0
 */
public class MealNotFoundException extends Exception {
    
    /**
     * Constructs a new MealNotFoundException with the specified detail message.
     * 
     * @param message the detail message explaining which meal was not found
     */
    public MealNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new MealNotFoundException with the specified detail message
     * and underlying cause.
     * 
     * @param message the detail message explaining which meal was not found
     * @param cause the underlying cause of the exception
     */
    public MealNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}