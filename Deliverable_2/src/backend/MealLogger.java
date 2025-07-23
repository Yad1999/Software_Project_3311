package backend;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealLogger {
    private Map<LocalDate, List<Meal>> mealHistory;

    public MealLogger() {
        mealHistory = new HashMap<>();
    }

    public void addMeal(LocalDate date, Meal meal) {
        mealHistory.putIfAbsent(date, new ArrayList<>());
        mealHistory.get(date).add(meal);
    }


    public Meal getMeal(LocalDate date, String mealType) {
        List<Meal> meals = mealHistory.getOrDefault(date, new ArrayList<>());
        for (Meal meal : meals) {
            if (meal.getMealType().equalsIgnoreCase(mealType)) {
                return meal;
            }
        }
        return null;
    }

    public void logMeal(LocalDate date, Meal meal) {
        addMeal(date, meal);
    }

    public Map<LocalDate, List<Meal>> getAllMeals() {
        return mealHistory;
    }

}

