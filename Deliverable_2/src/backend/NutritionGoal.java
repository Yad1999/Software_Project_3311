package backend;

public class NutritionGoal {
    private String nutrientName;
    private double minAmount;
    private double maxAmount;

    public NutritionGoal(String nutrientName, double minAmount, double maxAmount) {
        this.nutrientName = nutrientName;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public String getType() {
        boolean hasMin = minAmount > 0.0;
        boolean hasMax = maxAmount < Double.MAX_VALUE;
        if (hasMin && hasMax) return "RANGE";
        if (hasMin) return "MIN";
        if (hasMax) return "MAX";
        return "NONE";
    }

    public double getMinAmount() {
        return minAmount;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public boolean isSatisfiedBy(FoodItem item) {
        double val = item.getNutrient(nutrientName);
        return val >= minAmount && val <= maxAmount;
    }

    public boolean isSatisfiedBy(NutritionSummary summary) {
        double val = summary.get(nutrientName);
        return val >= minAmount && val <= maxAmount;
    }

    public double getTargetAmount() {
        if (getType().equals("MIN")) {
            return getMinAmount();
        } else {
            return getMaxAmount();
        }
    }

    public boolean isBetterChoice(FoodItem candidate, FoodItem original) {
        double candidateVal = candidate.getNutrient(nutrientName);
        double originalVal = original.getNutrient(nutrientName);

        String type = getType();

        if (type.equals("MIN")) {
            return candidateVal < originalVal;
        }
        else if (type.equals("MAX")) {
            return candidateVal > originalVal;
        }
        else if (type.equals("RANGE")) {
            double target = (minAmount + maxAmount) / 2.0;
            return Math.abs(candidateVal - target) < Math.abs(originalVal - target);
        }
        return false;
    }

}






