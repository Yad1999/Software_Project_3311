package com.nutrisci.database.constants;

/**
 * Database constants class containing SQL error codes, table names, column names,
 * and other magic numbers used throughout the database layer.
 * This eliminates magic numbers and provides a centralized location for database-related constants.
 */
public final class DatabaseConstants {
    
    // Prevent instantiation
    private DatabaseConstants() {
        throw new UnsupportedOperationException("Constants class cannot be instantiated");
    }
    
    // SQL Server Error Codes
    public static final int SQL_SERVER_UNIQUE_CONSTRAINT_VIOLATION = 2627;
    public static final int SQL_SERVER_FOREIGN_KEY_VIOLATION = 547;
    public static final int SQL_SERVER_CHECK_CONSTRAINT_VIOLATION = 547;
    
    // Table Names
    public static final String TABLE_USERS = "USERS";
    public static final String TABLE_LOGGED_MEALS = "LOGGED_MEALS";
    public static final String TABLE_MEAL_ITEMS = "MEAL_ITEMS";
    public static final String TABLE_FOOD_NAME = "FOOD_NAME";
    public static final String TABLE_NUTRIENT_AMOUNT = "NUTRIENT_AMOUNT";
    public static final String TABLE_NUTRIENT_NAME = "NUTRIENT_NAME";
    
    // Column Names - Users Table
    public static final String COL_USER_ID = "user_id";
    public static final String COL_USERNAME = "username";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD_HASH = "password_hash";
    public static final String COL_SEX = "sex";
    public static final String COL_DOB = "dob";
    public static final String COL_HEIGHT_CM = "height_cm";
    public static final String COL_WEIGHT_KG = "weight_kg";
    public static final String COL_UNIT_PREFERENCE = "unit_preference";
    public static final String COL_CREATED_AT = "created_at";
    
    // Column Names - Meal Tables
    public static final String COL_MEAL_ID = "meal_id";
    public static final String COL_MEAL_TYPE = "meal_type";
    public static final String COL_MEAL_DATE = "meal_date";
    public static final String COL_LOGGED_AT = "logged_at";
    public static final String COL_ITEM_ID = "item_id";
    public static final String COL_FOOD_ID = "food_id";
    public static final String COL_QUANTITY_GRAMS = "quantity_grams";
    
    // Column Names - Food/Nutrient Tables
    public static final String COL_FOOD_ID_CAPS = "FoodID";
    public static final String COL_FOOD_DESCRIPTION = "FoodDescription";
    public static final String COL_NUTRIENT_ID = "NutrientID";
    public static final String COL_NUTRIENT_NAME = "NutrientName";
    public static final String COL_NUTRIENT_VALUE = "NutrientValue";
    public static final String COL_NUTRIENT_UNIT = "NutrientUnit";
    
    // SQL Query Templates
    public static final String SELECT_ALL_USER_COLUMNS = 
        COL_USER_ID + ", " + COL_USERNAME + ", " + COL_EMAIL + ", " + COL_PASSWORD_HASH + ", " +
        COL_SEX + ", " + COL_DOB + ", " + COL_HEIGHT_CM + ", " + COL_WEIGHT_KG + ", " +
        COL_UNIT_PREFERENCE + ", " + COL_CREATED_AT;
    
    public static final String SELECT_ALL_MEAL_COLUMNS = 
        "m." + COL_MEAL_ID + ", m." + COL_MEAL_TYPE + ", m." + COL_MEAL_DATE + ", " +
        "m." + COL_USER_ID + ", m." + COL_LOGGED_AT;
    
    public static final String SELECT_ALL_MEAL_ITEM_COLUMNS = 
        "mi." + COL_ITEM_ID + ", mi." + COL_FOOD_ID + ", mi." + COL_QUANTITY_GRAMS;
    
    // Common WHERE Clauses
    public static final String WHERE_USERNAME = " WHERE " + COL_USERNAME + " = ?";
    public static final String WHERE_USER_ID = " WHERE " + COL_USER_ID + " = ?";
    public static final String WHERE_MEAL_ID = " WHERE " + COL_MEAL_ID + " = ?";
    public static final String WHERE_ITEM_ID = " WHERE " + COL_ITEM_ID + " = ?";
    public static final String WHERE_MEAL_ID_AND_USER_ID = " WHERE " + COL_MEAL_ID + " = ? AND " + COL_USER_ID + " = ?";
    public static final String WHERE_ITEM_ID_AND_MEAL_ID = " WHERE " + COL_ITEM_ID + " = ? AND " + COL_MEAL_ID + " = ?";
    
    // Common JOIN Clauses
    public static final String JOIN_MEAL_ITEMS = " LEFT JOIN " + TABLE_MEAL_ITEMS + " mi ON m." + COL_MEAL_ID + " = mi." + COL_MEAL_ID;
    public static final String JOIN_FOOD_NAME = " LEFT JOIN " + TABLE_FOOD_NAME + " fn ON mi." + COL_FOOD_ID + " = fn." + COL_FOOD_ID_CAPS;
    public static final String JOIN_NUTRIENT_AMOUNT = " LEFT JOIN " + TABLE_NUTRIENT_AMOUNT + " na ON fn." + COL_FOOD_ID_CAPS + " = na." + COL_FOOD_ID_CAPS;
    public static final String JOIN_NUTRIENT_NAME = " LEFT JOIN " + TABLE_NUTRIENT_NAME + " nn ON na." + COL_NUTRIENT_ID + " = nn." + COL_NUTRIENT_ID;
    
    // Common ORDER BY Clauses
    public static final String ORDER_BY_MEAL_DATE_DESC = " ORDER BY m." + COL_MEAL_DATE + " DESC, m." + COL_LOGGED_AT + " DESC";
    public static final String ORDER_BY_FOOD_DESCRIPTION = " ORDER BY fn." + COL_FOOD_DESCRIPTION;
    public static final String ORDER_BY_NUTRIENT_ID = " ORDER BY na." + COL_NUTRIENT_ID;
    
    // Search Patterns
    public static final String LIKE_PATTERN_PREFIX = "%";
    public static final String LIKE_PATTERN_SUFFIX = "%";
    
    // Validation Constants
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_USERNAME_LENGTH = 50;
    public static final int MAX_EMAIL_LENGTH = 255;
    public static final int MIN_HEIGHT_CM = 50;
    public static final int MAX_HEIGHT_CM = 300;
    public static final double MIN_WEIGHT_KG = 20.0;
    public static final double MAX_WEIGHT_KG = 500.0;
    
    // Default Values
    public static final String DEFAULT_UNIT_PREFERENCE = "metric";
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_SEARCH_RESULTS = 100;
    
    // Error Messages Templates
    public static final String ERROR_USER_NOT_FOUND = "User not found: ";
    public static final String ERROR_MEAL_NOT_FOUND = "Meal not found: ";
    public static final String ERROR_MEAL_ITEM_NOT_FOUND = "Meal item not found: ";
    public static final String ERROR_FOOD_NOT_FOUND = "Food not found: ";
    public static final String ERROR_UNAUTHORIZED_ACCESS = "User not authorized to access this ";
    public static final String ERROR_DUPLICATE_USER = "Username or email already exists";
    public static final String ERROR_INVALID_CREDENTIALS = "Invalid password for user: ";
    public static final String ERROR_NO_ROWS_AFFECTED = "Failed to %s - no rows affected";
    public static final String ERROR_FAILED_TO_RETRIEVE_KEY = "Failed to retrieve generated %s ID";
    
    // SQL Operation Types (for error messages)
    public static final String OPERATION_CREATE = "create";
    public static final String OPERATION_UPDATE = "update";
    public static final String OPERATION_DELETE = "delete";
    public static final String OPERATION_INSERT = "insert";
}