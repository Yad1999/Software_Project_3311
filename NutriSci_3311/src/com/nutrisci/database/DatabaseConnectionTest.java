package com.nutrisci.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Simple database connection test class to verify connectivity 
 * and basic operations with USERS, LOGGED_MEALS, and MEAL_ITEMS tables.
 */
public class DatabaseConnectionTest {
    
    public static void main(String[] args) {
        System.out.println("Starting Database Connection Test...");
        
        DatabaseConnectionTest test = new DatabaseConnectionTest();
        
        // Test basic connection
        if (test.testConnection()) {
            System.out.println("✓ Database connection successful!");
            
            // Test table access
            test.testTableAccess();
            
        } else {
            System.out.println("✗ Database connection failed!");
        }
    }
    
    /**
     * Test basic database connection
     */
    public boolean testConnection() {
        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connection established successfully!");
                System.out.println("Database URL: " + conn.getMetaData().getURL());
                System.out.println("Database Product: " + conn.getMetaData().getDatabaseProductName());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Test access to the three main tables: USERS, LOGGED_MEALS, MEAL_ITEMS
     */
    public void testTableAccess() {
        System.out.println("\n--- Testing Table Access ---");
        
        testTableExists("USERS");
        testTableExists("LOGGED_MEALS");
        testTableExists("MEAL_ITEMS");
    }
    
    /**
     * Test if a table exists and show its structure
     */
    private void testTableExists(String tableName) {
        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            
            // Test if table exists by counting rows
            String countQuery = "SELECT COUNT(*) as row_count FROM " + tableName;
            try (PreparedStatement stmt = conn.prepareStatement(countQuery);
                 ResultSet rs = stmt.executeQuery()) {
                
                if (rs.next()) {
                    int rowCount = rs.getInt("row_count");
                    System.out.println("✓ Table " + tableName + " exists and has " + rowCount + " rows");
                }
            }
            
            // Show table structure
            showTableStructure(conn, tableName);
            
        } catch (SQLException e) {
            System.err.println("✗ Error accessing table " + tableName + ": " + e.getMessage());
        }
    }
    
    /**
     * Show the structure of a table (column names and types)
     */
    private void showTableStructure(Connection conn, String tableName) {
        try {
            String query = "SELECT TOP 0 * FROM " + tableName;
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                
                System.out.println("  Columns in " + tableName + ":");
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rs.getMetaData().getColumnName(i);
                    String columnType = rs.getMetaData().getColumnTypeName(i);
                    System.out.println("    - " + columnName + " (" + columnType + ")");
                }
            }
        } catch (SQLException e) {
            System.err.println("  Could not retrieve structure for " + tableName + ": " + e.getMessage());
        }
    }
}