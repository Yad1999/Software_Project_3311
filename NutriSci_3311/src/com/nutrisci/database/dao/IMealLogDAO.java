package com.nutrisci.database.dao;

import com.nutrisci.database.dto.Meal;
import com.nutrisci.database.dto.MealItem;
import com.nutrisci.database.exceptions.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Access Object interface for meal logging operations.
 * Defines contracts for managing logged meals and meal items.
 */
public interface IMealLogDAO {
    
    /**
     * Logs a new meal with all its associated meal items.
     * This operation should be transactional.
     * 
     * @param userId The ID of the user logging the meal
     * @param meal Meal object containing meal details and items
     * @return The meal_id of the logged meal
     * @throws DatabaseAccessException if database access fails
     */
    int logMeal(int userId, Meal meal) throws DatabaseAccessException;
    
    /**
     * Loads logged meals for a user within a specified date range.
     * Includes nested meal items with food names.
     * 
     * @param userId The user's ID
     * @param startDate Start date of the range (inclusive)
     * @param endDate End date of the range (inclusive)
     * @return List of meals with their associated meal items
     * @throws DatabaseAccessException if database access fails
     */
    List<Meal> loadLoggedMeals(int userId, LocalDate startDate, LocalDate endDate) 
            throws DatabaseAccessException;
    
    /**
     * Removes a specific meal item from a logged meal.
     * If no items remain for the meal, the meal record is also deleted.
     * 
     * @param userId The user's ID (for authorization)
     * @param mealId The meal's ID
     * @param itemId The meal item's ID
     * @return true if the item was successfully removed
     * @throws DatabaseAccessException if database access fails
     * @throws MealNotFoundException if meal doesn't exist
     * @throws MealItemNotFoundException if meal item doesn't exist
     * @throws UnauthorizedAccessException if user doesn't own the meal
     */
    boolean removeMealItem(int userId, int mealId, int itemId) 
            throws DatabaseAccessException, MealNotFoundException, MealItemNotFoundException, UnauthorizedAccessException;
    
    /**
     * Adds a new item to an existing meal.
     * 
     * @param userId The user's ID (for authorization)
     * @param mealId The meal's ID
     * @param item MealItem object containing food and quantity information
     * @return The item_id of the added meal item
     * @throws DatabaseAccessException if database access fails
     * @throws MealNotFoundException if meal doesn't exist
     * @throws UnauthorizedAccessException if user doesn't own the meal
     */
    int addMealItem(int userId, int mealId, MealItem item) 
            throws DatabaseAccessException, MealNotFoundException, UnauthorizedAccessException;
}