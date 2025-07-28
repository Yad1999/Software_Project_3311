package loginprofile.test;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class MealLogUI {

    private JPanel mealLogPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel buttonPanel;
    private JTextField dateField;
    private JComboBox<String> mealTypeComboBox;
    private JTable ingredientsTable;
    private DefaultTableModel tableModel;
    private JButton addRowButton;
    private JButton submitButton;
    private JButton deleteButton;
    private JLabel statusLabel;

    private MockNutritionDB nutritionDB = new MockNutritionDB();

    public MealLogUI() {
        initialize();
    }

    private void initialize() {
        mealLogPanel = new JPanel(new BorderLayout());

        // Top panel: date + meal type
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

        // Center Panel: ingredients table
        String[] columnNames = {"Ingredient", "Quantity (e.g., 100g)"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ingredientsTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(ingredientsTable);
        mealLogPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel: add row, submit, and status
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
                // Skip invalid entries
            }
        }

        System.out.printf("Meal Logged: %s on %s\n", mealType, date);
        System.out.printf("Total Calories: %.2f | Protein: %.2fg | Carbs: %.2fg | Fat: %.2fg\n",
                totalCalories, totalProtein, totalCarbs, totalFat);

        statusLabel.setText(String.format("\u2705 Calories: %.2f kcal", totalCalories));
    }

    public JPanel getPanel() {
        return mealLogPanel;
    }
}
