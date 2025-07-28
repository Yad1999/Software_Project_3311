package com.nutrisci.database.exceptions;

/**
 * General exception for database access errors in the NutriSci application.
 * This exception is thrown when database operations fail due to connection issues,
 * SQL errors, or other database-related problems that are not covered by more
 * specific exception types.
 * 
 * <p>This is a checked exception that forces callers to handle database access
 * failures appropriately, either by catching and handling the error or
 * propagating it to higher levels.</p>
 * 
 * @author NutriSci Development Team
 * @version 1.0
 * @since 1.0
 */
public class DatabaseAccessException extends Exception {
    
    /**
     * Constructs a new DatabaseAccessException with the specified detail message.
     * 
     * @param message the detail message explaining the cause of the exception
     */
    public DatabaseAccessException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new DatabaseAccessException with the specified detail message
     * and underlying cause.
     * 
     * @param message the detail message explaining the cause of the exception
     * @param cause the underlying cause of the exception (typically an SQLException)
     */
    public DatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}