package com.nutrisci.database.exceptions;

/**
 * Exception thrown when a meal item is not found in the database.
 */
public class MealItemNotFoundException extends Exception {
    
    public MealItemNotFoundException(String message) {
        super(message);
    }
    
    public MealItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}