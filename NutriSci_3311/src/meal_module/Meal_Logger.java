package meal_module;
import profile_module.*;

/**
 * This class do meal logging
 * 
 * @author Aliiiirezaaa
 * @version 1.0.0
 * @since 1.0.0
 */
public class Meal_Logger {
	private static Meal_Logger instance;

	/**
	 * 
	 */
	public Meal_Logger() {
	}
	/**
	 * @return instance
	 */
	public static Meal_Logger getInstance() {
		if(instance == null) {
			instance = new Meal_Logger();
		}
		return instance;
	}
	private boolean logMeal(Profile profile, Meal meal) {
		return true;
	}
}
