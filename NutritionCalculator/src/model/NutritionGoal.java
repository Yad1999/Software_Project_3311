package model;

public class NutritionGoal {
    private String nutrientName;
    private double targetValue;
    private boolean increase;

    public NutritionGoal(String nutrientName, double targetValue, boolean increase) {
        this.nutrientName = nutrientName;
        this.targetValue = targetValue;
        this.increase = increase;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public double getTargetValue() {
        return targetValue;
    }

    public boolean isIncrease() {
        return increase;
    }
}

