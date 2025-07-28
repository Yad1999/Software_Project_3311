package com.nutrisci.database;

import com.nutrisci.database.dao.IUserProfileDAO;
import com.nutrisci.database.dao.impl.UserProfileDAOImpl;
import com.nutrisci.database.dao.impl.MealLogDAOImpl;
import com.nutrisci.database.dto.User;
import com.nutrisci.database.dto.Meal;
import com.nutrisci.database.dto.MealItem;
import com.nutrisci.database.dto.MealType;
import com.nutrisci.database.exceptions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Comprehensive test class for DAO implementations.
 * Tests UserProfileDAO and MealLogDAO with actual database operations.
 */
public class DAOImplementationTest {
    
    private static final IUserProfileDAO userDAO = new UserProfileDAOImpl();
    private static final MealLogDAOImpl mealDAO = new MealLogDAOImpl();
    
    public static void main(String[] args) {
        System.out.println("Starting DAO Implementation Tests...");
        
        DAOImplementationTest test = new DAOImplementationTest();
        
        // Test User DAO operations
        test.testUserProfileDAO();
        
        // Test Meal DAO operations (requires a user to exist)
        test.testMealLogDAO();
        
        System.out.println("\nDAO Implementation Tests Completed!");
    }
    
    /**
     * Test UserProfileDAO operations: create, authenticate, load
     */
    public void testUserProfileDAO() {
        System.out.println("\n=== Testing UserProfileDAO ===");
        
        try {
            // Test 1: Create a new user profile
            System.out.println("\n1. Testing createUserProfile():");
            User newUser = new User(
                "testuser_" + System.currentTimeMillis(), // Unique username
                "test@nutrisci.com",
                "hashedpassword123", // In real app, this would be properly hashed
                'M',
                LocalDate.of(1990, 5, 15),
                175, // height in cm
                70.5, // weight in kg
                "metric"
            );
            
            int userId = userDAO.createUserProfile(newUser);
            System.out.println("✓ User created successfully with ID: " + userId);
            
            // Test 2: Load user profile by ID
            System.out.println("\n2. Testing loadUserProfile():");
            User loadedUser = userDAO.loadUserProfile(userId);
            System.out.println("✓ User loaded successfully:");
            System.out.println("  - Username: " + loadedUser.getUsername());
            System.out.println("  - Email: " + loadedUser.getEmail());
            System.out.println("  - Sex: " + loadedUser.getSex());
            System.out.println("  - Height: " + loadedUser.getHeightCm() + " cm");
            System.out.println("  - Weight: " + loadedUser.getWeightKg() + " kg");
            System.out.println("  - Unit Preference: " + loadedUser.getUnitPreference());
            
            // Test 3: Authenticate user
            System.out.println("\n3. Testing authenticateUser():");
            User authenticatedUser = userDAO.authenticateUser(newUser.getUsername(), "hashedpassword123");
            System.out.println("✓ User authenticated successfully: " + authenticatedUser.getUsername());
            
            // Test 4: Test duplicate user creation (should fail)
            System.out.println("\n4. Testing duplicate user detection:");
            try {
                User duplicateUser = new User(
                    newUser.getUsername(), // Same username
                    "different@email.com",
                    "differenthash",
                    'F',
                    LocalDate.of(1995, 1, 1),
                    160,
                    60.0,
                    "imperial"
                );
                userDAO.createUserProfile(duplicateUser);
                System.out.println("✗ Duplicate user creation should have failed!");
            } catch (DuplicateUserException e) {
                System.out.println("✓ Duplicate user correctly rejected: " + e.getMessage());
            }
            
            // Test 5: Test invalid authentication
            System.out.println("\n5. Testing invalid authentication:");
            try {
                userDAO.authenticateUser(newUser.getUsername(), "wrongpassword");
                System.out.println("✗ Invalid authentication should have failed!");
            } catch (InvalidCredentialsException e) {
                System.out.println("✓ Invalid credentials correctly rejected: " + e.getMessage());
            }
            
            // Store userId for meal tests
            testUserId = userId;
            
        } catch (Exception e) {
            System.err.println("✗ UserProfileDAO test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static int testUserId = -1;
    
    /**
     * Test MealLogDAO operations: log meal, load meals, remove items
     */
    public void testMealLogDAO() {
        System.out.println("\n=== Testing MealLogDAO ===");
        
        if (testUserId == -1) {
            System.out.println("Skipping MealLogDAO tests - no test user available");
            return;
        }
        
        try {
            // Test 1: Log a meal with items
            System.out.println("\n1. Testing logMeal():");
            
            // Create a meal (breakfast)
            Meal testMeal = new Meal(MealType.BREAKFAST, LocalDate.now(), testUserId);
            
            // Add some meal items (using food IDs that should exist in CNF database)
            // Note: These food IDs might need to be adjusted based on your actual food data
            testMeal.addMealItem(new MealItem(0, 0, 1001, new BigDecimal("100.0"), "Test Food 1"));
            testMeal.addMealItem(new MealItem(0, 0, 1002, new BigDecimal("150.0"), "Test Food 2"));
            
            int mealId = mealDAO.logMeal(testUserId, testMeal);
            System.out.println("✓ Meal logged successfully with ID: " + mealId);
            
            // Test 2: Load logged meals
            System.out.println("\n2. Testing loadLoggedMeals():");
            LocalDate startDate = LocalDate.now().minusDays(1);
            LocalDate endDate = LocalDate.now().plusDays(1);
            
            List<Meal> loadedMeals = mealDAO.loadLoggedMeals(testUserId, startDate, endDate);
            System.out.println("✓ Loaded " + loadedMeals.size() + " meal(s)");
            
            for (Meal meal : loadedMeals) {
                System.out.println("  - Meal ID: " + meal.getMealId());
                System.out.println("  - Type: " + meal.getMealType());
                System.out.println("  - Date: " + meal.getMealDate());
                System.out.println("  - Items: " + meal.getMealItems().size());
                
                for (MealItem item : meal.getMealItems()) {
                    System.out.println("    * Food ID: " + item.getFoodId() + 
                                     ", Quantity: " + item.getQuantityGrams() + "g");
                }
            }
            
            // Test 3: Remove a meal item (if we have items)
            if (!loadedMeals.isEmpty() && !loadedMeals.get(0).getMealItems().isEmpty()) {
                System.out.println("\n3. Testing removeMealItem():");
                Meal firstMeal = loadedMeals.get(0);
                MealItem firstItem = firstMeal.getMealItems().get(0);
                
                boolean removed = mealDAO.removeMealItem(testUserId, firstMeal.getMealId(), firstItem.getItemId());
                System.out.println("✓ Meal item removed: " + removed);
            }
            
            // Test 4: Test unauthorized access
            System.out.println("\n4. Testing unauthorized access:");
            try {
                // Try to access with wrong user ID
                mealDAO.loadLoggedMeals(99999, startDate, endDate);
                System.out.println("✓ Unauthorized access test passed (no exception expected for load)");
            } catch (Exception e) {
                System.out.println("Note: Exception during unauthorized access test: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.err.println("✗ MealLogDAO test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}