package backend;

import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    private Controller controller;
    private MealLogger mealLogger;
    private UserProfile userProfile;
    private SwapEngineImpl swapEngine;

    private FoodItem apple, banana, tofu;

    @BeforeEach
    public void setup() {
        mealLogger = new MealLogger();
        userProfile = new UserProfile("testUser", "password123", "2000-01-01", 170.0, "male", true);
        swapEngine = new SwapEngineImpl();
        controller = new Controller(userProfile, mealLogger, swapEngine);

        apple = new FoodItem("0001","Apple", 50, 10, 0, 0);
        banana = new FoodItem("0002","Banana", 90, 20, 0, 0);
        tofu = new FoodItem("0003","Tofu", 100, 30, 5, 5);
    }

    private List<FoodItem> getSampleFoods() {
        List<FoodItem> list = new ArrayList<>();
        list.add(apple);
        list.add(banana);
        list.add(tofu);
        return list;
    }

    // UC1: Load User Profile
    @Test
    public void testGetUserProfile() {
        assertEquals("testUser", controller.getUserProfile().getUsername());
    }

    // UC2: Add Meal
    @Test
    public void testAddMeal() {
        LocalDate date = LocalDate.now();
        Meal meal = new Meal("Lunch", date);
        meal.addFoodItem(apple);
        controller.logMeal(date, meal);
        assertEquals(1, controller.getMealLogger().getAllMeals().get(date).size());
    }

    // UC2: View Nutrition Summary
    @Test
    public void testGetNutritionSummaryForDate() {
        LocalDate date = LocalDate.now();
        Meal meal = new Meal("Lunch", date);
        meal.addFoodItem(banana);
        controller.logMeal(date, meal);
        NutritionSummary summary = controller.getNutritionSummaryForDate(date);
        assertEquals(20, summary.get("protein"));
    }

    // UC3: Set Nutrition Goals
    @Test
    public void testSetAndGetGoals() {
        NutritionGoal goal = new NutritionGoal("protein", 50, Double.MAX_VALUE);
        List<NutritionGoal> goals = new ArrayList<>();
        goals.add(goal);
        controller.setGoals(goals);
        assertEquals(50, controller.getGoals().get(0).getTargetAmount());
    }

    // UC4: Check Goal Fulfillment
    @Test
    public void testGetUnmetGoals() {
        LocalDate date = LocalDate.now();
        Meal meal = new Meal("Lunch", date);
        meal.addFoodItem(apple);
        controller.logMeal(date, meal);

        List<NutritionGoal> goals = new ArrayList<>();
        goals.add(new NutritionGoal("protein", 100, Double.MAX_VALUE));
        controller.setGoals(goals);

        List<NutritionGoal> unmet = controller.getUnmetGoals(date);
        assertEquals(1, unmet.size());
    }

    // UC5: SuggestFoodSwap
    @Test
    public void testSuggestFoodSwap() {
        List<FoodItem> candidates = new ArrayList<>();
        UserProfile userProfile = new UserProfile("testUser", "password123", "2000-01-01", 170.0, "male", true);
        MealLogger mealLogger = new MealLogger();
        SwapEngine swapEngine = new SwapEngineImpl();

        Controller controller = new Controller(userProfile, mealLogger, swapEngine);


        FoodItem apple = new FoodItem("0001", "Apple", 95, 0.5, 0.3, 25);
        FoodItem chicken = new FoodItem("0002", "Chicken Breast", 165, 31.0, 3.6, 0);
        FoodItem rice = new FoodItem("0003", "White Rice", 200, 4.0, 0.4, 45);

        candidates.add(apple);
        candidates.add(chicken);
        candidates.add(rice);

        userProfile.setNutritionGoal(new NutritionGoal("protein", 20.0, 40.0));

        List<FoodItem> suggestions = controller.suggestFoodSwaps("protein", candidates);

        assertFalse(suggestions.isEmpty());
        assertTrue(suggestions.stream()
                .anyMatch(item -> item.getName().equalsIgnoreCase("Chicken Breast")));
    }



    // UC6: Analyze Nutrition Trend
    @Test
    public void testAnalyzeNutritionRange() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        Meal m1 = new Meal("Lunch", today);
        m1.addFoodItem(banana);
        controller.logMeal(yesterday, m1);

        Meal m2 = new Meal("Lunch", yesterday);
        m2.addFoodItem(tofu);
        controller.logMeal(today, m2);

        NutritionSummary range = controller.analyzeNutritionRange(yesterday, today);
        assertTrue(range.get("protein") > 0);
    }

    // UC7: View Deficiency Analysis (vs Canada Food Guide)
    @Test
    public void testCompareToCanadaGuide() {
        LocalDate date = LocalDate.now();
        Meal meal = new Meal("Lunch", date);
        meal.addFoodItem(apple);
        controller.logMeal(date, meal);

        Map<String, Double> diff = controller.getDifferenceFromCanadaGuide(date);
        assertTrue(diff.containsKey("protein"));
    }

    // UC7: AnalyzeNutritionTrend
    @Test
    public void testAddMultipleMeals() {
        LocalDate date = LocalDate.now();
        Meal m1 = new Meal("Lunch", date);
        m1.addFoodItem(apple);
        Meal m2 = new Meal("Lunch", date);
        m2.addFoodItem(tofu);

        controller.logMeal(date, m1);
        controller.logMeal(date, m2);
        assertEquals(2, controller.getMealLogger().getAllMeals().get(date).size());
    }

    // Extra 1
    @Test
    public void testClearGoals() {
        controller.setGoals(new ArrayList<>());
        assertTrue(controller.getGoals().isEmpty());
    }

    // Extra 2
    @Test
    public void testUserProfileSexAndUnit() {
        assertEquals("male", controller.getUserProfile().getSex());
        assertTrue(controller.getUserProfile().isMetric());
    }

    // Extra 3
    @Test
    public void testSuggestProteinFoodOnly() {
        controller.getUserProfile().setNutritionGoal(new NutritionGoal("protein", 20.0, 40.0));

        List<FoodItem> suggestions = controller.suggestFoodSwaps("protein", getSampleFoods());

        for (FoodItem fi : suggestions) {
            assertTrue(fi.getNutrient("protein") > 0);
        }
    }

}
