package loginprofile.test;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ProfileUI {

	private JFrame frame;
	private JLabel profileLabel;
	
	ProfileUI(String userID) {
		initialize(userID);
	}

	private void initialize(String userID) {
		// TODO Auto-generated method stub
		frame = new JFrame();
		frame.setTitle("NutriSci");
		frame.setSize(420, 420);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		
		profileLabel = new JLabel("Welcome " + userID + "!");
		profileLabel.setFont(new Font(null, Font.PLAIN, 25));
		profileLabel.setBounds(110, 110, 200, 35);
		
		frame.add(profileLabel);
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	
}
