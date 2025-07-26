package loginprofile.test;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CreateAccountUI {

	private JFrame frame;
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
		frame.setLayout(new GridLayout(5, 2, 10, 10));
		
		
		userIDField = new JTextField();
		passIDField = new JPasswordField();
		confirmPassIDField = new JPasswordField();
		
		createButton = new JButton("Create Account");
		backButton = new JButton("Back to Login");
		messageLabel = new JLabel("", SwingConstants.CENTER);
		
		frame.add(new JLabel("Username: "));
		frame.add(userIDField);
		frame.add(new JLabel("Password: "));
		frame.add(passIDField);
		frame.add(new JLabel("Confirm Password: "));
		frame.add(confirmPassIDField);
		
		frame.add(createButton);
		frame.add(backButton);
		frame.add(messageLabel);
		
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
			messageLabel.setText("All fields are required.");
			return;
		}
		
		if (!passID.equals(confirmPassID)) {
			messageLabel.setText("Passwords do not match.");
			return;
		}
		
		if (profileDataBase.userExists(userID)) {
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
