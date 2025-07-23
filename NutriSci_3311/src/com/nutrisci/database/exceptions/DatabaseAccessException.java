package com.nutrisci.database.exceptions;

/**
 * General exception for database access errors.
 */
public class DatabaseAccessException extends Exception {
    
    public DatabaseAccessException(String message) {
        super(message);
    }
    
    public DatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}