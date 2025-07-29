package loginprofile.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginUI {

	private JFrame frame;
	private JPanel centerPanel;
	private JPanel formPanel;
	private JPanel messagePanel;
	private JButton loginButton;
	private JButton createAccountButton;
	private JTextField userIDField;
	private JPasswordField passIDField;
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
		frame.setSize(420, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		
		centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		
		formPanel = new JPanel(new GridLayout(3, 2, 10, 10));

		userIDField = new JTextField();
		passIDField = new JPasswordField();
		
		loginButton = new JButton("Login");
		loginButton.setFocusable(false);
		loginButton.addActionListener(e -> loginFunction());		
		createAccountButton = new JButton("Create Account");
		createAccountButton.setFocusable(false);
		createAccountButton.addActionListener(e -> showCreateAccount());
		
		formPanel.add(new JLabel("Username"));
		formPanel.add(userIDField);
		formPanel.add(new JLabel("Password"));
		formPanel.add(passIDField);
		formPanel.add(loginButton);
		formPanel.add(createAccountButton);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(formPanel, gbc);
		
		messagePanel = new JPanel();
		messageLabel = new JLabel("");
		messageLabel.setFont(new Font(null, Font.ITALIC, 16));
		messageLabel.setHorizontalAlignment(JLabel.CENTER);
		messagePanel.add(messageLabel);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		centerPanel.add(messagePanel, gbc);
		frame.add(centerPanel, BorderLayout.CENTER);
	}
	
	public void show() {
		frame.setVisible(true);
	}

	
	private void loginFunction() {
		String userID = userIDField.getText();
		String passID = String.valueOf(passIDField.getPassword());
		
		if (loginInfo.containsKey(userID)) {
			if (loginInfo.get(userID).equals(passID)) {
				JOptionPane.showMessageDialog(frame, "Login Succesful!");
				frame.dispose();
				
				MainDashboardUI mainDashBoardUI= new MainDashboardUI();
				mainDashBoardUI.show();
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
	
	private void showCreateAccount() {
		frame.dispose();
		CreateAccountUI createAccountUI = new CreateAccountUI();
		createAccountUI.show();
	}
}