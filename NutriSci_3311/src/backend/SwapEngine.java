package backend;
import java.util.List;

public interface SwapEngine {
    List<FoodItem> suggestAlternatives(FoodItem original, NutritionGoal goal, List<FoodItem> candidates);
}





