package loginprofile.test;

public class MealEntry {

	private String ingredient;
	private String quantity;
	
	public MealEntry(String ingredient, String quantity) {
		this.ingredient = ingredient;
		this.quantity = quantity;
	}
	
	public String getIngredient() {
		return ingredient;
	}
	
	public String getQuantity() {
		return quantity;
	}
	
	@Override
	public String toString() {
		return ingredient + " : " + quantity;
	}
}
