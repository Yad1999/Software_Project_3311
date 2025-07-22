package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.*;

/**
 * The DataAnalyzer class is responsible for analyzing nutritional intake
 * across multiple meals and summarizing whether the user met their nutritional goals.
 */
public class DataAnalyzer {

    private MealLogger mealLogger;

    public DataAnalyzer(MealLogger mealLogger) {
        this.mealLogger = mealLogger;
    }

    public NutritionSummary analyzeRange(LocalDate start, LocalDate end) {
        NutritionSummary total = new NutritionSummary();
        int count = 0;

        for (Map.Entry<LocalDate, List<Meal>> entry : mealLogger.getAllMeals().entrySet()) {
            LocalDate date = entry.getKey();
            if ((date.isEqual(start) || date.isAfter(start)) &&
                    (date.isEqual(end) || date.isBefore(end))) {

                for (Meal meal : entry.getValue()) {
                    total.add(meal.calculateTotalNutrition());
                    count++;
                }
            }
        }

        if (count > 0) {
            total.divide(count);
        }

        return total;
    }

    public Map<String, Double> compareWithCanadaGuide(LocalDate date) {
        List<Meal> meals = mealLogger.getAllMeals().getOrDefault(date, new ArrayList<>());
        Map<String, Double> actualIntake = new HashMap<>();

        for (Meal meal : meals) {
            for (FoodItem item : meal.getFoodItems()) {
                for (Map.Entry<String, Double> entry : item.getAllNutrients().entrySet()) {
                    actualIntake.merge(entry.getKey(), entry.getValue(), Double::sum);
                }
            }
        }

        Map<String, Double> differences = new HashMap<>();
        for (String nutrient : CanadaFoodGuide.STANDARD_VALUES.keySet()) {
            double standard = CanadaFoodGuide.STANDARD_VALUES.get(nutrient);
            double actual = actualIntake.getOrDefault(nutrient, 0.0);
            double diff = standard - actual;
            if (Math.abs(diff) > 1e-6) {
                differences.put(nutrient, diff);
            }
        }

        return differences;
    }

    public class CanadaFoodGuide {
        public static final Map<String, Double> STANDARD_VALUES = new HashMap<>();

        static {
            STANDARD_VALUES.put("protein", 50.0);
            STANDARD_VALUES.put("fiber", 30.0);
            STANDARD_VALUES.put("vitaminC", 75.0);
        }
    }







}


