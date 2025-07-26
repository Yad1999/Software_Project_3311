package loginprofile.test;

import java.util.HashMap;

public class MockUserDataBase {

	HashMap<String, String> mockProfileDB;
	
	MockUserDataBase() {
		mockProfileDB = new HashMap<>();
		mockProfileDB.put("User1", "userpassword1");
		mockProfileDB.put("User2", "userpassword2");
		mockProfileDB.put("User3", "userpassword3");
	}
	
	public HashMap getProfileDB() {
		return mockProfileDB;
	}
	
	public boolean userExists(String username) {
		return mockProfileDB.containsKey(username);
	}
	
	public void addUser(String username, String password) {
		mockProfileDB.put(username, password);
	}
}
