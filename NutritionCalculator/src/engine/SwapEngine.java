package engine;

import data.DataLoader;
import model.FoodItem;
import model.NutritionGoal;

public class SwapEngine {
    private DataLoader loader;

    public SwapEngine(DataLoader loader) {
        this.loader = loader;
    }

    public FoodItem suggestSwap(FoodItem original, NutritionGoal goal) {
        double originalValue = original.getNutrient(goal.getNutrientName());
        double bestDelta = Double.MAX_VALUE;
        FoodItem bestItem = null;

        for (FoodItem candidate : loader.getAllFoodItems()) {
            if (candidate.getName().equalsIgnoreCase(original.getName())) continue;

            double candidateValue = candidate.getNutrient(goal.getNutrientName());
            boolean condition = goal.isIncrease() ? candidateValue > originalValue : candidateValue < originalValue;
            double delta = Math.abs(goal.getTargetValue() - candidateValue);

            if (condition && delta < bestDelta) {
                bestDelta = delta;
                bestItem = candidate;
            }
        }

        return bestItem;
    }
}

