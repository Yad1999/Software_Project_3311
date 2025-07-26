package loginprofile.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateAccountUI {

	private JFrame frame;
	private JPanel centerPanel;
	private JPanel formPanel;
	private JPanel messagePanel;
	private JTextField userIDField;
	private JPasswordField passIDField;
	private JPasswordField confirmPassIDField;
	private JButton createButton;
	private JButton backButton;
	private JLabel messageLabel;
	
	private MockUserDataBase profileDataBase = new MockUserDataBase();
	private LoginUI loginUI = new LoginUI(profileDataBase.getProfileDB());
	
	public CreateAccountUI() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Create Account");
		frame.setSize(350, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		
		centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);;

		formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
		
		formPanel.add(new JLabel("Username: "));
		userIDField = new JTextField();
		formPanel.add(userIDField);
		
		formPanel.add(new JLabel("Password: "));
		passIDField = new JPasswordField();
		formPanel.add(passIDField);
		
		formPanel.add(new JLabel("Confirm Password: "));
		confirmPassIDField = new JPasswordField();
		formPanel.add(confirmPassIDField);
		
		createButton = new JButton("Create Account");
		formPanel.add(createButton);
		
		backButton = new JButton("Back to Login");
		formPanel.add(backButton);
				
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
		
		createButton.addActionListener(e -> createAccount());
		backButton.addActionListener(e -> {
			frame.dispose();
			loginUI.show();
		});
	}
	
	private void createAccount() {
		// TODO Auto-generated method stub
		String userID = userIDField.getText();
		String passID = String.valueOf(passIDField.getPassword());
		String confirmPassID = String.valueOf(confirmPassIDField.getPassword());
		
		if (userID.isEmpty() || passID.isEmpty() || confirmPassID.isEmpty()) {
			messageLabel.setForeground(Color.RED);
			messageLabel.setText("All fields are required.");
			return;
		}
		
		if (!passID.equals(confirmPassID)) {
			messageLabel.setForeground(Color.RED);
			messageLabel.setText("Passwords do not match.");
			return;
		}
		
		if (profileDataBase.userExists(userID)) {
			messageLabel.setForeground(Color.RED);
			messageLabel.setText("Username already exists.");
			return;
		}
		
		profileDataBase.addUser(userID, passID);
		JOptionPane.showMessageDialog(frame, "Account created successfully!");
		frame.dispose();
		loginUI.show();
	}

	public void show() {
		frame.setVisible(true);
	}
}
