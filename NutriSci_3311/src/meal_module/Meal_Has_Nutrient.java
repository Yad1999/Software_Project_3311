package meal_module;

/**
 * This class representing the Meal_Has_Nutrient entity in the database
 * 
 * @author Aliiiirezaaa
 * @version 1.0.0
 * @since 1.0.0
 */
public class Meal_Has_Nutrient {
	private Long meal_has_nutrient_id;
	private Meal meal;
	private Meal_Nutrient meal_nutrient;
	private int amount;
	/**
	 * @param meal
	 * @param meal_nutrient
	 * @param amount
	 */
	public Meal_Has_Nutrient(Meal meal, Meal_Nutrient meal_nutrient, int amount) {
		this.meal = meal;
		this.meal_nutrient = meal_nutrient;
		this.amount = amount;
	}
	/**
	 * @return the meal_has_nutrient_id
	 */
	public Long getMeal_has_nutrient_id() {
		return meal_has_nutrient_id;
	}
	/**
	 * @param meal_has_nutrient_id the meal_has_nutrient_id to set
	 */
	public void setMeal_has_nutrient_id(Long meal_has_nutrient_id) {
		this.meal_has_nutrient_id = meal_has_nutrient_id;
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
	 * @return the meal_nutrient
	 */
	public Meal_Nutrient getMeal_nutrient() {
		return meal_nutrient;
	}
	/**
	 * @param meal_nutrient the meal_nutrient to set
	 */
	public void setMeal_nutrient(Meal_Nutrient meal_nutrient) {
		this.meal_nutrient = meal_nutrient;
	}
	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
