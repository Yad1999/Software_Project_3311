package backend;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Meal {
    private String mealType;
    private LocalDate date;
    private List<FoodItem> foodItems;

    public Meal(String mealType, LocalDate date) {
        this.mealType = mealType;
        this.date = date;
        this.foodItems = new ArrayList<>();
    }

    public void addFoodItem(FoodItem item) {
        foodItems.add(item);
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public String getMealType() {
        return mealType;
    }

    public LocalDate getDate() {
        return date;
    }

    public NutritionSummary calculateTotalNutrition() {
        NutritionSummary summary = new NutritionSummary();

        for (FoodItem item : foodItems) {
            for (String nutrient : item.getNutrientNames()) {
                double amount = item.getNutrient(nutrient);
                summary.add(nutrient, amount);
            }
        }

        return summary;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealType='" + mealType + '\'' +
                ", date=" + date +
                ", foodItems=" + foodItems +
                '}';
    }
}
