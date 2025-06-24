package model;

import java.util.*;

public class Meal {
    private String date;
    private String mealType;
    private List<FoodItem> foodItems = new ArrayList<>();

    public Meal(String date, String type) {
        this.date = date;
        this.mealType = type;
    }

    public void addFoodItem(FoodItem item) {
        foodItems.add(item);
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public String getDate() {
        return date;
    }
}

