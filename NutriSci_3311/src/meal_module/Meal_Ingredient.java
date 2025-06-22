package meal_module;

/**
 * This class representing the Meal_Ingredient entity in the database
 * 
 * @author Aliiiirezaaa
 * @version 1.0.0
 * @since 1.0.0
 */
public class Meal_Ingredient {
	private Long meal_ingredient_id;
	private String ingredient;
	/**
	 * @param ingredient
	 */
	public Meal_Ingredient(String ingredient) {
		//todo: generate id
		this.ingredient = ingredient;
	}
	/**
	 * @return the meal_ingredient_id
	 */
	public Long getMeal_ingredient_id() {
		return meal_ingredient_id;
	}
	/**
	 * @param meal_ingredient_id the meal_ingredient_id to set
	 */
	public void setMeal_ingredient_id(Long meal_ingredient_id) {
		this.meal_ingredient_id = meal_ingredient_id;
	}
	/**
	 * @return the ingredient
	 */
	public String getIngredient() {
		return ingredient;
	}
	/**
	 * @param ingredient the ingredient to set
	 */
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
}
