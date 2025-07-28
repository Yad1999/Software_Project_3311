package loginprofile.test;

import java.awt.*;
import javax.swing.*;

public class ProfileUI {

    private JPanel profileUIPanel;
    private JPanel centerPanel;
    private JPanel formPanel;
    private JPanel messagePanel;

    private JLabel statusLabel;
    private JTextField dobField;
    private JTextField heightField;
    private JTextField weightField;
    private JComboBox<String> sexComboBox;
    private JComboBox<String> unitComboBox;
    private JButton saveButton;

    public ProfileUI() {
        initialize();
    }

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

        // Save button action
        saveButton.addActionListener(e -> {
            statusLabel.setText("\u2705 Profile updated!");
        });
    }

    public JPanel getPanel() {
        return profileUIPanel;
    }
}
