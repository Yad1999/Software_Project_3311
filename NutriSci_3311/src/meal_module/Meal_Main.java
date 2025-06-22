package meal_module;
import profile_module.*;
/**
 * For console testing the meal_module
 * 
 * @author Aliiiirezaaa
 * @version 1.0.0
 * @since 1.0.0
 */
public class Meal_Main {

	/**
	 * For console testing the meal_module
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Meal_Ingredient mi = new Meal_Ingredient(Long.valueOf(1), "egg");
		Meal m = new Meal(Long.valueOf(1), new Meal_Type(Long.valueOf(1), "Breakfast"), "Fried Egg");
		Meal_Has mh = new Meal_Has(Long.valueOf(1), m, mi, 100);
		System.out.println(m.getMeal());
		
	}

}
