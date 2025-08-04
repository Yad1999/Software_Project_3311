package com.nutrisci.ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * The {@code MealLogUI} class provides a graphical user interface for logging meals
 * in the NutriSci application.
 * 
 * <p>Users can input the date, select a meal type (e.g., breakfast, lunch),
 * add ingredients with quantities, and submit the meal. Upon submission,
 * total nutrient information (calories, protein, carbs, and fat) is calculated
 * based on a mock nutrition database.</p>
 * 
 * <p>This panel is typically embedded in the {@code MainDashboardUI} using a {@code CardLayout}.</p>
 */
public class MealLogUI {

    /** The main panel for the meal logging UI. */
    private JPanel mealLogPanel;

    /** Panel containing date and meal type fields. */
    private JPanel topPanel;

    /** Panel containing table and control buttons. */
    private JPanel bottomPanel;

    /** Sub-panel that holds add/delete/submit buttons. */
    private JPanel buttonPanel;

    /** Text field for entering the date of the meal. */
    private JTextField dateField;

    /** Combo box for selecting meal type. */
    private JComboBox<String> mealTypeComboBox;

    /** Table for listing ingredients and their quantities. */
    private JTable ingredientsTable;

    /** Model managing table data for ingredients. */
    private DefaultTableModel tableModel;

    /** Button to add a new row to the ingredient table. */
    private JButton addRowButton;

    /** Button to submit the meal and calculate nutrient totals. */
    private JButton submitButton;

    /** Button to delete a selected ingredient row. */
    private JButton deleteButton;

    /** Label to display success or error messages. */
    private JLabel statusLabel;

    /** Mock database used to retrieve nutritional values. */
    private MockNutritionDB nutritionDB = new MockNutritionDB();

    /**
     * Constructs the Meal Logging UI and initializes all components.
     */
    public MealLogUI() {
        initialize();
    }

    /**
     * Initializes the UI layout and components for date input, meal type,
     * ingredient entry, and submission functionality.
     */
    private void initialize() {
        mealLogPanel = new JPanel(new BorderLayout());

        // --- Top Panel: Date and Meal Type ---
        topPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        topPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        topPanel.add(dateField);

        topPanel.add(new JLabel("Meal Type:"));
        String[] mealTypes = {"Breakfast", "Lunch", "Dinner", "Snack"};
        mealTypeComboBox = new JComboBox<>(mealTypes);
        topPanel.add(mealTypeComboBox);

        mealLogPanel.add(topPanel, BorderLayout.NORTH);

        // --- Center Panel: Ingredients Table ---
        String[] columnNames = {"Ingredient", "Quantity (e.g., 100g)"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ingredientsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(ingredientsTable);
        mealLogPanel.add(scrollPane, BorderLayout.CENTER);

        // --- Bottom Panel: Buttons and Status ---
        bottomPanel = new JPanel(new BorderLayout());

        buttonPanel = new JPanel();

        addRowButton = new JButton("Add Ingredient");
        addRowButton.addActionListener(e -> tableModel.addRow(new Object[] {"", ""}));

        deleteButton = new JButton("Delete Ingredient");
        deleteButton.addActionListener(e -> {
            int selectedRow = ingredientsTable.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
            } else {
                statusLabel.setText("\u274C Please select a row to delete.");
            }
        });

        submitButton = new JButton("Submit Meal");
        submitButton.addActionListener(e -> handleSubmitMeal());

        buttonPanel.add(addRowButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(submitButton);

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        mealLogPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Processes the submitted meal entry by calculating total nutrient values based
     * on the entered ingredients and their quantities.
     * 
     * <p>If the date or ingredient list is empty, an error is displayed.
     * Otherwise, totals for calories, protein, carbs, and fat are computed
     * and printed to console, and the calorie total is displayed on screen.</p>
     */
    private void handleSubmitMeal() {
        String date = dateField.getText().trim();
        String mealType = (String) mealTypeComboBox.getSelectedItem();

        if (date.isEmpty() || tableModel.getRowCount() == 0) {
            statusLabel.setText("\u274C Please enter a date and at least one ingredient.");
            return;
        }

        double totalCalories = 0, totalProtein = 0, totalCarbs = 0, totalFat = 0;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String ingredient = (String) tableModel.getValueAt(i, 0);
            String quantityStr = (String) tableModel.getValueAt(i, 1);

            if (ingredient == null || quantityStr == null || ingredient.isEmpty() || quantityStr.isEmpty()) {
                continue;
            }

            try {
                double quantity = Double.parseDouble(quantityStr.replaceAll("[^\\d.]", ""));
                NutritionInfo info = nutritionDB.getNutrition(ingredient.toLowerCase());
                if (info != null) {
                    totalCalories += info.getCalories() * (quantity / 100);
                    totalProtein += info.getProtein() * (quantity / 100);
                    totalCarbs += info.getCarbs() * (quantity / 100);
                    totalFat += info.getFat() * (quantity / 100);
                }
            } catch (NumberFormatException ex) {
                // Skip invalid entries silently
            }
        }

        System.out.printf("Meal Logged: %s on %s\n", mealType, date);
        System.out.printf("Total Calories: %.2f | Protein: %.2fg | Carbs: %.2fg | Fat: %.2fg\n",
                totalCalories, totalProtein, totalCarbs, totalFat);

        statusLabel.setText(String.format("\u2705 Calories: %.2f kcal", totalCalories));
    }

    /**
     * Returns the main panel of the meal logging interface.
     *
     * @return the {@code JPanel} containing the entire meal logging UI.
     */
    public JPanel getPanel() {
        return mealLogPanel;
    }
}
