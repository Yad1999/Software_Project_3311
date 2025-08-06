package com.nutrisci.database.dao.impl;

import com.nutrisci.database.DatabaseConnectionManager;
import com.nutrisci.database.dao.ICNFDataDAO;
import com.nutrisci.database.dto.Food;
import com.nutrisci.database.dto.FoodNutrient;
import com.nutrisci.database.dto.NutrientConstraint;
import com.nutrisci.database.exceptions.*;
import com.nutrisci.database.constants.DatabaseConstants;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ICNFDataDAO for SQL Server database operations.
 * Provides concrete logic for Canadian Nutrient File data retrieval using JDBC.
 * Extends BaseDAO to leverage common database operation patterns and eliminate duplicate code.
 */
public class CNFDataDAOImpl extends BaseDAO implements ICNFDataDAO {
    
    // SQL Query Constants using DatabaseConstants
    private static final String SELECT_FOOD_WITH_NUTRIENTS_SQL = 
        "SELECT fn." + DatabaseConstants.COL_FOOD_ID_CAPS + ", fn." + DatabaseConstants.COL_FOOD_DESCRIPTION + ", " +
        "na." + DatabaseConstants.COL_NUTRIENT_ID + ", nn." + DatabaseConstants.COL_NUTRIENT_NAME + ", " +
        "na." + DatabaseConstants.COL_NUTRIENT_VALUE + ", nn." + DatabaseConstants.COL_NUTRIENT_UNIT + " " +
        "FROM " + DatabaseConstants.TABLE_FOOD_NAME + " fn " +
        DatabaseConstants.JOIN_NUTRIENT_AMOUNT +
        DatabaseConstants.JOIN_NUTRIENT_NAME +
        " WHERE fn." + DatabaseConstants.COL_FOOD_DESCRIPTION + " LIKE ?" +
        DatabaseConstants.ORDER_BY_NUTRIENT_ID;
    
    private static final String SELECT_REPLACEMENT_FOODS_BASE = 
        "SELECT DISTINCT fn." + DatabaseConstants.COL_FOOD_ID_CAPS + ", fn." + DatabaseConstants.COL_FOOD_DESCRIPTION + " " +
        "FROM " + DatabaseConstants.TABLE_FOOD_NAME + " fn ";
    
    private static final String REPLACEMENT_FOODS_WHERE_CLAUSE = " WHERE ";
    private static final String REPLACEMENT_FOODS_ORDER_BY = DatabaseConstants.ORDER_BY_FOOD_DESCRIPTION;
    private static final String NUTRIENT_CONSTRAINT_TEMPLATE = 
        "(na%d." + DatabaseConstants.COL_NUTRIENT_ID + " = ? AND na%d." + DatabaseConstants.COL_NUTRIENT_VALUE + " BETWEEN ? AND ?) ";
    private static final String JOIN_NUTRIENT_AMOUNT_TEMPLATE = 
        "JOIN " + DatabaseConstants.TABLE_NUTRIENT_AMOUNT + " na%d ON fn." + DatabaseConstants.COL_FOOD_ID_CAPS + " = na%d." + DatabaseConstants.COL_FOOD_ID_CAPS + " ";
    private static final String EXCLUSION_CLAUSE_PREFIX = " AND fn." + DatabaseConstants.COL_FOOD_ID_CAPS + " NOT IN (";
    private static final String EXCLUSION_CLAUSE_SUFFIX = ") ";
    
    // Search configuration constants
    private static final int MAX_CONSTRAINT_PARAMS = 3; // nutrientId, minValue, maxValue
    
    @Override
    public Food retrieveNutrientDataForFood(String foodName) 
            throws DatabaseAccessException, FoodNotFoundException {
        
        String searchPattern = DatabaseConstants.LIKE_PATTERN_PREFIX + foodName + DatabaseConstants.LIKE_PATTERN_SUFFIX;
        
        List<FoodData> foodDataList = new ArrayList<>();
        
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_FOOD_WITH_NUTRIENTS_SQL)) {
            
            stmt.setString(1, searchPattern);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    FoodData foodData = extractFoodDataFromResultSet(rs);
                    foodDataList.add(foodData);
                }
            }
            
        } catch (SQLException e) {
            throw new DatabaseAccessException("Failed to retrieve nutrient data for food", e);
        }
        
        if (foodDataList.isEmpty()) {
            throw new FoodNotFoundException(DatabaseConstants.ERROR_FOOD_NOT_FOUND + foodName);
        }
        
        return buildFoodFromDataList(foodDataList);
    }
    
    /**
     * Extracts food and nutrient data from a result set row.
     */
    private FoodData extractFoodDataFromResultSet(ResultSet rs) throws SQLException {
        int foodId = rs.getInt(DatabaseConstants.COL_FOOD_ID_CAPS);
        String foodDescription = rs.getString(DatabaseConstants.COL_FOOD_DESCRIPTION);
        
        FoodNutrient nutrient = null;
        if (rs.getObject(DatabaseConstants.COL_NUTRIENT_ID) != null) {
            int nutrientId = rs.getInt(DatabaseConstants.COL_NUTRIENT_ID);
            String nutrientName = rs.getString(DatabaseConstants.COL_NUTRIENT_NAME);
            BigDecimal nutrientValue = rs.getBigDecimal(DatabaseConstants.COL_NUTRIENT_VALUE);
            String nutrientUnit = rs.getString(DatabaseConstants.COL_NUTRIENT_UNIT);
            
            if (nutrientValue != null) {
                nutrient = new FoodNutrient(nutrientId, nutrientName, nutrientValue, nutrientUnit);
            }
        }
        
        return new FoodData(foodId, foodDescription, nutrient);
    }
    
    /**
     * Builds a Food object from the collected food data.
     */
    private Food buildFoodFromDataList(List<FoodData> foodDataList) {
        FoodData firstEntry = foodDataList.get(0);
        Food food = Food.builder()
            .foodId(firstEntry.foodId)
            .foodName(firstEntry.foodDescription)
            .build();
        
        List<FoodNutrient> nutrients = new ArrayList<>();
        for (FoodData data : foodDataList) {
            if (data.nutrient != null) {
                nutrients.add(data.nutrient);
            }
        }
        
        food.setNutrients(nutrients);
        return food;
    }
    
    @Override
    public List<Food> searchReplacementFoods(List<NutrientConstraint> constraints, List<Integer> excludedFoodIds) 
            throws DatabaseAccessException {
        
        if (constraints == null || constraints.isEmpty()) {
            return new ArrayList<>();
        }
        
        String sql = buildReplacementFoodsQuery(constraints, excludedFoodIds);
        
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setReplacementFoodsParameters(stmt, constraints, excludedFoodIds);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return buildFoodListFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            throw new DatabaseAccessException("Failed to search replacement foods", e);
        }
    }
    
    /**
     * Builds the dynamic SQL query for replacement food search.
     */
    private String buildReplacementFoodsQuery(List<NutrientConstraint> constraints, List<Integer> excludedFoodIds) {
        StringBuilder sqlBuilder = new StringBuilder(SELECT_REPLACEMENT_FOODS_BASE);
        
        // Add JOIN clauses for each constraint
        for (int i = 0; i < constraints.size(); i++) {
            sqlBuilder.append(String.format(JOIN_NUTRIENT_AMOUNT_TEMPLATE, i, i));
        }
        
        sqlBuilder.append(REPLACEMENT_FOODS_WHERE_CLAUSE);
        
        // Add nutrient constraints
        for (int i = 0; i < constraints.size(); i++) {
            if (i > NO_ROWS_AFFECTED) {
                sqlBuilder.append("AND ");
            }
            sqlBuilder.append(String.format(NUTRIENT_CONSTRAINT_TEMPLATE, i, i));
        }
        
        // Add exclusion clause if needed
        if (excludedFoodIds != null && !excludedFoodIds.isEmpty()) {
            sqlBuilder.append(EXCLUSION_CLAUSE_PREFIX);
            for (int i = 0; i < excludedFoodIds.size(); i++) {
                if (i > NO_ROWS_AFFECTED) {
                    sqlBuilder.append(", ");
                }
                sqlBuilder.append("?");
            }
            sqlBuilder.append(EXCLUSION_CLAUSE_SUFFIX);
        }
        
        sqlBuilder.append(REPLACEMENT_FOODS_ORDER_BY);
        return sqlBuilder.toString();
    }
    
    /**
     * Sets parameters for the replacement foods query.
     */
    private void setReplacementFoodsParameters(PreparedStatement stmt, List<NutrientConstraint> constraints, 
                                             List<Integer> excludedFoodIds) throws SQLException {
        int paramIndex = FIRST_GENERATED_KEY_INDEX;
        
        // Set constraint parameters
        for (NutrientConstraint constraint : constraints) {
            stmt.setInt(paramIndex++, constraint.getNutrientId());
            stmt.setBigDecimal(paramIndex++, constraint.getMinValue());
            stmt.setBigDecimal(paramIndex++, constraint.getMaxValue());
        }
        
        // Set exclusion parameters
        if (excludedFoodIds != null && !excludedFoodIds.isEmpty()) {
            for (Integer excludedId : excludedFoodIds) {
                stmt.setInt(paramIndex++, excludedId);
            }
        }
    }
    
    /**
     * Builds a list of Food objects from the result set.
     */
    private List<Food> buildFoodListFromResultSet(ResultSet rs) throws SQLException {
        List<Food> foods = new ArrayList<>();
        
        while (rs.next()) {
            int foodId = rs.getInt(DatabaseConstants.COL_FOOD_ID_CAPS);
            String foodDescription = rs.getString(DatabaseConstants.COL_FOOD_DESCRIPTION);
            
            Food food = Food.builder()
                .foodId(foodId)
                .foodName(foodDescription)
                .build();
            foods.add(food);
            
            // Limit results to prevent excessive memory usage
            if (foods.size() >= DatabaseConstants.MAX_SEARCH_RESULTS) {
                break;
            }
        }
        
        return foods;
    }
    
    /**
     * Helper class to hold food data during processing.
     */
    private static class FoodData {
        final int foodId;
        final String foodDescription;
        final FoodNutrient nutrient;
        
        FoodData(int foodId, String foodDescription, FoodNutrient nutrient) {
            this.foodId = foodId;
            this.foodDescription = foodDescription;
            this.nutrient = nutrient;
        }
    }
}