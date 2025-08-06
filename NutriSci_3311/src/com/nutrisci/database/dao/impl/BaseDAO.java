package com.nutrisci.database.dao.impl;

import com.nutrisci.database.DatabaseConnectionManager;
import com.nutrisci.database.exceptions.DatabaseAccessException;

import java.sql.*;
import java.util.function.Function;

/**
 * Base DAO class providing common database operation patterns and utilities.
 * Eliminates duplicate code across DAO implementations by providing reusable methods
 * for connection management, parameter setting, and result processing.
 */
public abstract class BaseDAO {
    
    // SQL Query Constants - Common patterns
    protected static final String SELECT_COUNT_FROM = "SELECT COUNT(*) FROM ";
    protected static final String SELECT_EXISTS_FROM = "SELECT 1 FROM ";
    protected static final String DELETE_FROM = "DELETE FROM ";
    protected static final String INSERT_INTO = "INSERT INTO ";
    protected static final String UPDATE_SET = "UPDATE ";
    
    // Parameter placeholders
    protected static final String WHERE_ID_EQUALS = " WHERE id = ?";
    protected static final String WHERE_USER_ID_EQUALS = " WHERE user_id = ?";
    protected static final String AND_USER_ID_EQUALS = " AND user_id = ?";
    
    // Common numeric constants
    protected static final int NO_ROWS_AFFECTED = 0;
    protected static final int SINGLE_ROW_EXPECTED = 1;
    protected static final int FIRST_GENERATED_KEY_INDEX = 1;
    
    /**
     * Executes a query and returns a single result using the provided mapper function.
     * 
     * @param sql SQL query to execute
     * @param parameterSetter Function to set prepared statement parameters
     * @param resultMapper Function to map ResultSet to return type
     * @param <T> Return type
     * @return Mapped result from the query
     * @throws DatabaseAccessException if database operation fails
     */
    protected <T> T executeQuerySingle(String sql, 
                                      ParameterSetter parameterSetter, 
                                      ResultMapper<T> resultMapper) throws DatabaseAccessException {
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (parameterSetter != null) {
                parameterSetter.setParameters(stmt);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return resultMapper.map(rs);
                }
                return null;
            }
            
        } catch (SQLException e) {
            throw new DatabaseAccessException("Query execution failed: " + sql, e);
        }
    }
    
    /**
     * Executes an update/insert/delete statement and returns the number of affected rows.
     * 
     * @param sql SQL statement to execute
     * @param parameterSetter Function to set prepared statement parameters
     * @return Number of affected rows
     * @throws DatabaseAccessException if database operation fails
     */
    protected int executeUpdate(String sql, ParameterSetter parameterSetter) throws DatabaseAccessException {
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (parameterSetter != null) {
                parameterSetter.setParameters(stmt);
            }
            
            return stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new DatabaseAccessException("Update execution failed: " + sql, e);
        }
    }
    
    /**
     * Executes an insert statement and returns the generated key.
     * 
     * @param sql SQL insert statement
     * @param parameterSetter Function to set prepared statement parameters
     * @return Generated key
     * @throws DatabaseAccessException if database operation fails
     */
    protected int executeInsertWithGeneratedKey(String sql, ParameterSetter parameterSetter) throws DatabaseAccessException {
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            if (parameterSetter != null) {
                parameterSetter.setParameters(stmt);
            }
            
            int rowsAffected = stmt.executeUpdate();
            validateRowsAffected(rowsAffected, SINGLE_ROW_EXPECTED);
            
            return extractGeneratedKey(stmt);
            
        } catch (SQLException e) {
            throw new DatabaseAccessException("Insert with generated key failed: " + sql, e);
        }
    }
    
    /**
     * Checks if a record exists using the provided SQL query.
     * 
     * @param sql SQL query that should return 1 if record exists
     * @param parameterSetter Function to set prepared statement parameters
     * @return true if record exists, false otherwise
     * @throws DatabaseAccessException if database operation fails
     */
    protected boolean recordExists(String sql, ParameterSetter parameterSetter) throws DatabaseAccessException {
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (parameterSetter != null) {
                parameterSetter.setParameters(stmt);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
            
        } catch (SQLException e) {
            throw new DatabaseAccessException("Record existence check failed: " + sql, e);
        }
    }
    
    /**
     * Counts records using the provided SQL query.
     * 
     * @param sql SQL count query
     * @param parameterSetter Function to set prepared statement parameters
     * @return Number of records
     * @throws DatabaseAccessException if database operation fails
     */
    protected int countRecords(String sql, ParameterSetter parameterSetter) throws DatabaseAccessException {
        Integer count = executeQuerySingle(sql, parameterSetter, rs -> rs.getInt(1));
        return count != null ? count : 0;
    }
    
    /**
     * Validates that the expected number of rows were affected by an operation.
     * 
     * @param actualRows Actual number of rows affected
     * @param expectedRows Expected number of rows
     * @throws DatabaseAccessException if validation fails
     */
    protected void validateRowsAffected(int actualRows, int expectedRows) throws DatabaseAccessException {
        if (actualRows != expectedRows) {
            throw new DatabaseAccessException(
                String.format("Expected %d rows affected, but got %d", expectedRows, actualRows));
        }
    }
    
    /**
     * Extracts the first generated key from a prepared statement.
     * 
     * @param stmt Prepared statement with generated keys
     * @return Generated key
     * @throws SQLException if key extraction fails
     * @throws DatabaseAccessException if no key was generated
     */
    protected int extractGeneratedKey(PreparedStatement stmt) throws SQLException, DatabaseAccessException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(FIRST_GENERATED_KEY_INDEX);
            } else {
                throw new DatabaseAccessException("Failed to retrieve generated key");
            }
        }
    }
    
    /**
     * Functional interface for setting prepared statement parameters.
     */
    @FunctionalInterface
    protected interface ParameterSetter {
        void setParameters(PreparedStatement stmt) throws SQLException;
    }
    
    /**
     * Functional interface for mapping ResultSet to objects.
     */
    @FunctionalInterface
    protected interface ResultMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }
}