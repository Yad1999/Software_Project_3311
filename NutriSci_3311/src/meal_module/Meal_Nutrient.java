package meal_module;

/**
 * This class do ingredient swap suggestions
 * 
 * @author Aliiiirezaaa
 * @version 1.0.0
 * @since 1.0.0
 */
public class Meal_Nutrient {
	private Long nutrient_id;
	private String nutrient;
	/**
	 * @param nutrient
	 */
	public Meal_Nutrient(String nutrient) {
		//todo: generate id
		this.nutrient = nutrient;
	}
	/**
	 * @return the nutrient_id
	 */
	public Long getNutrient_id() {
		return nutrient_id;
	}
	/**
	 * @param nutrient_id the nutrient_id to set
	 */
	public void setNutrient_id(Long nutrient_id) {
		this.nutrient_id = nutrient_id;
	}
	/**
	 * @return the nutrient
	 */
	public String getNutrient() {
		return nutrient;
	}
	/**
	 * @param nutrient the nutrient to set
	 */
	public void setNutrient(String nutrient) {
		this.nutrient = nutrient;
	}
	
}
