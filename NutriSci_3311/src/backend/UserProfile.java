package backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfile{
    private String username;
    private String password;
    private String dob;
    private double height;
    private double weight;
    private NutritionGoal goal;
    private String sex;
    private boolean isMetric;
    private List<NutritionGoal> goals = new ArrayList<>();
    private final Map<String, NutritionGoal> nutritionGoals = new HashMap<>();


    public UserProfile(String username, String password, String dob, double height, String sex, boolean isMetric) {
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.height = height;
        this.sex = sex;
        this.isMetric = isMetric;
    }

    public UserProfile(String username, String sex, int height, double weight, LocalDate dob, String unit) {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDob() {
        return dob;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public NutritionGoal getNutritionGoal(String nutrientName) {
        return nutritionGoals.get(nutrientName);
    }

    public void setNutritionGoal(NutritionGoal goal) {
        this.nutritionGoals.put(goal.getNutrientName(), goal);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isMetric() {
        return isMetric;
    }

    public void setMetric(boolean isMetric) {
        this.isMetric = isMetric;
    }

    public void setGoals(List<NutritionGoal> goals) {
        this.goals = goals;
    }

    public List<NutritionGoal> getNutritionGoals() {
        return goals;
    }


}

