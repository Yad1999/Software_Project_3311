package com.nutrisci.ui;

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

/**
 * The {@code LoginUI} class provides a graphical user interface for user login
 * and account creation in the NutriSci application.
 * 
 * <p>This UI allows users to enter a username and password to log in,
 * and provides a button to navigate to account creation if needed.
 * Upon successful login, the main dashboard is displayed.</p>
 * 
 * <p>Note: This class uses a mock in-memory {@code HashMap} as a temporary
 * user credential store. In production, this should be replaced with a secure
 * persistent authentication system.</p>
 */
public class LoginUI {

    /** Main application window frame. */
    private JFrame frame;

    /** Central container for form and message panels. */
    private JPanel centerPanel;

    /** Panel for login form input fields and buttons. */
    private JPanel formPanel;

    /** Panel for displaying login error messages. */
    private JPanel messagePanel;

    /** Button for logging into an existing account. */
    private JButton loginButton;

    /** Button for navigating to the account creation screen. */
    private JButton createAccountButton;

    /** Text field for entering the username. */
    private JTextField userIDField;

    /** Password field for entering the password. */
    private JPasswordField passIDField;

    /** Label for displaying login status messages. */
    private JLabel messageLabel;

    /** HashMap containing username-password pairs for login verification. */
    HashMap<String, String> loginInfo = new HashMap<>();

    /**
     * Constructs the Login UI with the given user credential data.
     *
     * @param loginInfoOriginal a map containing mock usernames and passwords.
     */
    public LoginUI(HashMap<String, String> loginInfoOriginal) {
        initialize(loginInfoOriginal);
    }

    /**
     * Initializes all GUI components and layout.
     *
     * @param loginInfoOriginal the initial user login credentials.
     */
    private void initialize(HashMap<String, String> loginInfoOriginal) {
        loginInfo = loginInfoOriginal;

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

        gbc.gridy = 1;
        centerPanel.add(messagePanel, gbc);

        frame.add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Displays the login window.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Verifies the username and password against the mock database.
     * Displays appropriate success or error messages.
     * On successful login, navigates to the main dashboard UI.
     */
    private void loginFunction() {
        String userID = userIDField.getText();
        String passID = String.valueOf(passIDField.getPassword());

        if (loginInfo.containsKey(userID)) {
            if (loginInfo.get(userID).equals(passID)) {
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                frame.dispose();

                MainDashboardUI mainDashBoardUI = new MainDashboardUI();
                mainDashBoardUI.show();
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Wrong Password!");
            }
        } else {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Username not found");
        }
    }

    /**
     * Navigates to the account creation UI.
     */
    private void showCreateAccount() {
        frame.dispose();
        CreateAccountUI createAccountUI = new CreateAccountUI();
        createAccountUI.show();
    }
}
