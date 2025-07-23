package backend;
import java.util.List;

public class NutritionCalculator {

    public static NutritionSummary calculateMealNutrition(Meal meal) {
        if (meal == null) {
            return new NutritionSummary();
        }
        return meal.calculateTotalNutrition();
    }

}
