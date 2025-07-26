package loginprofile.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MockUserDataBase {

	private HashMap<String, String> mockProfileDB;
	private HashMap<String, List<String>> userMealLogs = new HashMap<>();
	
	MockUserDataBase() {
		mockProfileDB = new HashMap<>();
		mockProfileDB.put("User1", "userpassword1");
		mockProfileDB.put("User2", "userpassword2");
		mockProfileDB.put("User3", "userpassword3");
	}
	
	public HashMap<String, String> getProfileDB() {
		return mockProfileDB;
	}
	
	public boolean userExists(String username) {
		return mockProfileDB.containsKey(username);
	}
	
	public void addUser(String username, String password) {
		mockProfileDB.put(username, password);
	}
	
	// temporary/not used methods
	public void addMeal(String username, String date, String mealType, List<MealEntry> entries) {
		StringBuilder mealRecord = new StringBuilder();
		mealRecord.append("Date: ").append(date).append(", Meal: ").append(mealType).append("\n");
		
		for (MealEntry e : entries) {
			mealRecord.append("  - ").append(e.toString()).append("\n");
		}
		
		userMealLogs.putIfAbsent(username, new ArrayList<>());
		userMealLogs.get(username).add(mealRecord.toString());

        System.out.println("Meal saved to mock database for user '" + username + "'.");
	}
	
	public List<String> getMeals(String username) {
        return userMealLogs.getOrDefault(username, new ArrayList<>());
	}
}
