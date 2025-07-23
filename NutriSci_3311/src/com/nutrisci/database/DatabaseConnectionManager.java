package com.nutrisci.database;

import com.nutrisci.database.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centralized management of database connection properties and obtaining Connection instances.
 * Handles connections to SQL Server database for the NutriSci application.
 * Now uses secure configuration management instead of hardcoded credentials.
 */
public class DatabaseConnectionManager {
    
    private static final DatabaseConfig config = DatabaseConfig.getInstance();
    
    // Private constructor to hint at singleton-like usage
    private DatabaseConnectionManager() {
        // Private constructor
    }
    
    /**
     * Returns an active JDBC connection to the SQL Server database.
     * Each call opens a new connection for D2 simplicity.
     * Configuration is loaded from secure properties file.
     * 
     * @return Active database connection
     * @throws SQLException if connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Ensure SQL Server JDBC driver is loaded
            Class.forName(config.getDatabaseDriver());
            return DriverManager.getConnection(
                config.getDatabaseUrl(),
                config.getDatabaseUsername(),
                config.getDatabasePassword()
            );
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQL Server JDBC driver not found", e);
        }
    }
    
//    /**
//     * Gets database configuration for testing or debugging purposes.
//     * Note: This does not expose sensitive credentials.
//     * 
//     * @return Database URL (credentials are not exposed)
//     */
//    public static String getDatabaseUrl() {
//        return config.getDatabaseUrl();
//    }
}