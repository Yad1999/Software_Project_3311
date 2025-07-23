package com.nutrisci.database.dao;

import com.nutrisci.database.dto.Food;
import com.nutrisci.database.dto.NutrientConstraint;
import com.nutrisci.database.exceptions.*;

import java.util.List;

/**
 * Data Access Object interface for Canadian Nutrient File (CNF) data operations.
 * Handles retrieval of food and nutrient information from the CNF database tables.
 */
public interface ICNFDataDAO {
    
    /**
     * Retrieves detailed nutrient data for a specific food item.
     * Joins FOOD_NAME, NUTRIENT_AMOUNT, and NUTRIENT_NAME tables.
     * 
     * @param foodName The name of the food to retrieve nutrient data for
     * @return Food object containing nutrient information
     * @throws DatabaseAccessException if database access fails
     * @throws FoodNotFoundException if the food is not found in the CNF database
     */
    Food retrieveNutrientDataForFood(String foodName) 
            throws DatabaseAccessException, FoodNotFoundException;
    
    /**
     * Searches for replacement foods that match specified nutrient constraints.
     * Excludes foods in the exclusion list.
     * 
     * @param constraints List of nutrient constraints (nutrient ID with min/max values)
     * @param excludedFoodIds List of food IDs to exclude from results
     * @return List of Food objects matching the criteria
     * @throws DatabaseAccessException if database access fails
     */
    List<Food> searchReplacementFoods(List<NutrientConstraint> constraints, List<Integer> excludedFoodIds) 
            throws DatabaseAccessException;
}