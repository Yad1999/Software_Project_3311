package data;

import model.FoodItem;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class DataLoader {
    private Map<String, FoodItem> foodDB = new HashMap<>();

    public void loadCSV(String path) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens.length < 7) continue;
            String name = tokens[0].trim();
            double quantity = Double.parseDouble(tokens[1].trim());
            double calories = Double.parseDouble(tokens[2].trim());
            double protein = Double.parseDouble(tokens[3].trim());
            double fat = Double.parseDouble(tokens[4].trim());
            double fiber = Double.parseDouble(tokens[5].trim());
            double carbs = Double.parseDouble(tokens[6].trim());
            FoodItem item = new FoodItem(name, quantity, calories, protein, fat, fiber, carbs);
            foodDB.put(name.toLowerCase(), item);
        }
        reader.close();
    }

    public FoodItem getFoodItem(String name) {
        return foodDB.get(name.toLowerCase());
    }

    public Collection<FoodItem> getAllFoodItems() {
        return foodDB.values();
    }
}

