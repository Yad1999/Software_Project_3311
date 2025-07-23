package loginprofile.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginUI implements ActionListener{

	private JFrame frame;
	private JButton loginButton;
	private JButton resetButton;
	private JTextField userIDField;
	private JPasswordField passIDField;
	private JLabel userIDLabel;
	private JLabel passIDLabel;
	private JLabel messageLabel;
	
	HashMap<String, String> loginInfo = new HashMap<>();
	
	LoginUI(HashMap<String, String> loginInfoOriginal) {
		initialize(loginInfoOriginal);
	}
	
	private void initialize(HashMap<String, String> loginInfoOriginal) {
		// Calling mock Database
		loginInfo = loginInfoOriginal;
		
		// Initialize frame
		frame = new JFrame();
		frame.setTitle("NutriSci");
		frame.setSize(420, 420);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		
		userIDLabel = new JLabel("Username:");
		userIDLabel.setBounds(50, 100, 75, 25);
		userIDField = new JTextField();
		userIDField.setBounds(125, 100, 200, 25);
		
		passIDLabel = new JLabel("Password:");
		passIDLabel.setBounds(50, 150, 75, 25);
		passIDField = new JPasswordField();
		passIDField.setBounds(125, 150, 200, 25);
		
		loginButton = new JButton("Login");
		loginButton.setBounds(125, 200, 100, 25);
		loginButton.setFocusable(false);
		loginButton.addActionListener(this);
		
		resetButton = new JButton("Reset");
		resetButton.setBounds(225, 200, 100, 25);
		resetButton.setFocusable(false);
		resetButton.addActionListener(this);
		
		messageLabel = new JLabel("");
		messageLabel.setBounds(125, 250, 250, 35);
		messageLabel.setFont(new Font(null, Font.ITALIC, 25));
		
		frame.add(userIDLabel);
		frame.add(userIDField);
		frame.add(passIDLabel);
		frame.add(passIDField);
		frame.add(loginButton);
		frame.add(resetButton);
		frame.add(messageLabel);
	}
	
	public void show() {
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == loginButton) {
			String userID = userIDField.getText();
			String passID = String.valueOf(passIDField.getPassword());
			
			if (loginInfo.containsKey(userID)) {
				if (loginInfo.get(userID).equals(passID)) {
					messageLabel.setForeground(Color.GREEN);
					messageLabel.setText("Login Successful");
					
					frame.dispose();
					ProfileUI profileUI = new ProfileUI(userID);
					profileUI.show();
					
				}
				else {
					messageLabel.setForeground(Color.RED);
					messageLabel.setText("Wrong Password!");
				}
			}
			else {
				messageLabel.setForeground(Color.RED);
				messageLabel.setText("Username not found");
			}
		}
		
		if (e.getSource() == resetButton) {
			userIDField.setText("");
			passIDField.setText("");
		}
	}
}
