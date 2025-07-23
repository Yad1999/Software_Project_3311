package com.nutrisci.database.dao.impl;

import com.nutrisci.database.DatabaseConnectionManager;
import com.nutrisci.database.dao.IMealLogDAO;
import com.nutrisci.database.dto.Meal;
import com.nutrisci.database.dto.MealItem;
import com.nutrisci.database.dto.MealType;
import com.nutrisci.database.exceptions.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of IMealLogDAO for SQL Server database operations.
 * Provides concrete logic for meal logging operations using JDBC.
 */
public class MealLogDAOImpl implements IMealLogDAO {
    
    @Override
    public int logMeal(int userId, Meal meal) throws DatabaseAccessException {
        Connection conn = null;
        try {
            conn = DatabaseConnectionManager.getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            // Insert meal record
            String mealSql = "INSERT INTO LOGGED_MEALS (meal_type, meal_date, user_id) VALUES (?, ?, ?)";
            int mealId;
            
            try (PreparedStatement mealStmt = conn.prepareStatement(mealSql, Statement.RETURN_GENERATED_KEYS)) {
                mealStmt.setString(1, meal.getMealType().getValue());
                mealStmt.setDate(2, Date.valueOf(meal.getMealDate()));
                mealStmt.setInt(3, userId);
                
                int rowsAffected = mealStmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new DatabaseAccessException("Failed to insert meal record");
                }
                
                try (ResultSet generatedKeys = mealStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        mealId = generatedKeys.getInt(1);
                    } else {
                        throw new DatabaseAccessException("Failed to retrieve generated meal ID");
                    }
                }
            }
            
            // Insert meal items
            String itemSql = "INSERT INTO MEAL_ITEMS (meal_id, food_id, quantity_grams) VALUES (?, ?, ?)";
            try (PreparedStatement itemStmt = conn.prepareStatement(itemSql)) {
                for (MealItem item : meal.getMealItems()) {
                    itemStmt.setInt(1, mealId);
                    itemStmt.setInt(2, item.getFoodId());
                    itemStmt.setBigDecimal(3, item.getQuantityGrams());
                    itemStmt.addBatch();
                }
                itemStmt.executeBatch();
            }
            
            conn.commit(); // Commit transaction
            return mealId;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (SQLException rollbackEx) {
                    throw new DatabaseAccessException("Failed to rollback transaction", rollbackEx);
                }
            }
            throw new DatabaseAccessException("Failed to log meal", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    // Log error but don't throw
                }
            }
        }
    }
    
    @Override
    public List<Meal> loadLoggedMeals(int userId, LocalDate startDate, LocalDate endDate) 
            throws DatabaseAccessException {
        
        String sql = "SELECT m.meal_id, m.meal_type, m.meal_date, m.user_id, m.logged_at, " +
                     "mi.item_id, mi.food_id, mi.quantity_grams, fn.FoodDescription " +
                     "FROM LOGGED_MEALS m " +
                     "LEFT JOIN MEAL_ITEMS mi ON m.meal_id = mi.meal_id " +
                     "LEFT JOIN FOOD_NAME fn ON mi.food_id = fn.FoodID " +
                     "WHERE m.user_id = ? AND m.meal_date BETWEEN ? AND ? " +
                     "ORDER BY m.meal_date DESC, m.logged_at DESC, mi.item_id";
        
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setDate(2, Date.valueOf(startDate));
            stmt.setDate(3, Date.valueOf(endDate));
            
            try (ResultSet rs = stmt.executeQuery()) {
                Map<Integer, Meal> mealMap = new HashMap<>();
                
                while (rs.next()) {
                    int mealId = rs.getInt("meal_id");
                    
                    // Create meal if not exists
                    if (!mealMap.containsKey(mealId)) {
                        MealType mealType = MealType.fromString(rs.getString("meal_type"));
                        LocalDate mealDate = rs.getDate("meal_date").toLocalDate();
                        LocalDateTime loggedAt = rs.getTimestamp("logged_at").toLocalDateTime();
                        
                        Meal meal = new Meal(mealId, mealType, mealDate, userId, loggedAt, new ArrayList<>());
                        mealMap.put(mealId, meal);
                    }
                    
                    // Add meal item if exists
                    if (rs.getObject("item_id") != null) {
                        int itemId = rs.getInt("item_id");
                        int foodId = rs.getInt("food_id");
                        BigDecimal quantity = rs.getBigDecimal("quantity_grams");
                        String foodName = rs.getString("FoodDescription");
                        
                        MealItem item = new MealItem(itemId, mealId, foodId, quantity, foodName);
                        mealMap.get(mealId).addMealItem(item);
                    }
                }
                
                return new ArrayList<>(mealMap.values());
            }
            
        } catch (SQLException e) {
            throw new DatabaseAccessException("Failed to load logged meals", e);
        }
    }
    
    @Override
    public boolean removeMealItem(int userId, int mealId, int itemId) 
            throws DatabaseAccessException, MealNotFoundException, MealItemNotFoundException, UnauthorizedAccessException {
        
        // First verify authorization and existence
        String verifySql = "SELECT mi.item_id FROM MEAL_ITEMS mi " +
                          "JOIN LOGGED_MEALS m ON mi.meal_id = m.meal_id " +
                          "WHERE mi.item_id = ? AND mi.meal_id = ? AND m.user_id = ?";
        
        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            // Verify authorization
            try (PreparedStatement verifyStmt = conn.prepareStatement(verifySql)) {
                verifyStmt.setInt(1, itemId);
                verifyStmt.setInt(2, mealId);
                verifyStmt.setInt(3, userId);
                
                try (ResultSet rs = verifyStmt.executeQuery()) {
                    if (!rs.next()) {
                        // Check if meal exists for user
                        if (!mealExistsForUser(conn, mealId, userId)) {
                            throw new MealNotFoundException("Meal not found: " + mealId);
                        }
                        // Check if item exists for meal
                        if (!mealItemExists(conn, itemId, mealId)) {
                            throw new MealItemNotFoundException("Meal item not found: " + itemId);
                        }
                        throw new UnauthorizedAccessException("User not authorized to access this meal item");
                    }
                }
            }
            
            // Delete the meal item
            String deleteItemSql = "DELETE FROM MEAL_ITEMS WHERE item_id = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteItemSql)) {
                deleteStmt.setInt(1, itemId);
                deleteStmt.executeUpdate();
            }
            
            // Check if meal has any remaining items
            String countSql = "SELECT COUNT(*) FROM MEAL_ITEMS WHERE meal_id = ?";
            try (PreparedStatement countStmt = conn.prepareStatement(countSql)) {
                countStmt.setInt(1, mealId);
                
                try (ResultSet rs = countStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        // No items left, delete the meal
                        String deleteMealSql = "DELETE FROM LOGGED_MEALS WHERE meal_id = ?";
                        try (PreparedStatement deleteMealStmt = conn.prepareStatement(deleteMealSql)) {
                            deleteMealStmt.setInt(1, mealId);
                            deleteMealStmt.executeUpdate();
                        }
                    }
                }
            }
            
            return true;
            
        } catch (MealNotFoundException | MealItemNotFoundException | UnauthorizedAccessException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseAccessException("Failed to remove meal item", e);
        }
    }
    
    @Override
    public int addMealItem(int userId, int mealId, MealItem item) 
            throws DatabaseAccessException, MealNotFoundException, UnauthorizedAccessException {
        
        // Verify meal exists for user
        String verifySql = "SELECT meal_id FROM LOGGED_MEALS WHERE meal_id = ? AND user_id = ?";
        
        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            // Verify authorization
            try (PreparedStatement verifyStmt = conn.prepareStatement(verifySql)) {
                verifyStmt.setInt(1, mealId);
                verifyStmt.setInt(2, userId);
                
                try (ResultSet rs = verifyStmt.executeQuery()) {
                    if (!rs.next()) {
                        if (!mealExistsForUser(conn, mealId, userId)) {
                            throw new MealNotFoundException("Meal not found: " + mealId);
                        }
                        throw new UnauthorizedAccessException("User not authorized to access this meal");
                    }
                }
            }
            
            // Insert meal item
            String insertSql = "INSERT INTO MEAL_ITEMS (meal_id, food_id, quantity_grams) VALUES (?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setInt(1, mealId);
                insertStmt.setInt(2, item.getFoodId());
                insertStmt.setBigDecimal(3, item.getQuantityGrams());
                
                int rowsAffected = insertStmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new DatabaseAccessException("Failed to insert meal item");
                }
                
                try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new DatabaseAccessException("Failed to retrieve generated item ID");
                    }
                }
            }
            
        } catch (SQLException e) {
            throw new DatabaseAccessException("Failed to add meal item", e);
        }
    }
    
    // Helper methods
    private boolean mealExistsForUser(Connection conn, int mealId, int userId) throws SQLException {
        String sql = "SELECT 1 FROM LOGGED_MEALS WHERE meal_id = ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, mealId);
            stmt.setInt(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    private boolean mealItemExists(Connection conn, int itemId, int mealId) throws SQLException {
        String sql = "SELECT 1 FROM MEAL_ITEMS WHERE item_id = ? AND meal_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, itemId);
            stmt.setInt(2, mealId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}