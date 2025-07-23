package com.nutrisci.database.exceptions;

/**
 * Exception thrown when user authentication fails due to invalid credentials.
 */
public class InvalidCredentialsException extends Exception {
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
    
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}