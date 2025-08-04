package com.nutrisci.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * The {@code MealSwapUI} class provides a user interface for setting nutritional swap goals
 * and visualizing food substitutions before and after the swap.
 *
 * <p>This panel allows users to input a date, select a meal type, and define two nutrient-based goals
 * (e.g., increase fiber, reduce calories). Upon submission, a popup displays a side-by-side comparison
 * of the original and swapped meals with visual cues and tooltips highlighting nutritional changes.</p>
 *
 * <p>Tooltips and color-coded cells in the comparison table enhance user feedback and clarity.</p>
 */
public class MealSwapUI {

    /** The main panel for this UI component. */
    private JPanel panel;

    /** Text field for entering the meal date. */
    private JTextField dateField;

    /** Dropdown menu for selecting the meal type. */
    private JComboBox<String> mealTypeBox;

    /** Dropdowns and fields for goal 1: nutrient type, precise amount, and intensity. */
    private JComboBox<String> nutrient1Box, intensity1Box;
    private JTextField preciseAmount1Field;

    /** Dropdowns and fields for goal 2: nutrient type, precise amount, and intensity. */
    private JComboBox<String> nutrient2Box, intensity2Box;
    private JTextField preciseAmount2Field;

    /** Button for submitting the swap goals. */
    private JButton submitButton;

    /** Label for displaying feedback messages. */
    private JLabel messageLabel;

    /**
     * Constructs the MealSwapUI and initializes the layout and components.
     */
    public MealSwapUI() {
        initialize();
    }

    /**
     * Initializes the panel layout, adds input sections and binds the submit button.
     */
    private void initialize() {
        panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = defaultConstraints();

        addDateAndMealSection(gbc);
        addSeparator(gbc, 2);
        addGoalSection(gbc, "Goal 1:", 3, true);
        addSeparator(gbc, 6);
        addGoalSection(gbc, "Goal 2:", 7, false);
        addSubmitAndMessage(gbc, 10);
    }

    /**
     * Returns a standard {@link GridBagConstraints} configuration for consistent layout spacing.
     *
     * @return a configured {@link GridBagConstraints} object.
     */
    private GridBagConstraints defaultConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    /**
     * Adds date and meal type selection inputs to the UI.
     *
     * @param gbc layout constraints to position components.
     */
    private void addDateAndMealSection(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Date (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        dateField = new JTextField();
        panel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Meal Type:"), gbc);

        gbc.gridx = 1;
        mealTypeBox = new JComboBox<>(new String[]{"Breakfast", "Lunch", "Dinner", "Snack"});
        panel.add(mealTypeBox, gbc);
    }

    /**
     * Adds a horizontal separator to visually separate form sections.
     *
     * @param gbc layout constraints.
     * @param row the grid row to place the separator on.
     */
    private void addSeparator(GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;
    }

    /**
     * Adds one goal section (nutrient, precise amount, and intensity) to the panel.
     *
     * @param gbc layout constraints.
     * @param label the label prefix (e.g., "Goal 1:").
     * @param startRow the starting grid row for the section.
     * @param isFirstGoal whether this is the first goal (affects field assignments).
     */
    private void addGoalSection(GridBagConstraints gbc, String label, int startRow, boolean isFirstGoal) {
        String[] nutrients = {"Calories", "Protein", "Carbs", "Fat", "Fiber"};
        String[] intensityOptions = {"Slightly", "Moderately", "Significantly"};

        gbc.gridy = startRow;
        gbc.gridx = 0;
        panel.add(new JLabel(label + " Nutrient"), gbc);

        gbc.gridx = 1;
        JComboBox<String> nutrientBox = new JComboBox<>(nutrients);
        panel.add(nutrientBox, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Precise Amount (optional):"), gbc);

        gbc.gridx = 1;
        JTextField amountField = new JTextField();
        panel.add(amountField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Intensity / Direction"), gbc);

        gbc.gridx = 1;
        JComboBox<String> intensityBox = new JComboBox<>(intensityOptions);
        panel.add(intensityBox, gbc);

        if (isFirstGoal) {
            nutrient1Box = nutrientBox;
            preciseAmount1Field = amountField;
            intensity1Box = intensityBox;
        } else {
            nutrient2Box = nutrientBox;
            preciseAmount2Field = amountField;
            intensity2Box = intensityBox;
        }
    }

    /**
     * Adds the submit button and message label to the layout and sets up its event handler.
     *
     * @param gbc layout constraints.
     * @param startRow the grid row to start the section on.
     */
    private void addSubmitAndMessage(GridBagConstraints gbc, int startRow) {
        gbc.gridx = 0;
        gbc.gridy = startRow;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        submitButton = new JButton("Submit Goals");
        panel.add(submitButton, gbc);

        gbc.gridy++;
        messageLabel = new JLabel(" ");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(messageLabel, gbc);

        submitButton.addActionListener(e -> {
            Object[][] before = {
                {"Tomato", "50g", "10", "0.5", "2", "0.2"},
                {"Bread", "100g", "250", "9", "45", "2"}
            };
            Object[][] after = {
                {"Tomato", "50g", "10", "0.5", "2", "0.2"},
                {"Whole Grain Bread", "100g", "240", "10", "42", "4"}
            };
            updateComparisonTable(before, after);
        });
    }

    /**
     * Opens a popup frame displaying a side-by-side nutrient comparison of the original
     * and swapped meals, with color-coded cell differences and tooltips.
     *
     * @param beforeData 2D array of data before swap.
     * @param afterData 2D array of data after swap.
     */
    private void updateComparisonTable(Object[][] beforeData, Object[][] afterData) {
        String[] columnNames = {"Item", "Quantity", "Calories", "Protein", "Carbs", "Fiber"};

        JFrame compareFrame = new JFrame("Before vs After Swap");
        compareFrame.setSize(1000, 400);
        compareFrame.setLocationRelativeTo(null);
        compareFrame.setLayout(new BorderLayout());

        JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        tablesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTable beforeTable = new JTable(new DefaultTableModel(beforeData, columnNames));
        JTable afterTable = new JTable(new DefaultTableModel(afterData, columnNames));

        beforeTable.setEnabled(false);
        afterTable.setEnabled(false);

        applyTooltipAndColoring(beforeTable, afterTable);

        JScrollPane beforeScroll = new JScrollPane(beforeTable);
        JScrollPane afterScroll = new JScrollPane(afterTable);

        beforeScroll.setBorder(BorderFactory.createTitledBorder("Before Swap"));
        afterScroll.setBorder(BorderFactory.createTitledBorder("After Swap"));

        tablesPanel.add(beforeScroll);
        tablesPanel.add(afterScroll);
        compareFrame.add(tablesPanel, BorderLayout.CENTER);

        JButton applyButton = new JButton("Apply Swap to Previous Meals");
        applyButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(compareFrame, "Swap applied to previous meals successfully!");
            compareFrame.dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(applyButton);
        compareFrame.add(bottomPanel, BorderLayout.SOUTH);

        compareFrame.setVisible(true);
    }

    /**
     * Applies tooltip annotations and background color highlighting to the "After" table
     * based on numeric differences compared to the "Before" table.
     *
     * @param beforeTable the original meal nutrient table.
     * @param afterTable  the proposed swapped meal nutrient table.
     */
    private void applyTooltipAndColoring(JTable beforeTable, JTable afterTable) {
        DefaultTableModel beforeModel = (DefaultTableModel) beforeTable.getModel();
        DefaultTableModel afterModel = (DefaultTableModel) afterTable.getModel();

        for (int col = 2; col < afterModel.getColumnCount(); col++) {
            final int fCol = col;
            afterTable.getColumnModel().getColumn(col).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
                Object beforeVal = beforeModel.getValueAt(row, fCol);
                Object afterVal = afterModel.getValueAt(row, fCol);
                JLabel cell = new JLabel(afterVal != null ? afterVal.toString() : "");
                cell.setOpaque(true);

                if (beforeVal != null && afterVal != null) {
                    try {
                        double beforeNum = Double.parseDouble(beforeVal.toString());
                        double afterNum = Double.parseDouble(afterVal.toString());

                        if (afterNum > beforeNum) {
                            cell.setBackground(Color.GREEN.brighter());
                        } else if (afterNum < beforeNum) {
                            cell.setBackground(Color.PINK);
                        }
                        cell.setToolTipText(String.format("Was %.2f, now %.2f", beforeNum, afterNum));
                    } catch (NumberFormatException ignored) {}
                }
                return cell;
            });
        }
    }

    /**
     * Returns the main panel component for integration into a container (e.g., dashboard).
     *
     * @return the {@link JPanel} containing the MealSwapUI.
     */
    public JPanel getPanel() {
        return panel;
    }
}
