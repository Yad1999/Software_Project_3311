package com.nutrisci.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;

/**
 * The {@code MealSwapUI} class provides a user interface to define nutrient goals
 * and visualize meal swaps through tabular and graphical comparisons.
 * <p>
 * Users can select a date and meal type, specify two nutrient goals, and view the
 * impact of swaps using colored tables and switchable line/bar charts.
 */
public class MealSwapUI {

    /** Root panel containing all UI components. */
    private JPanel panel;

    /** Input field for entering the meal date. */
    private JTextField dateField;

    /** Dropdown for selecting meal type. */
    private JComboBox<String> mealTypeBox;

    /** First nutrient goal: nutrient type, intensity, and precise amount. */
    private JComboBox<String> nutrient1Box, intensity1Box;
    private JTextField preciseAmount1Field;

    /** Second nutrient goal: nutrient type, intensity, and precise amount. */
    private JComboBox<String> nutrient2Box, intensity2Box;
    private JTextField preciseAmount2Field;

    /** Button to submit goal settings. */
    private JButton submitButton;

    /** Message label for feedback and validation messages. */
    private JLabel messageLabel;

    /**
     * Constructs a new {@code MealSwapUI} and initializes the interface.
     */
    public MealSwapUI() {
        initialize();
    }

    /**
     * Initializes the UI layout and its sections.
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
     * Returns a configured {@link GridBagConstraints} for uniform layout.
     *
     * @return the default layout constraints.
     */
    private GridBagConstraints defaultConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    /**
     * Adds UI components for date input and meal type selection.
     *
     * @param gbc layout constraints.
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
     * Adds a horizontal separator line.
     *
     * @param gbc  layout constraints.
     * @param row  the row position of the separator.
     */
    private void addSeparator(GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;
    }

    /**
     * Adds input fields for nutrient goal definition.
     *
     * @param gbc layout constraints.
     * @param label label prefix (e.g., "Goal 1").
     * @param startRow starting row index.
     * @param isFirstGoal flag to bind to first or second goal.
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
     * Adds a submit button and message label to the UI.
     *
     * @param gbc layout constraints.
     * @param startRow row to begin component placement.
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
     * Opens a comparison frame showing nutritional tables and visual tools.
     *
     * @param beforeData 2D array of pre-swap meal data.
     * @param afterData  2D array of post-swap meal data.
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

        JPanel bottomPanel = new JPanel();

        JButton applyButton = new JButton("Apply Swap to Previous Meals");
        applyButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(compareFrame, "Swap applied to previous meals successfully!");
            compareFrame.dispose();
        });

        JButton visualizeButton = new JButton("Visualize Swap");
        visualizeButton.addActionListener(e -> showLineBarChart(beforeData, afterData));

        bottomPanel.add(visualizeButton);
        bottomPanel.add(applyButton);
        compareFrame.add(bottomPanel, BorderLayout.SOUTH);

        compareFrame.setVisible(true);
    }

    /**
     * Opens a chart frame to visualize nutrient totals before and after the swap.
     * Allows toggling between line and bar chart formats.
     *
     * @param beforeData 2D array of pre-swap meal data.
     * @param afterData  2D array of post-swap meal data.
     */
    private void showLineBarChart(Object[][] beforeData, Object[][] afterData) {
        String[] nutrients = {"Calories", "Protein", "Carbs", "Fiber"};
        double[] totalBefore = new double[nutrients.length];
        double[] totalAfter = new double[nutrients.length];

        for (Object[] row : beforeData) {
            for (int i = 0; i < nutrients.length; i++) {
                try {
                    totalBefore[i] += Double.parseDouble(row[i + 2].toString());
                } catch (NumberFormatException ignored) {}
            }
        }

        for (Object[] row : afterData) {
            for (int i = 0; i < nutrients.length; i++) {
                try {
                    totalAfter[i] += Double.parseDouble(row[i + 2].toString());
                } catch (NumberFormatException ignored) {}
            }
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < nutrients.length; i++) {
            dataset.addValue(totalBefore[i], "Before", nutrients[i]);
            dataset.addValue(totalAfter[i], "After", nutrients[i]);
        }

        JPanel chartPanelContainer = new JPanel(new BorderLayout());
        JFrame chartFrame = new JFrame("Nutrient Comparison Chart");
        chartFrame.setSize(800, 500);
        chartFrame.setLocationRelativeTo(null);
        chartFrame.setLayout(new BorderLayout());

        JFreeChart chart = ChartFactory.createLineChart(
            "Total Nutrient Changes Before vs After Swap",
            "Nutrient", "Amount", dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanelContainer.add(chartPanel, BorderLayout.CENTER);

        JButton toggleButton = new JButton("Switch to Bar Chart");
        toggleButton.addActionListener(e -> {
            chartPanelContainer.removeAll();
            JFreeChart newChart;

            if (toggleButton.getText().contains("Bar")) {
                newChart = ChartFactory.createBarChart(
                    "Total Nutrient Changes Before vs After Swap",
                    "Nutrient", "Amount", dataset);
                toggleButton.setText("Switch to Line Chart");
            } else {
                newChart = ChartFactory.createLineChart(
                    "Total Nutrient Changes Before vs After Swap",
                    "Nutrient", "Amount", dataset);
                toggleButton.setText("Switch to Bar Chart");
            }

            ChartPanel newPanel = new ChartPanel(newChart);
            chartPanelContainer.add(newPanel, BorderLayout.CENTER);
            chartPanelContainer.revalidate();
            chartPanelContainer.repaint();
        });

        JPanel togglePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        togglePanel.add(toggleButton);

        chartFrame.add(chartPanelContainer, BorderLayout.CENTER);
        chartFrame.add(togglePanel, BorderLayout.SOUTH);
        chartFrame.setVisible(true);
    }

    /**
     * Applies tooltip text and background color highlighting to indicate
     * nutrient increases or decreases in the "After" table.
     *
     * @param beforeTable JTable showing original meal values.
     * @param afterTable  JTable showing swapped meal values.
     */
    private void applyTooltipAndColoring(JTable beforeTable, JTable afterTable) {
        DefaultTableModel beforeModel = (DefaultTableModel) beforeTable.getModel();
        DefaultTableModel afterModel = (DefaultTableModel) afterTable.getModel();

        for (int col = 2; col < afterModel.getColumnCount(); col++) {
            final int fCol = col;
            afterTable.getColumnModel().getColumn(col).setCellRenderer((table, value, isSelected, hasFocus, row, column) ->
                    createStyledCell(beforeModel, afterModel, row, fCol)
            );
        }
    }

    /**
     * Creates a styled table cell with background color and tooltip
     * based on before and after nutrient values.
     *
     * @param beforeModel the table model with original values
     * @param afterModel  the table model with swapped values
     * @param row         the row index
     * @param col         the column index
     * @return a styled JLabel for the table cell
     */
    private JLabel createStyledCell(DefaultTableModel beforeModel, DefaultTableModel afterModel, int row, int col) {
        Object beforeVal = beforeModel.getValueAt(row, col);
        Object afterVal = afterModel.getValueAt(row, col);

        JLabel cell = new JLabel(afterVal != null ? afterVal.toString() : "");
        cell.setOpaque(true);

        if (beforeVal == null || afterVal == null) {
            return cell;
        }

        try {
            double beforeNum = Double.parseDouble(beforeVal.toString());
            double afterNum = Double.parseDouble(afterVal.toString());
            applyColorAndTooltip(cell, beforeNum, afterNum);
        } catch (NumberFormatException ignored) {
            // Invalid numeric value, skip styling
        }

        return cell;
    }

    /**
     * Applies background color and tooltip based on the comparison
     * between before and after values.
     *
     * @param cell       the label representing the cell
     * @param beforeNum  the original nutrient value
     * @param afterNum   the swapped nutrient value
     */
    private void applyColorAndTooltip(JLabel cell, double beforeNum, double afterNum) {
        if (afterNum > beforeNum) {
            cell.setBackground(Color.GREEN.brighter());
        } else if (afterNum < beforeNum) {
            cell.setBackground(Color.PINK);
        }

        cell.setToolTipText(String.format("Was %.2f, now %.2f", beforeNum, afterNum));
    }


    /**
     * Returns the main panel for integration into a larger container.
     *
     * @return the root {@link JPanel}.
     */
    public JPanel getPanel() {
        return panel;
    }
}
