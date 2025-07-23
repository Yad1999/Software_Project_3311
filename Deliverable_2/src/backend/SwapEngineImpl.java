package backend;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.*;

public class SwapEngineImpl implements SwapEngine {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/nutrisci_db";
    private static final String JDBC_USER = "youruser";
    private static final String JDBC_PASSWORD = "yourpassword";

    public Meal applySwapToMeal(Meal original, List<FoodItem> unused, NutritionGoal goal) {
        Meal swappedMeal = new Meal(original.getMealType(), original.getDate());
        int replacements = 0;

        for (FoodItem item : original.getFoodItems()) {
            if (replacements >= 2) {
                swappedMeal.addFoodItem(item);
                continue;
            }

            if (goal.isSatisfiedBy(item)) {
                swappedMeal.addFoodItem(item);
            } else {
                FoodItem swap = findSwap(item, goal);
                if (swap != null) {
                    swappedMeal.addFoodItem(swap);
                    replacements++;
                } else {
                    swappedMeal.addFoodItem(item);
                }
            }
        }

        return swappedMeal;
    }

    public Meal getSwappedMeal(String mealId) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public List<FoodItem> suggestAlternatives(FoodItem item, NutritionGoal goal) {
        List<FoodItem> candidates = getCandidatesFromDB(item.getGroup());
        List<FoodItem> result = new ArrayList<>();

        for (FoodItem candidate : candidates) {
            if (goal.getType().equals("MIN") && candidate.getNutrient(goal.getNutrientName()) < goal.getTargetAmount()) {
                continue;
            }
            if (goal.getType().equals("MAX") && candidate.getNutrient(goal.getNutrientName()) > goal.getTargetAmount()) {
                continue;
            }

            boolean withinRange = true;
            for (String nutrient : item.getNutrientNames()) {
                if (nutrient.equals(goal.getNutrientName())) continue;
                double originalVal = item.getNutrient(nutrient);
                double candidateVal = candidate.getNutrient(nutrient);
                if (originalVal > 0 && Math.abs((candidateVal - originalVal) / originalVal) > 0.10) {
                    withinRange = false;
                    break;
                }
            }

            if (withinRange) result.add(candidate);
        }

        return result;
    }


    private FoodItem findSwap(FoodItem original, NutritionGoal goal) {
        List<FoodItem> candidates = getCandidatesFromDB(original.getGroup());
        for (FoodItem candidate : candidates) {
            double candidateVal = candidate.getNutrient(goal.getNutrientName());
            if (goal.getType().equals("MIN") && candidateVal < goal.getTargetAmount()) continue;
            if (goal.getType().equals("MAX") && candidateVal > goal.getTargetAmount()) continue;

            boolean withinRange = true;
            for (String nutrient : original.getNutrientNames()) {
                if (nutrient.equals(goal.getNutrientName())) continue;
                double o = original.getNutrient(nutrient);
                double c = candidate.getNutrient(nutrient);
                if (o > 0 && Math.abs((c - o) / o) > 0.10) {
                    withinRange = false;
                    break;
                }
            }

            if (withinRange) return candidate;
        }
        return null;
    }

    private List<FoodItem> getCandidatesFromDB(String group) {
        List<FoodItem> result = new ArrayList<>();
        String sql = "SELECT * FROM food_items WHERE food_group = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, group);
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    FoodItem fi = new FoodItem(
                            rs.getString("name"),
                            rs.getDouble("calories"),
                            rs.getDouble("protein"),
                            rs.getDouble("fat"),
                            rs.getDouble("carbs")
                    );
                    fi.setGroup(group);

                    ResultSetMetaData meta = rs.getMetaData();
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        String col = meta.getColumnName(i).toLowerCase();
                        if (col.equals("name") || col.equals("food_group") ||
                                col.equals("calories") || col.equals("protein") ||
                                col.equals("fat") || col.equals("carbs")) {
                            continue;
                        }
                        double val = rs.getDouble(i);
                        if (!rs.wasNull()) {
                            fi.addNutrient(col, val);
                        }
                    }

                    result.add(fi);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<FoodItem> suggestAlternatives(FoodItem original, NutritionGoal goal, List<FoodItem> candidates) {
        List<FoodItem> suggestions = new ArrayList<>();
        for (FoodItem item : candidates) {
            if (!item.equals(original) && goal.isBetterChoice(item, original)) {
                suggestions.add(item);
            }
        }
        return suggestions;
    }



}
