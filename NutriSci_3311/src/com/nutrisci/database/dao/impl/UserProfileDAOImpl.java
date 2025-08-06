package com.nutrisci.database.dao.impl;

import com.nutrisci.database.DatabaseConnectionManager;
import com.nutrisci.database.dao.IUserProfileDAO;
import com.nutrisci.database.dto.User;
import com.nutrisci.database.exceptions.*;
import com.nutrisci.database.constants.DatabaseConstants;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Implementation of IUserProfileDAO for SQL Server database operations.
 * Provides concrete logic for user profile management using JDBC.
 * Extends BaseDAO to leverage common database operation patterns and eliminate duplicate code.
 */
public class UserProfileDAOImpl extends BaseDAO implements IUserProfileDAO {
    
    // SQL Query Constants using DatabaseConstants
    private static final String INSERT_USER_SQL = 
        INSERT_INTO + DatabaseConstants.TABLE_USERS + 
        " (" + DatabaseConstants.COL_USERNAME + ", " + DatabaseConstants.COL_EMAIL + ", " + 
        DatabaseConstants.COL_PASSWORD_HASH + ", " + DatabaseConstants.COL_SEX + ", " + 
        DatabaseConstants.COL_DOB + ", " + DatabaseConstants.COL_HEIGHT_CM + ", " + 
        DatabaseConstants.COL_WEIGHT_KG + ", " + DatabaseConstants.COL_UNIT_PREFERENCE + ") " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_USER_BY_USERNAME_SQL = 
        "SELECT " + DatabaseConstants.SELECT_ALL_USER_COLUMNS + 
        " FROM " + DatabaseConstants.TABLE_USERS + DatabaseConstants.WHERE_USERNAME;
    
    private static final String SELECT_USER_BY_ID_SQL = 
        "SELECT " + DatabaseConstants.SELECT_ALL_USER_COLUMNS + 
        " FROM " + DatabaseConstants.TABLE_USERS + DatabaseConstants.WHERE_USER_ID;
    
    @Override
    public int createUserProfile(User user) throws DatabaseAccessException, DuplicateUserException {
        try {
            return executeInsertWithGeneratedKey(INSERT_USER_SQL, stmt -> setUserInsertParameters(stmt, user));
        } catch (DatabaseAccessException e) {
            handleUserCreationException(e);
            return -1; // Never reached due to exception throwing above
        }
    }
    
    /**
     * Sets parameters for user insertion prepared statement.
     */
    private void setUserInsertParameters(PreparedStatement stmt, User user) throws SQLException {
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getPasswordHash());
        stmt.setString(4, String.valueOf(user.getSex()));
        stmt.setDate(5, Date.valueOf(user.getDateOfBirth()));
        stmt.setInt(6, user.getHeightCm());
        stmt.setDouble(7, user.getWeightKg());
        stmt.setString(8, user.getUnitPreference());
    }
    
    /**
     * Handles database exceptions during user creation and converts to appropriate business exceptions.
     */
    private void handleUserCreationException(DatabaseAccessException e) throws DatabaseAccessException, DuplicateUserException {
        Throwable cause = e.getCause();
        if (cause instanceof SQLException) {
            SQLException sqlEx = (SQLException) cause;
            if (isUniqueConstraintViolation(sqlEx)) {
                throw new DuplicateUserException(DatabaseConstants.ERROR_DUPLICATE_USER, sqlEx);
            }
        }
        throw new DatabaseAccessException("Failed to create user profile", e);
    }
    
    /**
     * Checks if the SQL exception represents a unique constraint violation.
     */
    private boolean isUniqueConstraintViolation(SQLException e) {
        return e.getMessage().contains(DatabaseConstants.COL_USERNAME) || 
               e.getMessage().contains(DatabaseConstants.COL_EMAIL) || 
               e.getErrorCode() == DatabaseConstants.SQL_SERVER_UNIQUE_CONSTRAINT_VIOLATION;
    }
    
    @Override
    public User authenticateUser(String username, String hashedPassword) 
            throws DatabaseAccessException, UserNotFoundException, InvalidCredentialsException {
        
        User user = executeQuerySingle(SELECT_USER_BY_USERNAME_SQL, 
            stmt -> stmt.setString(1, username),
            rs -> mapResultSetToUser(rs)
        );
        
        if (user == null) {
            throw new UserNotFoundException(DatabaseConstants.ERROR_USER_NOT_FOUND + username);
        }
        
        validatePassword(user.getPasswordHash(), hashedPassword, username);
        return user;
    }
    
    /**
     * Validates that the provided password matches the stored password hash.
     */
    private void validatePassword(String storedPasswordHash, String hashedPassword, String username) 
            throws InvalidCredentialsException {
        if (!storedPasswordHash.equals(hashedPassword)) {
            throw new InvalidCredentialsException(DatabaseConstants.ERROR_INVALID_CREDENTIALS + username);
        }
    }
    
    @Override
    public User loadUserProfile(int userId) throws DatabaseAccessException, UserNotFoundException {
        User user = executeQuerySingle(SELECT_USER_BY_ID_SQL, 
            stmt -> stmt.setInt(1, userId),
            rs -> mapResultSetToUser(rs)
        );
        
        if (user == null) {
            throw new UserNotFoundException(DatabaseConstants.ERROR_USER_NOT_FOUND + "ID: " + userId);
        }
        
        return user;
    }
    
    /**
     * Helper method to map ResultSet to User object using DatabaseConstants for column names.
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        int userId = rs.getInt(DatabaseConstants.COL_USER_ID);
        String username = rs.getString(DatabaseConstants.COL_USERNAME);
        String email = rs.getString(DatabaseConstants.COL_EMAIL);
        String passwordHash = rs.getString(DatabaseConstants.COL_PASSWORD_HASH);
        char sex = rs.getString(DatabaseConstants.COL_SEX).charAt(0);
        LocalDate dateOfBirth = rs.getDate(DatabaseConstants.COL_DOB).toLocalDate();
        int heightCm = rs.getInt(DatabaseConstants.COL_HEIGHT_CM);
        double weightKg = rs.getDouble(DatabaseConstants.COL_WEIGHT_KG);
        String unitPreference = rs.getString(DatabaseConstants.COL_UNIT_PREFERENCE);
        LocalDateTime createdAt = rs.getTimestamp(DatabaseConstants.COL_CREATED_AT).toLocalDateTime();
        
        return User.builder()
            .userId(userId)
            .username(username)
            .email(email)
            .passwordHash(passwordHash)
            .sex(sex)
            .dateOfBirth(dateOfBirth)
            .heightCm(heightCm)
            .weightKg(weightKg)
            .unitPreference(unitPreference)
            .createdAt(createdAt)
            .build();
    }
}