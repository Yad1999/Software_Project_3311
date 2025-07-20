package loginprofile.test;

import java.awt.EventQueue;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				MockUserDataBase profileDatabase = new MockUserDataBase();
				
				LoginUI loginUI = new LoginUI(profileDatabase.getProfileDB());
				loginUI.show();
			}
			
		});
	}

}
