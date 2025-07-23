package com.nutrisci.database.exceptions;

/**
 * Exception thrown when a user attempts to access resources they are not authorized to access.
 */
public class UnauthorizedAccessException extends Exception {
    
    public UnauthorizedAccessException(String message) {
        super(message);
    }
    
    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}