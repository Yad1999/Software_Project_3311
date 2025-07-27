package backend;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FoodItem {
    private String name;
    private Map<String, Double> nutrients;
    private double calories;
    private double protein;
    private double fat;
    private double carbs;
    private String group;
    private String id;

    public FoodItem(String id, String name, double calories, double protein, double fat, double carbs) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.nutrients = new HashMap<>();
        this.nutrients.put("protein".toLowerCase(), protein);
        this.nutrients.put("fat".toLowerCase(), fat);
        this.nutrients.put("carbs".toLowerCase(), carbs);

    }

    public String getId() {
        return id;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void addNutrient(String nutrientName, double value) {
        nutrients.put(nutrientName.toLowerCase(), value);
    }

    public double getNutrient(String nutrientName) {
        return nutrients.getOrDefault(nutrientName.toLowerCase(), 0.0);
    }

    public Map<String, Double> getAllNutrients() {
        return new HashMap<>(nutrients);
    }
    public Set<String> getNutrientNames() {
        return nutrients.keySet();
    }
    public String getName() {
        return name;
    }
    public double getProtein() {
        return protein;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FoodItem)) return false;
        FoodItem other = (FoodItem) obj;

        if (this.id != null && other.id != null) {
            return this.id.equalsIgnoreCase(other.id);
        }
        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return (id != null ? id.toLowerCase().hashCode() : name.toLowerCase().hashCode());
    }

    @Override
    public String toString() {
        return "FoodItem{name='" + name + "', nutrients=" + nutrients + '}';
    }
}

