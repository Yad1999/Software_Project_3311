package app;

import data.DataLoader;
import engine.SwapEngine;
import model.FoodItem;
import model.NutritionGoal;

public class Main {
    public static void main(String[] args) {
        try {
            DataLoader loader = new DataLoader();
            loader.loadCSV("cnf.csv");

            FoodItem apple = loader.getFoodItem("apple");
            System.out.println("Original: " + apple.getName() + " - Fiber: " + apple.getFiber());

            NutritionGoal goal = new NutritionGoal("fiber", 5.0, true);
            SwapEngine swapEngine = new SwapEngine(loader);

            FoodItem suggestion = swapEngine.suggestSwap(apple, goal);
            if (suggestion != null) {
                System.out.println("Suggested swap: " + suggestion.getName() + " - Fiber: " + suggestion.getFiber());
            } else {
                System.out.println("No suitable swap found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

