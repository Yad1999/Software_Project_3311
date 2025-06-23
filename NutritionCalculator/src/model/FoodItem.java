package model;

public class FoodItem {
    private String name;
    private double quantity;
    private double calories;
    private double protein;
    private double fat;
    private double fiber;
    private double carbs;

    public FoodItem(String name, double quantity, double calories, double protein, double fat, double fiber, double carbs) {
        this.name = name;
        this.quantity = quantity;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.fiber = fiber;
        this.carbs = carbs;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getFat() {
        return fat;
    }

    public double getFiber() {
        return fiber;
    }

    public double getCarbs() {
        return carbs;
    }

    public String getName() {
        return name;
    }

    public double getNutrient(String nutrientName) {
        switch (nutrientName.toLowerCase()) {
            case "calories":
                return getCalories();
            case "protein":
                return getProtein();
            case "fat":
                return getFat();
            case "fiber":
                return getFiber();
            case "carbs":
            case "carbohydrates":
                return getCarbs();
            default:
                throw new IllegalArgumentException("Unknown nutrient: " + nutrientName);
        }
    }

}
