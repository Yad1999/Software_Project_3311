package loginprofile.test;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class MealLogUI {

	private JFrame frame;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JPanel buttonPanel;
	private JTextField dateField;
	private JComboBox<String> mealTypeComboBox;
	private JTable ingredientsTable;
	private DefaultTableModel tableModel;
	private JButton addRowButton;
	private JButton submitButton;
	private JLabel statusLabel;
	
	
	public MealLogUI() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Meal Logger");
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		
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
		
		frame.add(topPanel, BorderLayout.NORTH);
		
		// Center Panel: ingredients table
		String[] columnNames = {"Ingredient", "Quantity (e.g., 100g)"};
		tableModel = new DefaultTableModel(columnNames, 0);
		ingredientsTable = new JTable(tableModel);
		
		JScrollPane scrollPane = new JScrollPane(ingredientsTable);
		frame.add(scrollPane, BorderLayout.CENTER);
		
		// Bottom Panel: add row, submit, and status
		bottomPanel = new JPanel(new BorderLayout());
		
		buttonPanel = new JPanel();
		addRowButton = new JButton("Add Ingredient");
		addRowButton.addActionListener(e -> tableModel.addRow(new Object[] {"", ""}));
		
		submitButton = new JButton("Submit Meal");
		submitButton.addActionListener(e -> handleSubmitMeal());
		
		buttonPanel.add(addRowButton);
	    buttonPanel.add(submitButton);
	     
	    bottomPanel.add(buttonPanel, BorderLayout.NORTH);
	     
	    statusLabel = new JLabel(" ");
	    statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    bottomPanel.add(statusLabel, BorderLayout.SOUTH);
	     
	    frame.add(bottomPanel, BorderLayout.SOUTH);
	}
	
	private void handleSubmitMeal() {
		String date = dateField.getText().trim();
		String mealType = (String) mealTypeComboBox.getSelectedItem();
		
		if (date.isEmpty() || tableModel.getRowCount() == 0) {
            statusLabel.setText("\u274C Please enter a date and at least one ingredient.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Meal Logged: ").append(mealType).append(" on ").append(date).append("\n");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String ingredient = (String) tableModel.getValueAt(i, 0);
            String quantity = (String) tableModel.getValueAt(i, 1);

            if (ingredient == null || quantity == null || ingredient.isEmpty() || quantity.isEmpty()) {
                continue;
            }

            sb.append("- ").append(ingredient).append(" : ").append(quantity).append("\n");
        }

        System.out.println(sb.toString());
        statusLabel.setText("\u2705 Meal submitted successfully!");
	}
	
	public void show() {
		frame.setVisible(true);
	}
}
