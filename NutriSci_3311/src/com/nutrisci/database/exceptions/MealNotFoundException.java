package com.nutrisci.database.exceptions;

/**
 * Exception thrown when a meal is not found in the database.
 */
public class MealNotFoundException extends Exception {
    
    public MealNotFoundException(String message) {
        super(message);
    }
    
    public MealNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}