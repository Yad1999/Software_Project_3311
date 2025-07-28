package com.nutrisci.database.exceptions;

/**
 * Exception thrown when attempting to create a user with duplicate username or email.
 * This exception indicates a violation of unique constraints in the USERS table,
 * specifically when trying to register a new user with a username or email address
 * that already exists in the database.
 * 
 * <p>This exception is typically thrown during:</p>
 * <ul>
 *   <li>User registration with existing username</li>
 *   <li>User registration with existing email address</li>
 *   <li>Profile updates that would create duplicate identifiers</li>
 * </ul>
 * 
 * <p>The underlying cause is usually a SQL unique constraint violation (error code 2627
 * in SQL Server) which is caught and wrapped in this more specific exception type.</p>
 * 
 * @author NutriSci Development Team
 * @version 1.0
 * @since 1.0
 */
public class DuplicateUserException extends Exception {
    
    /**
     * Constructs a new DuplicateUserException with the specified detail message.
     * 
     * @param message the detail message explaining which field caused the duplication
     */
    public DuplicateUserException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new DuplicateUserException with the specified detail message
     * and underlying cause.
     * 
     * @param message the detail message explaining which field caused the duplication
     * @param cause the underlying cause (typically an SQLException with constraint violation)
     */
    public DuplicateUserException(String message, Throwable cause) {
        super(message, cause);
    }
}