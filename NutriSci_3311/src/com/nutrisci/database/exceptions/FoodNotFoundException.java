package com.nutrisci.database.exceptions;

/**
 * Exception thrown when a food item is not found in the CNF database.
 */
public class FoodNotFoundException extends Exception {
    
    public FoodNotFoundException(String message) {
        super(message);
    }
    
    public FoodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}