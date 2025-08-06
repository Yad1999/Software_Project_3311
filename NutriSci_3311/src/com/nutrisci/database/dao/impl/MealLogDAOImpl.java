package com.nutrisci.database.dao.impl;

import com.nutrisci.database.DatabaseConnectionManager;
import com.nutrisci.database.dao.IMealLogDAO;
import com.nutrisci.database.dto.Meal;
import com.nutrisci.database.dto.MealItem;
import com.nutrisci.database.dto.MealType;
import com.nutrisci.database.exceptions.*;
import com.nutrisci.database.constants.DatabaseConstants;

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
 * Extends BaseDAO to leverage common database operation patterns.
 */
public class MealLogDAOImpl extends BaseDAO implements IMealLogDAO {
    
    // SQL Query Constants
    private static final String INSERT_MEAL_SQL = 
        INSERT_INTO + DatabaseConstants.TABLE_LOGGED_MEALS + 
        " (" + DatabaseConstants.COL_MEAL_TYPE + ", " + DatabaseConstants.COL_MEAL_DATE + ", " + DatabaseConstants.COL_USER_ID + ") " +
        "VALUES (?, ?, ?)";
    
    private static final String INSERT_MEAL_ITEMS_SQL = 
        INSERT_INTO + DatabaseConstants.TABLE_MEAL_ITEMS + 
        " (" + DatabaseConstants.COL_MEAL_ID + ", " + DatabaseConstants.COL_FOOD_ID + ", " + DatabaseConstants.COL_QUANTITY_GRAMS + ") " +
        "VALUES (?, ?, ?)";
    
    private static final String SELECT_MEALS_WITH_ITEMS_SQL = 
        "SELECT " + DatabaseConstants.SELECT_ALL_MEAL_COLUMNS + ", " +
        DatabaseConstants.SELECT_ALL_MEAL_ITEM_COLUMNS + ", fn." + DatabaseConstants.COL_FOOD_DESCRIPTION + " " +
        "FROM " + DatabaseConstants.TABLE_LOGGED_MEALS + " m" +
        DatabaseConstants.JOIN_MEAL_ITEMS +
        DatabaseConstants.JOIN_FOOD_NAME +
        " WHERE m." + DatabaseConstants.COL_USER_ID + " = ? AND m." + DatabaseConstants.COL_MEAL_DATE + " BETWEEN ? AND ?" +
        DatabaseConstants.ORDER_BY_MEAL_DATE_DESC + ", mi." + DatabaseConstants.COL_ITEM_ID;
    
    private static final String SELECT_MEAL_ITEM_ACCESS_SQL = 
        "SELECT mi." + DatabaseConstants.COL_ITEM_ID + " FROM " + DatabaseConstants.TABLE_MEAL_ITEMS + " mi " +
        "JOIN " + DatabaseConstants.TABLE_LOGGED_MEALS + " m ON mi." + DatabaseConstants.COL_MEAL_ID + " = m." + DatabaseConstants.COL_MEAL_ID + " " +
        "WHERE mi." + DatabaseConstants.COL_ITEM_ID + " = ? AND mi." + DatabaseConstants.COL_MEAL_ID + " = ? AND m." + DatabaseConstants.COL_USER_ID + " = ?";
    
    private static final String COUNT_MEAL_ITEMS_SQL = 
        SELECT_COUNT_FROM + DatabaseConstants.TABLE_MEAL_ITEMS + DatabaseConstants.WHERE_MEAL_ID;
    
    private static final String DELETE_MEAL_ITEM_SQL = 
        DELETE_FROM + DatabaseConstants.TABLE_MEAL_ITEMS + DatabaseConstants.WHERE_ITEM_ID;
    
    private static final String DELETE_MEAL_SQL = 
        DELETE_FROM + DatabaseConstants.TABLE_LOGGED_MEALS + DatabaseConstants.WHERE_MEAL_ID;
    
    private static final String SELECT_MEAL_ACCESS_SQL = 
        "SELECT " + DatabaseConstants.COL_MEAL_ID + " FROM " + DatabaseConstants.TABLE_LOGGED_MEALS + 
        DatabaseConstants.WHERE_MEAL_ID_AND_USER_ID;
    
    private static final String CHECK_MEAL_EXISTS_SQL = 
        SELECT_EXISTS_FROM + DatabaseConstants.TABLE_LOGGED_MEALS + DatabaseConstants.WHERE_MEAL_ID_AND_USER_ID;
    
    private static final String CHECK_MEAL_ITEM_EXISTS_SQL = 
        SELECT_EXISTS_FROM + DatabaseConstants.TABLE_MEAL_ITEMS + DatabaseConstants.WHERE_ITEM_ID_AND_MEAL_ID;

    @Override
    public int logMeal(int userId, Meal meal) throws DatabaseAccessException {
        return executeInTransaction(conn -> {
            int mealId = insertMealRecord(conn, userId, meal);
            insertMealItems(conn, mealId, meal.getMealItems());
            return mealId;
        });
    }
    
    /**
     * Executes database operations within a transaction.
     */
    private <T> T executeInTransaction(TransactionCallback<T> callback) throws DatabaseAccessException {
        Connection conn = null;
        try {
            conn = DatabaseConnectionManager.getConnection();
            conn.setAutoCommit(false);
            
            T result = callback.execute(conn);
            
            conn.commit();
            return result;
            
        } catch (SQLException | DatabaseAccessException e) {
            rollbackTransaction(conn);
            if (e instanceof DatabaseAccessException) {
                throw (DatabaseAccessException) e;
            }
            throw new DatabaseAccessException("Transaction failed", e);
        } finally {
            closeConnection(conn);
        }
    }
    
    /**
     * Inserts a meal record and returns the generated meal ID.
     */
    private int insertMealRecord(Connection conn, int userId, Meal meal) throws SQLException, DatabaseAccessException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_MEAL_SQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, meal.getMealType().getValue());
            stmt.setDate(2, Date.valueOf(meal.getMealDate()));
            stmt.setInt(3, userId);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == NO_ROWS_AFFECTED) {
                throw new SQLException(String.format(DatabaseConstants.ERROR_NO_ROWS_AFFECTED, DatabaseConstants.OPERATION_INSERT));
            }
            
            return extractGeneratedKey(stmt);
        }
    }
    
    /**
     * Inserts meal items for a given meal.
     */
    private void insertMealItems(Connection conn, int mealId, List<MealItem> mealItems) throws SQLException {
        if (mealItems == null || mealItems.isEmpty()) {
            return; // No items to insert
        }
        
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_MEAL_ITEMS_SQL)) {
            for (MealItem item : mealItems) {
                stmt.setInt(1, mealId);
                stmt.setInt(2, item.getFoodId());
                stmt.setBigDecimal(3, item.getQuantityGrams());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
    
    /**
     * Extracts the generated key from a prepared statement.
     */
    private int getGeneratedKey(PreparedStatement stmt) throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve generated key");
            }
        }
    }
    
    /**
     * Rolls back a transaction safely.
     */
    private void rollbackTransaction(Connection conn) throws DatabaseAccessException {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                throw new DatabaseAccessException("Failed to rollback transaction", rollbackEx);
            }
        }
    }
    
    /**
     * Closes database connection safely.
     */
    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                // Log error but don't throw in finally block
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Functional interface for transaction callbacks.
     */
    @FunctionalInterface
    private interface TransactionCallback<T> {
        T execute(Connection conn) throws SQLException, DatabaseAccessException;
    }
    
    @Override
    public List<Meal> loadLoggedMeals(int userId, LocalDate startDate, LocalDate endDate) 
            throws DatabaseAccessException {
        
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_MEALS_WITH_ITEMS_SQL)) {
            
            stmt.setInt(1, userId);
            stmt.setDate(2, Date.valueOf(startDate));
            stmt.setDate(3, Date.valueOf(endDate));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return new ArrayList<>(); // Return empty list if no results
                }
                return buildMealsFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            throw new DatabaseAccessException("Failed to load logged meals", e);
        }
    }
    
    /**
     * Builds meal objects from result set data.
     */
    private List<Meal> buildMealsFromResultSet(ResultSet rs) throws SQLException {
        Map<Integer, Meal> mealMap = new HashMap<>();
        
        do {
            int mealId = rs.getInt(DatabaseConstants.COL_MEAL_ID);
            
            // Create meal if not exists
            if (!mealMap.containsKey(mealId)) {
                Meal meal = createMealFromResultSet(rs);
                mealMap.put(mealId, meal);
            }
            
            // Add meal item if exists
            addMealItemIfExists(rs, mealMap.get(mealId));
        } while (rs.next());
        
        return new ArrayList<>(mealMap.values());
    }
    
    /**
     * Creates a meal object from result set data.
     */
    private Meal createMealFromResultSet(ResultSet rs) throws SQLException {
        int mealId = rs.getInt(DatabaseConstants.COL_MEAL_ID);
        int userId = rs.getInt(DatabaseConstants.COL_USER_ID);
        MealType mealType = MealType.fromString(rs.getString(DatabaseConstants.COL_MEAL_TYPE));
        LocalDate mealDate = rs.getDate(DatabaseConstants.COL_MEAL_DATE).toLocalDate();
        LocalDateTime loggedAt = rs.getTimestamp(DatabaseConstants.COL_LOGGED_AT).toLocalDateTime();
        
        return Meal.builder()
            .mealId(mealId)
            .mealType(mealType)
            .mealDate(mealDate)
            .userId(userId)
            .loggedAt(loggedAt)
            .build();
    }
    
    /**
     * Adds a meal item to the meal if it exists in the result set.
     */
    private void addMealItemIfExists(ResultSet rs, Meal meal) throws SQLException {
        if (rs.getObject(DatabaseConstants.COL_ITEM_ID) != null) {
            int itemId = rs.getInt(DatabaseConstants.COL_ITEM_ID);
            int foodId = rs.getInt(DatabaseConstants.COL_FOOD_ID);
            BigDecimal quantity = rs.getBigDecimal(DatabaseConstants.COL_QUANTITY_GRAMS);
            String foodName = rs.getString(DatabaseConstants.COL_FOOD_DESCRIPTION);
            
            MealItem item = MealItem.builder()
                .itemId(itemId)
                .mealId(meal.getMealId())
                .foodId(foodId)
                .quantityGrams(quantity)
                .foodName(foodName)
                .build();
            meal.addMealItem(item);
        }
    }
    
    @Override
    public boolean removeMealItem(int userId, int mealId, int itemId) 
            throws DatabaseAccessException, MealNotFoundException, MealItemNotFoundException, UnauthorizedAccessException {
        
        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            validateMealItemAccess(conn, userId, mealId, itemId);
            deleteMealItem(itemId);
            cleanupEmptyMeal(mealId);
            return true;
            
        } catch (MealNotFoundException | MealItemNotFoundException | UnauthorizedAccessException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseAccessException("Failed to remove meal item", e);
        }
    }
    
    /**
     * Validates that user has access to the meal item.
     */
    private void validateMealItemAccess(Connection conn, int userId, int mealId, int itemId) 
            throws SQLException, MealNotFoundException, MealItemNotFoundException, UnauthorizedAccessException, DatabaseAccessException {
        
        boolean hasAccess = recordExists(SELECT_MEAL_ITEM_ACCESS_SQL, stmt -> {
            stmt.setInt(1, itemId);
            stmt.setInt(2, mealId);
            stmt.setInt(3, userId);
        });
        
        if (!hasAccess) {
            handleMealItemAccessFailure(userId, mealId, itemId);
        }
    }
    
    /**
     * Handles meal item access validation failures.
     */
    private void handleMealItemAccessFailure(int userId, int mealId, int itemId) 
            throws MealNotFoundException, MealItemNotFoundException, UnauthorizedAccessException, DatabaseAccessException {
        
        if (!mealExistsForUser(mealId, userId)) {
            throw new MealNotFoundException(DatabaseConstants.ERROR_MEAL_NOT_FOUND + mealId);
        }
        if (!mealItemExists(itemId, mealId)) {
            throw new MealItemNotFoundException(DatabaseConstants.ERROR_MEAL_ITEM_NOT_FOUND + itemId);
        }
        throw new UnauthorizedAccessException(DatabaseConstants.ERROR_UNAUTHORIZED_ACCESS + "meal item");
    }
    
    /**
     * Deletes a meal item from the database.
     */
    private void deleteMealItem(int itemId) throws DatabaseAccessException {
        int rowsAffected = executeUpdate(DELETE_MEAL_ITEM_SQL, stmt -> stmt.setInt(1, itemId));
        validateRowsAffected(rowsAffected, SINGLE_ROW_EXPECTED);
    }
    
    /**
     * Removes empty meals that have no remaining items.
     */
    private void cleanupEmptyMeal(int mealId) throws DatabaseAccessException {
        int itemCount = countRecords(COUNT_MEAL_ITEMS_SQL, stmt -> stmt.setInt(1, mealId));
        
        if (itemCount == NO_ROWS_AFFECTED) {
            deleteMeal(mealId);
        }
    }
    
    /**
     * Deletes a meal from the database.
     */
    private void deleteMeal(int mealId) throws DatabaseAccessException {
        executeUpdate(DELETE_MEAL_SQL, stmt -> stmt.setInt(1, mealId));
    }

    @Override
    public int addMealItem(int userId, int mealId, MealItem item) 
            throws DatabaseAccessException, MealNotFoundException, UnauthorizedAccessException {
        
        validateMealAccess(userId, mealId);
        return insertSingleMealItem(mealId, item);
    }
    
    /**
     * Validates that user has access to the meal.
     */
    private void validateMealAccess(int userId, int mealId) 
            throws MealNotFoundException, UnauthorizedAccessException, DatabaseAccessException {
        
        boolean hasAccess = recordExists(SELECT_MEAL_ACCESS_SQL, stmt -> {
            stmt.setInt(1, mealId);
            stmt.setInt(2, userId);
        });
        
        if (!hasAccess) {
            if (!mealExistsForUser(mealId, userId)) {
                throw new MealNotFoundException(DatabaseConstants.ERROR_MEAL_NOT_FOUND + mealId);
            }
            throw new UnauthorizedAccessException(DatabaseConstants.ERROR_UNAUTHORIZED_ACCESS + "meal");
        }
    }
    
    /**
     * Inserts a single meal item and returns the generated item ID.
     */
    private int insertSingleMealItem(int mealId, MealItem item) throws DatabaseAccessException {
        return executeInsertWithGeneratedKey(INSERT_MEAL_ITEMS_SQL, stmt -> {
            stmt.setInt(1, mealId);
            stmt.setInt(2, item.getFoodId());
            stmt.setBigDecimal(3, item.getQuantityGrams());
        });
    }
    
    // Helper methods using BaseDAO utilities
    private boolean mealExistsForUser(int mealId, int userId) throws DatabaseAccessException {
        return recordExists(CHECK_MEAL_EXISTS_SQL, stmt -> {
            stmt.setInt(1, mealId);
            stmt.setInt(2, userId);
        });
    }
    
    private boolean mealItemExists(int itemId, int mealId) throws DatabaseAccessException {
        return recordExists(CHECK_MEAL_ITEM_EXISTS_SQL, stmt -> {
            stmt.setInt(1, itemId);
            stmt.setInt(2, mealId);
        });
    }
}
