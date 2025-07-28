package com.nutrisci.database.exceptions;

/**
 * Exception thrown when user authentication fails due to invalid credentials.
 * This exception is specifically thrown when a user provides correct username
 * but incorrect password during login attempts. It helps distinguish between
 * authentication failures and user-not-found scenarios.
 * 
 * <p>This exception is thrown in the following scenarios:</p>
 * <ul>
 *   <li>User exists but provided password doesn't match stored hash</li>
 *   <li>Password hash comparison fails during authentication</li>
 *   <li>Login attempts with expired or invalid credentials</li>
 * </ul>
 * 
 * <p>For security reasons, this exception should not reveal whether the
 * username exists or not to prevent username enumeration attacks.</p>
 * 
 * @author NutriSci Development Team
 * @version 1.0
 * @since 1.0
 */
public class InvalidCredentialsException extends Exception {
    
    /**
     * Constructs a new InvalidCredentialsException with the specified detail message.
     * 
     * @param message the detail message (should not reveal sensitive information)
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new InvalidCredentialsException with the specified detail message
     * and underlying cause.
     * 
     * @param message the detail message (should not reveal sensitive information)
     * @param cause the underlying cause of the authentication failure
     */
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}