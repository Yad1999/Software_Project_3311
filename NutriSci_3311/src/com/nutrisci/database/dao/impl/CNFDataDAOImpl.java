package com.nutrisci.database.dao.impl;

import com.nutrisci.database.DatabaseConnectionManager;
import com.nutrisci.database.dao.ICNFDataDAO;
import com.nutrisci.database.dto.Food;
import com.nutrisci.database.dto.FoodNutrient;
import com.nutrisci.database.dto.NutrientConstraint;
import com.nutrisci.database.exceptions.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ICNFDataDAO for SQL Server database operations.
 * Provides concrete logic for Canadian Nutrient File data retrieval using JDBC.
 */
public class CNFDataDAOImpl implements ICNFDataDAO {
    
    @Override
    public Food retrieveNutrientDataForFood(String foodName) 
            throws DatabaseAccessException, FoodNotFoundException {
        
        String sql = "SELECT fn.FoodID, fn.FoodDescription, " +
                     "na.NutrientID, nn.NutrientName, na.NutrientValue, nn.NutrientUnit " +
                     "FROM FOOD_NAME fn " +
                     "LEFT JOIN NUTRIENT_AMOUNT na ON fn.FoodID = na.FoodID " +
                     "LEFT JOIN NUTRIENT_NAME nn ON na.NutrientID = nn.NutrientID " +
                     "WHERE fn.FoodDescription LIKE ? " +
                     "ORDER BY na.NutrientID";
        
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + foodName + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                Food food = null;
                List<FoodNutrient> nutrients = new ArrayList<>();
                
                while (rs.next()) {
                    if (food == null) {
                        // First row - create Food object
                        int foodId = rs.getInt("FoodID");
                        String foodDescription = rs.getString("FoodDescription");
                        food = new Food(foodId, foodDescription);
                    }
                    
                    // Add nutrient if available
                    if (rs.getObject("NutrientID") != null) {
                        int nutrientId = rs.getInt("NutrientID");
                        String nutrientName = rs.getString("NutrientName");
                        BigDecimal nutrientValue = rs.getBigDecimal("NutrientValue");
                        String nutrientUnit = rs.getString("NutrientUnit");
                        
                        if (nutrientValue != null) {
                            FoodNutrient nutrient = new FoodNutrient(nutrientId, nutrientName, 
                                                                   nutrientValue, nutrientUnit);
                            nutrients.add(nutrient);
                        }
                    }
                }
                
                if (food == null) {
                    throw new FoodNotFoundException("Food not found: " + foodName);
                }
                
                food.setNutrients(nutrients);
                return food;
                
            }
            
        } catch (FoodNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseAccessException("Failed to retrieve nutrient data for food", e);
        }
    }
    
    @Override
    public List<Food> searchReplacementFoods(List<NutrientConstraint> constraints, List<Integer> excludedFoodIds) 
            throws DatabaseAccessException {
        
        if (constraints == null || constraints.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Build dynamic SQL query
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT DISTINCT fn.FoodID, fn.FoodDescription ");
        sqlBuilder.append("FROM FOOD_NAME fn ");
        
        // Join with NUTRIENT_AMOUNT for each constraint
        for (int i = 0; i < constraints.size(); i++) {
            sqlBuilder.append("JOIN NUTRIENT_AMOUNT na").append(i)
                     .append(" ON fn.FoodID = na").append(i).append(".FoodID ");
        }
        
        sqlBuilder.append("WHERE ");
        
        // Add nutrient constraints
        for (int i = 0; i < constraints.size(); i++) {
            if (i > 0) {
                sqlBuilder.append("AND ");
            }
            sqlBuilder.append("(na").append(i).append(".NutrientID = ? ")
                     .append("AND na").append(i).append(".NutrientValue BETWEEN ? AND ?) ");
        }
        
        // Add exclusion clause if needed
        if (excludedFoodIds != null && !excludedFoodIds.isEmpty()) {
            sqlBuilder.append("AND fn.FoodID NOT IN (");
            for (int i = 0; i < excludedFoodIds.size(); i++) {
                if (i > 0) sqlBuilder.append(", ");
                sqlBuilder.append("?");
            }
            sqlBuilder.append(") ");
        }
        
        sqlBuilder.append("ORDER BY fn.FoodDescription");
        
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlBuilder.toString())) {
            
            int paramIndex = 1;
            
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
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Food> foods = new ArrayList<>();
                
                while (rs.next()) {
                    int foodId = rs.getInt("FoodID");
                    String foodDescription = rs.getString("FoodDescription");
                    
                    Food food = new Food(foodId, foodDescription);
                    foods.add(food);
                }
                
                return foods;
            }
            
        } catch (SQLException e) {
            throw new DatabaseAccessException("Failed to search replacement foods", e);
        }
    }
}