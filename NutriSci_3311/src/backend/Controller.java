//package backend;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * The Controller class serves as the central coordinator between the UI/frontend and the backend logic.
// * It interacts with MealLogger, SwapEngine, NutritionCalculator, and other components to fulfill various use cases.
// */
//public class Controller {
//    private final UserProfile userProfile;
//    private final MealLogger mealLogger;
//    private final SwapEngine swapEngine;
//    private final ProfileManager profileManager = new ProfileManager();
//
//    /**
//     * Initializes the Controller with the required backend components.
//     */
//    public Controller(UserProfile userProfile, MealLogger mealLogger, SwapEngine swapEngine) {
//        this.userProfile = userProfile;
//        this.mealLogger = mealLogger;
//        this.swapEngine = swapEngine;
//    }
//
//    public NutritionSummary getNutritionSummaryForDate(LocalDate date) {
//        Meal meal = mealLogger.getMeal(date, "Lunch");
//        return NutritionCalculator.calculateMealNutrition(meal);
//    }
//
//    /**
//     * Logs a meal for a specific date.
//     *
//     * @param date the date of the meal
//     * @param meal the meal to be logged
//     */
//    public void logMeal(LocalDate date, Meal meal) {
//        mealLogger.logMeal(date, meal);
//    }
//
//    /**
//     * Returns the current user's profile.
//     */
//    public UserProfile getUserProfile() {
//        return userProfile;
//    }
//
//    public MealLogger getMealLogger() {
//        return mealLogger;
//    }
//
//    /**
//     * Sets a list of nutrition goals for the user.
//     */
//    public void setGoals(List<NutritionGoal> goals) {
//        userProfile.setGoals(goals);
//    }
//
//    /**
//     * Retrieves the list of nutrition goals for the user.
//     */
//    public List<NutritionGoal> getGoals() {
//        return userProfile.getNutritionGoals();
//    }
//
//    public List<NutritionGoal> getUnmetGoals(LocalDate date) {
//        Meal meal = mealLogger.getMeal(date, "Lunch");
//        NutritionSummary summary = NutritionCalculator.calculateMealNutrition(meal);
//        List<NutritionGoal> unmet = new ArrayList<>();
//        for (NutritionGoal goal : userProfile.getNutritionGoals()) {
//            if (!goal.isSatisfiedBy(summary)) {
//                unmet.add(goal);
//            }
//        }
//        return unmet;
//    }
//
//    public NutritionSummary analyzeNutritionRange(LocalDate startDate, LocalDate endDate) {
//        DataAnalyzer analyzer = new DataAnalyzer(mealLogger);
//        return analyzer.analyzeRange(startDate, endDate);
//    }
//
//    public Map<String, Double> getDifferenceFromCanadaGuide(LocalDate date) {
//        DataAnalyzer analyzer = new DataAnalyzer(mealLogger);
//        return analyzer.compareWithCanadaGuide(date);
//    }
//
//    /**
//     * Suggests food swaps from a list of candidates based on a specific nutrient goal.
//     * This is useful for testing or targeted recommendations.
//     */
//    public List<FoodItem> suggestFoodSwaps(String nutrientName, List<FoodItem> candidates) {
//        NutritionGoal goal = userProfile.getNutritionGoal(nutrientName);
//        FoodItem original = candidates.get(0);
//        return swapEngine.suggestAlternatives(original, goal, candidates);
//    }
//
//    private FoodItem findFoodItemById(String id, List<FoodItem> list) {
//        for (FoodItem item : list) {
//            if (item.getId().equals(id)) {
//                return item;
//            }
//        }
//        return null;
//    }
//
//    public void createProfile(String username, String sex, int height, double weight,
//                              LocalDate dob, String unit) {
//        UserProfile p = new UserProfile(username, sex, height, weight, dob, unit);
//        profileManager.addProfile(p);
//    }
//
//    public boolean updateUserProfile(UserProfile profile) {
//        if (profile == null) return false;
//        return profileManager.updateProfile(profile);
//    }
//
//    public boolean deleteUserProfile(String username) {
//        if (username == null || username.isEmpty()) return false;
//        return profileManager.removeProfile(username);
//    }
//
//    public UserProfile getUserProfile(String username) {
//        if (username == null || username.isEmpty()) return null;
//        return profileManager.getProfile(username);
//    }
//
//    public List<UserProfile> getAllUserProfiles() {
//        return new ArrayList<>(profileManager.getAllProfiles().values());
//    }
//
//}
//
//
