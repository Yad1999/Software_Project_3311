package ingredient_swap_module;
import meal_module.*;

/**
 * This class do ingredient swap suggestions
 * 
 * @author Aliiiirezaaa
 * @version 1.0.0
 * @since 1.0.0
 */
public class Ingredient_Swap_Suggestion {
	private Meal meal;
	private Meal_Ingredient ingredient1, ingredient2;
	private Integer ingredient_weight1, ingredient_weight2;
	private Meal_Nutrient nutrient1, nutrient2;
	private Integer goal_amount1, goal_amount2; //in percent

	/**
	 * @param meal
	 * @param nutrient1
	 * @param goal_amount1
	 */
	public Ingredient_Swap_Suggestion(Meal meal, Meal_Nutrient nutrient1, Integer goal_amount1) {
		this.meal = meal;
		this.nutrient1 = nutrient1;
		this.goal_amount1 = Math.max(goal_amount1, -100); //if the goal is to decrease the selected nutrient more than 100% setting the amount to -100
		// this.nutrient_amount1 = meal.calculateNutrient(nutrient1);
	}
	/**
	 * @param meal
	 * @param nutrient1
	 * @param nutrient2
	 * @param goal_amount1
	 * @param goal_amount2
	 */
	public Ingredient_Swap_Suggestion(Meal meal, Meal_Nutrient nutrient1, Meal_Nutrient nutrient2, Integer goal_amount1,
			Integer goal_amount2) {
		this.meal = meal;
		this.nutrient1 = nutrient1;
		this.nutrient2 = nutrient2;
		this.goal_amount1 = Math.max(goal_amount1, -100); //if the goal is to decrease the selected nutrient more than 100% setting the amount to -100
		this.goal_amount2 = Math.max(goal_amount2, -100); //if the goal is to decrease the selected nutrient more than 100% setting the amount to -100
	}
	
	public Meal getSuggestion() {
		Meal suggestion = null;
		//query database for the alternative based in Meal_Has_Nutrient data adjusted with the goal
		return suggestion;
	}
	
}
