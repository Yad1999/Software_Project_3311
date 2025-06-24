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
		Meal_Ingredient mi = new Meal_Ingredient("egg");
		Meal m = new Meal(new Meal_Type("Breakfast"), "Fried Egg");
		Meal_Has mh = new Meal_Has(m, mi, 100);
		System.out.println(m.getMeal());
		
	}

}
