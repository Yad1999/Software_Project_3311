package meal_module;

/**
 * This class representing the Meal_Type entity in the database
 * 
 * @author Aliiiirezaaa
 * @version 1.0.0
 * @since 1.0.0
 */
public class Meal_Type {
	private Long meal_type_id;
	private String type;
	/**
	 * @param meal_type_id
	 * @param type
	 */
	public Meal_Type(Long meal_type_id, String type) {
		this.meal_type_id = meal_type_id;
		this.type = type;
	}
	/**
	 * @return the meal_type_id
	 */
	public Long getMeal_type_id() {
		return meal_type_id;
	}
	/**
	 * @param meal_type_id the meal_type_id to set
	 */
	public void setMeal_type_id(Long meal_type_id) {
		this.meal_type_id = meal_type_id;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}
