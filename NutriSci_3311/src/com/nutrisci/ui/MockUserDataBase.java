package com.nutrisci.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A mock user database class for testing the NutriSci application's UI components.
 * 
 * <p>This class simulates user account management and meal logging functionality.
 * It is intended for temporary use during development and testing, and will be
 * replaced with a persistent database in the final version.</p>
 */
public class MockUserDataBase {

    /** Stores username-password pairs for mock login verification. */
    private HashMap<String, String> mockProfileDB;

    /** Stores meal logs for each user (keyed by username). */
    private HashMap<String, List<String>> userMealLogs = new HashMap<>();

    /**
     * Constructs a new mock user database with some preloaded user credentials.
     */
    public MockUserDataBase() {
        mockProfileDB = new HashMap<>();
        mockProfileDB.put("User1", "userpassword1");
        mockProfileDB.put("User2", "userpassword2");
        mockProfileDB.put("User3", "userpassword3");
    }

    /**
     * Returns the mock profile database.
     *
     * @return a HashMap containing username-password pairs.
     */
    public HashMap<String, String> getProfileDB() {
        return mockProfileDB;
    }

    /**
     * Checks whether a user with the specified username exists in the mock database.
     *
     * @param username the username to check.
     * @return true if the user exists, false otherwise.
     */
    public boolean userExists(String username) {
        return mockProfileDB.containsKey(username);
    }

    /**
     * Adds a new user to the mock database.
     *
     * @param username the username to add.
     * @param password the password associated with the username.
     */
    public void addUser(String username, String password) {
        mockProfileDB.put(username, password);
    }

    /**
     * Temporarily stores a meal log entry for the specified user.
     * 
     * <p>Each meal log contains a header with the date and meal type, followed by
     * individual meal entries. Intended for mock testing only.</p>
     *
     * @param username the user for whom the meal is logged.
     * @param date the date of the meal.
     * @param mealType the type of the meal (e.g., Breakfast, Lunch).
     * @param entries a list of MealEntry objects representing the meal contents.
     */
    public void addMeal(String username, String date, String mealType, List<MealEntry> entries) {
        StringBuilder mealRecord = new StringBuilder();
        mealRecord.append("Date: ").append(date).append(", Meal: ").append(mealType).append("\n");

        for (MealEntry e : entries) {
            mealRecord.append("  - ").append(e.toString()).append("\n");
        }

        userMealLogs.putIfAbsent(username, new ArrayList<>());
        userMealLogs.get(username).add(mealRecord.toString());

        System.out.println("Meal saved to mock database for user '" + username + "'.");
    }

    /**
     * Retrieves all logged meals for the specified user.
     *
     * @param username the username whose meals should be retrieved.
     * @return a list of meal descriptions, or an empty list if none are found.
     */
    public List<String> getMeals(String username) {
        return userMealLogs.getOrDefault(username, new ArrayList<>());
    }
}
