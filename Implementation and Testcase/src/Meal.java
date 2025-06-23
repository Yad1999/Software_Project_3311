public class Meal {
    private String id;
    private String name;
    private int calories;

    public Meal(String id, String name, int calories) {
        this.id = id;
        this.name = name;
        this.calories = calories;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }
}
