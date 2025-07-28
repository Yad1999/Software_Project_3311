package com.nutrisci.database.exceptions;

/**
 * Exception thrown when a user attempts to access resources they are not authorized to access.
 * This exception enforces data access security by ensuring users can only access their own
 * data and preventing unauthorized access to other users' meals, profiles, or personal information.
 * 
 * <p>This exception is commonly thrown during:</p>
 * <ul>
 *   <li>Attempting to access another user's meal logs</li>
 *   <li>Trying to modify meals that don't belong to the current user</li>
 *   <li>Accessing user profiles without proper authorization</li>
 *   <li>Cross-user data operations that violate privacy boundaries</li>
 * </ul>
 * 
 * <p>This exception is critical for maintaining data privacy and security in
 * multi-user environments, ensuring users cannot accidentally or maliciously
 * access data belonging to other users.</p>
 * 
 * @author NutriSci Development Team
 * @version 1.0
 * @since 1.0
 */
public class UnauthorizedAccessException extends Exception {
    
    /**
     * Constructs a new UnauthorizedAccessException with the specified detail message.
     * 
     * @param message the detail message explaining the unauthorized access attempt
     */
    public UnauthorizedAccessException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new UnauthorizedAccessException with the specified detail message
     * and underlying cause.
     * 
     * @param message the detail message explaining the unauthorized access attempt
     * @param cause the underlying cause of the security violation
     */
    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}