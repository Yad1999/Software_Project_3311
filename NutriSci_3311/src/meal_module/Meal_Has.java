package meal_module;

/**
 * This class representing the Meal_Has entity in the database
 * 
 * @author Aliiiirezaaa
 * @version 1.0.0
 * @since 1.0.0
 */
public class Meal_Has {
	private Long meal_has_id;
	private Meal meal;
	private Meal_Ingredient meal_ingredient;
	private int weight;
	/**
	 * @param meal
	 * @param meal_ingredient
	 * @param weight
	 */
	public Meal_Has(Meal meal, Meal_Ingredient meal_ingredient, int weight) {
		//todo: generate id
		this.meal = meal;
		this.meal_ingredient = meal_ingredient;
		this.weight = weight;
		//update Meal_Has_Nutrient amounts in the database
	}
	/**
	 * @return the meal_has_id
	 */
	public Long getMeal_has_id() {
		return meal_has_id;
	}
	/**
	 * @param meal_has_id the meal_has_id to set
	 */
	public void setMeal_has_id(Long meal_has_id) {
		this.meal_has_id = meal_has_id;
	}
	/**
	 * @return the meal
	 */
	public Meal getMeal() {
		return meal;
	}
	/**
	 * @param meal the meal to set
	 */
	public void setMeal(Meal meal) {
		this.meal = meal;
	}
	/**
	 * @return the meal_ingredient
	 */
	public Meal_Ingredient getMeal_ingredient() {
		return meal_ingredient;
	}
	/**
	 * @param meal_ingredient the meal_ingredient to set
	 */
	public void setMeal_ingredient(Meal_Ingredient meal_ingredient) {
		this.meal_ingredient = meal_ingredient;
	}
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
