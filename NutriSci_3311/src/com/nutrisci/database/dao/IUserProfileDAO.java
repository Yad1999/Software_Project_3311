package com.nutrisci.database.dao;

import com.nutrisci.database.dto.User;
import com.nutrisci.database.exceptions.*;

/**
 * Data Access Object interface for user profile operations.
 * Defines contracts for user authentication and profile management.
 */
public interface IUserProfileDAO {
    
    /**
     * Creates a new user profile in the database.
     * 
     * @param user User object containing profile information
     * @return The user_id of the created user
     * @throws DatabaseAccessException if database access fails
     * @throws DuplicateUserException if username or email already exists
     */
    int createUserProfile(User user) throws DatabaseAccessException, DuplicateUserException;
    
    /**
     * Authenticates a user with username and password.
     * 
     * @param username User's username
     * @param hashedPassword User's hashed password
     * @return User object with full profile information on successful authentication
     * @throws DatabaseAccessException if database access fails
     * @throws UserNotFoundException if user doesn't exist
     * @throws InvalidCredentialsException if password doesn't match
     */
    User authenticateUser(String username, String hashedPassword) 
            throws DatabaseAccessException, UserNotFoundException, InvalidCredentialsException;
    
    /**
     * Loads a user's full profile by user ID.
     * 
     * @param userId The user's ID
     * @return User object with full profile information
     * @throws DatabaseAccessException if database access fails
     * @throws UserNotFoundException if user doesn't exist
     */
    User loadUserProfile(int userId) throws DatabaseAccessException, UserNotFoundException;
}