package com.nutrisci.database.dto;

/**
 * Enumeration representing the different types of meals that can be logged in the NutriSci application.
 * This enum provides a standardized way to categorize meals for nutritional tracking and analysis.
 * 
 * <p>The meal types are designed to align with common dietary patterns and allow users to
 * track their nutritional intake across different eating occasions throughout the day.</p>
 * 
 * @author NutriSci Development Team
 * @version 1.0
 * @since 1.0
 */
public enum MealType {
    /** Morning meal, typically consumed before 11 AM */
    BREAKFAST("breakfast"),
    
    /** Midday meal, typically consumed between 11 AM and 2 PM */
    LUNCH("lunch"),
    
    /** Evening meal, typically consumed after 5 PM */
    DINNER("dinner"),
    
    /** Small meal or food consumed between main meals */
    SNACK("snack");
    
    private final String value;
    
    /**
     * Constructs a MealType with the specified string value.
     * 
     * @param value the string representation used in database storage
     */
    MealType(String value) {
        this.value = value;
    }
    
    /**
     * Gets the string value used for database storage and display.
     * 
     * @return the lowercase string representation of the meal type
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Converts a string value to MealType enum.
     * This method performs case-insensitive matching to handle various input formats.
     * 
     * @param value String representation of meal type (case-insensitive)
     * @return Corresponding MealType enum value
     * @throws IllegalArgumentException if value doesn't match any enum constant
     */
    public static MealType fromString(String value) {
        for (MealType type : MealType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid meal type: " + value);
    }
}