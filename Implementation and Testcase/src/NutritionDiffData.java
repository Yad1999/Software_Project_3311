public class NutritionDiffData {
    private int originalCalories;
    private int swappedCalories;

    public NutritionDiffData(int originalCalories, int swappedCalories) {
        this.originalCalories = originalCalories;
        this.swappedCalories = swappedCalories;
    }

    public int getOriginalCalories() {
        return originalCalories;
    }

    public int getSwappedCalories() {
        return swappedCalories;
    }
}

