package com.nutrisci.ui;

import java.awt.*;
import javax.swing.*;

/**
 * The {@code ProfileUI} class provides a user interface for viewing and updating
 * a user's profile in the NutriSci application.
 *
 * <p>The profile includes the user's sex, date of birth, height, weight, and preferred
 * measurement units. This panel is typically embedded in the main dashboard.</p>
 *
 * <p>Currently, profile data is not persisted and is intended for UI testing and integration.</p>
 */
public class ProfileUI {

    /** The main panel for this UI, to be embedded in a container like MainDashboardUI. */
    private JPanel profileUIPanel;

    /** Panel that holds all input components in a grid layout. */
    private JPanel centerPanel;

    /** Panel for form layout (not used explicitly, but may be extended in future). */
    private JPanel formPanel;

    /** Panel for displaying status or messages (not explicitly used here). */
    private JPanel messagePanel;

    /** Label to display save confirmation or status messages. */
    private JLabel statusLabel;

    /** Field for entering date of birth. */
    private JTextField dobField;

    /** Field for entering height (cm or in). */
    private JTextField heightField;

    /** Field for entering weight (kg or lb). */
    private JTextField weightField;

    /** Dropdown for selecting sex (Male, Female, Other). */
    private JComboBox<String> sexComboBox;

    /** Dropdown for selecting unit system (Metric or Imperial). */
    private JComboBox<String> unitComboBox;

    /** Button to save the entered profile data. */
    private JButton saveButton;

    /**
     * Constructs the Profile UI and initializes its components.
     */
    public ProfileUI() {
        initialize();
    }

    /**
     * Initializes all UI components, layouts, and event handling.
     */
    private void initialize() {
        profileUIPanel = new JPanel(new BorderLayout());
        profileUIPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Sex
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Sex:"), gbc);

        gbc.gridx = 1;
        sexComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        centerPanel.add(sexComboBox, gbc);

        // Date of Birth
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        dobField = new JTextField();
        centerPanel.add(dobField, gbc);

        // Height
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(new JLabel("Height (cm/in):"), gbc);

        gbc.gridx = 1;
        heightField = new JTextField();
        centerPanel.add(heightField, gbc);

        // Weight
        gbc.gridx = 0;
        gbc.gridy = 3;
        centerPanel.add(new JLabel("Weight (kg/lb):"), gbc);

        gbc.gridx = 1;
        weightField = new JTextField();
        centerPanel.add(weightField, gbc);

        // Units
        gbc.gridx = 0;
        gbc.gridy = 4;
        centerPanel.add(new JLabel("Units:"), gbc);

        gbc.gridx = 1;
        unitComboBox = new JComboBox<>(new String[]{"Metric", "Imperial"});
        centerPanel.add(unitComboBox, gbc);

        // Save Button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        saveButton = new JButton("Save Profile");
        centerPanel.add(saveButton, gbc);

        // Status Label
        gbc.gridy = 6;
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font(null, Font.ITALIC, 14));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(statusLabel, gbc);

        // Add center panel to main panel
        profileUIPanel.add(centerPanel, BorderLayout.CENTER);

        // Save button action: confirms the update visually
        saveButton.addActionListener(e -> {
            statusLabel.setText("\u2705 Profile updated!");
        });
    }

    /**
     * Returns the main panel of the profile UI.
     *
     * @return the {@link JPanel} representing this user interface.
     */
    public JPanel getPanel() {
        return profileUIPanel;
    }
}
