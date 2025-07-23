package com.nutrisci.database.dto;

/**
 * Enumeration representing the different types of meals that can be logged.
 */
public enum MealType {
    BREAKFAST("breakfast"),
    LUNCH("lunch"),
    DINNER("dinner"),
    SNACK("snack");
    
    private final String value;
    
    MealType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    /**
     * Converts a string value to MealType enum.
     * @param value String representation of meal type
     * @return Corresponding MealType enum
     * @throws IllegalArgumentException if value doesn't match any enum
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