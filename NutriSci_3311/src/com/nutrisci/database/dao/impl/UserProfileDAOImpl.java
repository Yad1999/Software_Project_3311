package com.nutrisci.database.dao.impl;

import com.nutrisci.database.DatabaseConnectionManager;
import com.nutrisci.database.dao.IUserProfileDAO;
import com.nutrisci.database.dto.User;
import com.nutrisci.database.exceptions.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Implementation of IUserProfileDAO for SQL Server database operations.
 * Provides concrete logic for user profile management using JDBC.
 */
public class UserProfileDAOImpl implements IUserProfileDAO {
    
    @Override
    public int createUserProfile(User user) throws DatabaseAccessException, DuplicateUserException {
        String sql = "INSERT INTO USERS (username, email, password_hash, sex, dob, height_cm, weight_kg, unit_preference) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());
            stmt.setString(4, String.valueOf(user.getSex()));
            stmt.setDate(5, Date.valueOf(user.getDateOfBirth()));
            stmt.setInt(6, user.getHeightCm());
            stmt.setDouble(7, user.getWeightKg());
            stmt.setString(8, user.getUnitPreference());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new DatabaseAccessException("Failed to create user profile - no rows affected");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new DatabaseAccessException("Failed to retrieve generated user ID");
                }
            }
            
        } catch (SQLException e) {
            // Check for unique constraint violations
            if (e.getMessage().contains("username") || e.getMessage().contains("email") || 
                e.getErrorCode() == 2627) { // SQL Server unique constraint violation error code
                throw new DuplicateUserException("Username or email already exists", e);
            }
            throw new DatabaseAccessException("Failed to create user profile", e);
        }
    }
    
    @Override
    public User authenticateUser(String username, String hashedPassword) 
            throws DatabaseAccessException, UserNotFoundException, InvalidCredentialsException {
        
        String sql = "SELECT user_id, username, email, password_hash, sex, dob, height_cm, weight_kg, unit_preference, created_at " +
                     "FROM USERS WHERE username = ?";
        
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new UserNotFoundException("User not found: " + username);
                }
                
                String storedPasswordHash = rs.getString("password_hash");
                if (!storedPasswordHash.equals(hashedPassword)) {
                    throw new InvalidCredentialsException("Invalid password for user: " + username);
                }
                
                return mapResultSetToUser(rs);
            }
            
        } catch (UserNotFoundException | InvalidCredentialsException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseAccessException("Failed to authenticate user", e);
        }
    }
    
    @Override
    public User loadUserProfile(int userId) throws DatabaseAccessException, UserNotFoundException {
        String sql = "SELECT user_id, username, email, password_hash, sex, dob, height_cm, weight_kg, unit_preference, created_at " +
                     "FROM USERS WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new UserNotFoundException("User not found with ID: " + userId);
                }
                
                return mapResultSetToUser(rs);
            }
            
        } catch (UserNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseAccessException("Failed to load user profile", e);
        }
    }
    
    /**
     * Helper method to map ResultSet to User object.
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        int userId = rs.getInt("user_id");
        String username = rs.getString("username");
        String email = rs.getString("email");
        String passwordHash = rs.getString("password_hash");
        char sex = rs.getString("sex").charAt(0);
        LocalDate dateOfBirth = rs.getDate("dob").toLocalDate();
        int heightCm = rs.getInt("height_cm");
        double weightKg = rs.getDouble("weight_kg");
        String unitPreference = rs.getString("unit_preference");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        
        return new User(userId, username, email, passwordHash, sex, dateOfBirth, 
                       heightCm, weightKg, unitPreference, createdAt);
    }
}