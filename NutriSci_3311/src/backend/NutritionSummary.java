package backend;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NutritionSummary {
    private Map<String, Double> nutrients;

    public NutritionSummary() {
        nutrients = new HashMap<>();
    }

    public void add(String name, double amount) {
        nutrients.put(name, nutrients.getOrDefault(name, 0.0) + amount);
    }

    public void add(NutritionSummary other) {
        for (Map.Entry<String, Double> entry : other.nutrients.entrySet()) {
            this.add(entry.getKey(), entry.getValue());
        }
    }

    public void divide(int divisor) {
        for (String key : nutrients.keySet()) {
            nutrients.put(key, nutrients.get(key) / divisor);
        }
    }

    public double get(String name) {
        return nutrients.getOrDefault(name, 0.0);
    }

    public Map<String, Double> getAll() {
        return nutrients;
    }

    @Override
    public String toString() {
        return nutrients.toString();
    }
}


