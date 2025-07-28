package com.nutrisci.database.exceptions;

/**
 * Exception thrown when a user is not found in the database.
 * This exception indicates that a requested user does not exist in the USERS table,
 * typically when searching by user ID, username, or during authentication attempts.
 * 
 * <p>This is commonly thrown by user profile operations such as:</p>
 * <ul>
 *   <li>Loading user profiles by ID</li>
 *   <li>User authentication with non-existent usernames</li>
 *   <li>Profile update operations on deleted users</li>
 * </ul>
 * 
 * @author NutriSci Development Team
 * @version 1.0
 * @since 1.0
 */
public class UserNotFoundException extends Exception {
    
    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     * 
     * @param message the detail message explaining which user was not found
     */
    public UserNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new UserNotFoundException with the specified detail message
     * and underlying cause.
     * 
     * @param message the detail message explaining which user was not found
     * @param cause the underlying cause of the exception
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}