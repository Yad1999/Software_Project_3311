package meal_module;
import java.util.ArrayList;

import profile_module.*;
/**
 * This class representing the Meal entity in the database
 * 
 * @author Aliiiirezaaa
 * @version 1.0.0
 * @since 1.0.0
 */
public class Meal {
	private Long meal_id;
	private Meal_Type meal_type;
	private String meal;
	/**
	 * @param meal_type
	 * @param meal
	 */
	public Meal(Meal_Type meal_type, String meal) {
		//todo: generate id
		this.meal_type = meal_type;
		this.meal = meal;
	}
	/**
	 * @param meal_ingredient
	 * @param weight
	 */
	public void addIngredient(Meal_Ingredient meal_ingredient, int weight) {
		Meal_Has meal_has = new Meal_Has(this, meal_ingredient, weight);
	}
	/**
	 * @return the meal_id
	 */
	public Long getMeal_id() {
		return meal_id;
	}
	/**
	 * @param meal_id the meal_id to set
	 */
	public void setMeal_id(Long meal_id) {
		this.meal_id = meal_id;
	}
	/**
	 * @return the meal_type
	 */
	public Meal_Type getMeal_type() {
		return meal_type;
	}
	/**
	 * @param meal_type the meal_type to set
	 */
	public void setMeal_type(Meal_Type meal_type) {
		this.meal_type = meal_type;
	}
	/**
	 * @return the meal
	 */
	public String getMeal() {
		return meal;
	}
	/**
	 * @param meal the meal to set
	 */
	public void setMeal(String meal) {
		this.meal = meal;
	}
	public ArrayList<Meal_Has_Nutrient> getNutrients() {
		ArrayList<Meal_Has_Nutrient> list = new ArrayList<>();
		//add nutrients based on a database query
		return list;
	}
}
