package loginprofile.test;

public class IngredientEntry {

    private String name;
    private double quantityInGrams;

    public IngredientEntry(String name, double quantityInGrams) {
        this.name = name;
        this.quantityInGrams = quantityInGrams;
    }

    public String getName() {
        return name;
    }

    public double getQuantityInGrams() {
        return quantityInGrams;
    }

    @Override
    public String toString() {
        return name + " - " + quantityInGrams + "g";
    }
}
