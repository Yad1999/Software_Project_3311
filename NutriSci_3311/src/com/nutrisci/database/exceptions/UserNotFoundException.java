package com.nutrisci.database.exceptions;

/**
 * Exception thrown when a user is not found in the database.
 */
public class UserNotFoundException extends Exception {
    
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}