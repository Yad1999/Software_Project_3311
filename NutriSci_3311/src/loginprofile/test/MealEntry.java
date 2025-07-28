package loginprofile.test;

import java.util.List;

public class MealEntry {

    private String date;
    private String mealType;
    private List<IngredientEntry> ingredients;

    private double totalCalories;
    private double totalProtein;
    private double totalCarbs;
    private double totalFat;

    public MealEntry(String date, String mealType, List<IngredientEntry> ingredients,
                     double totalCalories, double totalProtein, double totalCarbs, double totalFat) {
        this.date = date;
        this.mealType = mealType;
        this.ingredients = ingredients;
        this.totalCalories = totalCalories;
        this.totalProtein = totalProtein;
        this.totalCarbs = totalCarbs;
        this.totalFat = totalFat;
    }

    public String getDate() {
        return date;
    }

    public String getMealType() {
        return mealType;
    }

    public List<IngredientEntry> getIngredients() {
        return ingredients;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    public double getTotalProtein() {
        return totalProtein;
    }

    public double getTotalCarbs() {
        return totalCarbs;
    }

    public double getTotalFat() {
        return totalFat;
    }

    @Override
    public String toString() {
        return String.format("%s - %s: %.2f kcal", date, mealType, totalCalories);
    }
}
