package com.nutrisci.ui;

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

/**
 * The {@code CreateAccountUI} class provides a graphical user interface for creating
 * a new user account in the NutriSci application.
 * 
 * <p>This interface allows users to enter a new username, set and confirm a password, 
 * and create an account if the credentials are valid and the username is not already taken.
 * On successful creation, the user is redirected back to the login screen.</p>
 * 
 * <p>This UI uses a mock {@link MockUserDataBase} for temporary in-memory user management
 * and is intended for demonstration and testing purposes.</p>
 */
public class CreateAccountUI {

    /** Main application window for account creation. */
    private JFrame frame;

    /** Container for form and message components. */
    private JPanel centerPanel;

    /** Panel holding all input fields and buttons. */
    private JPanel formPanel;

    /** Panel displaying status or error messages. */
    private JPanel messagePanel;

    /** Input field for the new username. */
    private JTextField userIDField;

    /** Input field for the password. */
    private JPasswordField passIDField;

    /** Input field for confirming the password. */
    private JPasswordField confirmPassIDField;

    /** Button to submit account creation request. */
    private JButton createButton;

    /** Button to go back to the login screen. */
    private JButton backButton;

    /** Label for displaying error messages. */
    private JLabel messageLabel;

    /** Temporary mock database storing user credentials. */
    private MockUserDataBase profileDataBase = new MockUserDataBase();

    /** Login UI to return to after account creation or cancel. */
    private LoginUI loginUI = new LoginUI(profileDataBase.getProfileDB());

    /**
     * Constructs the Create Account UI and initializes all components.
     */
    public CreateAccountUI() {
        initialize();
    }

    /**
     * Initializes the UI components and layout, including form fields and button actions.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Create Account");
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

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

        gbc.gridy = 1;
        centerPanel.add(messagePanel, gbc);

        frame.add(centerPanel, BorderLayout.CENTER);

        createButton.addActionListener(e -> createAccount());
        backButton.addActionListener(e -> {
            frame.dispose();
            loginUI.show();
        });
    }

    /**
     * Validates user input and attempts to create a new account.
     * 
     * <p>If any fields are empty, passwords do not match, or the username already exists,
     * appropriate messages are displayed. On successful account creation, the user is
     * returned to the login screen.</p>
     */
    private void createAccount() {
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

    /**
     * Makes the Create Account UI visible.
     */
    public void show() {
        frame.setVisible(true);
    }
}
