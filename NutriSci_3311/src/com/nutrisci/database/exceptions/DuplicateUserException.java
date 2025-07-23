package com.nutrisci.database.exceptions;

/**
 * Exception thrown when attempting to create a user with duplicate username or email.
 */
public class DuplicateUserException extends Exception {
    
    public DuplicateUserException(String message) {
        super(message);
    }
    
    public DuplicateUserException(String message, Throwable cause) {
        super(message, cause);
    }
}