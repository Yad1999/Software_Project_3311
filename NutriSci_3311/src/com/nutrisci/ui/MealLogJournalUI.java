package com.nutrisci.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * The {@code MealLogJournalUI} class provides a graphical window for viewing a user's meal log history
 * in table format. Each meal entry displays its date, meal type, and total calories.
 * 
 * <p>When a user selects a row from the table, nutritional details (protein, carbs, fat)
 * are displayed at the bottom of the UI. Duplicate entries (by date and meal type) are prevented.</p>
 *
 * <p>This class depends on the {@link MealEntry} model to represent individual meal data.</p>
 */
public class MealLogJournalUI {

    /** The main window frame for the journal UI. */
    private JFrame frame;

    /** The root panel holding all UI components. */
    private JPanel journalPanel;

    /** Table displaying the list of logged meals. */
    private JTable journalTable;

    /** Table model backing the {@code journalTable}. */
    private DefaultTableModel tableModel;

    /** Label for displaying protein, carb, and fat details. */
    private JLabel detailLabel;

    /** Internal list of meal entries shown in the journal. */
    private List<MealEntry> mealEntries = new ArrayList<>();

    /**
     * Constructs the MealLogJournalUI and initializes the UI components.
     */
    public MealLogJournalUI() {
        initialize();
    }

    /**
     * Initializes the frame, table, layout, listeners, and display components.
     */
    private void initialize() {
        frame = new JFrame("Meal Journal");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        journalPanel = new JPanel(new BorderLayout());

        // Table setup
        String[] columns = {"Date", "Meal Type", "Calories (kcal)"};
        tableModel = new DefaultTableModel(columns, 0);
        journalTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(journalTable);
        journalPanel.add(scrollPane, BorderLayout.CENTER);

        // Detail label setup
        detailLabel = new JLabel(" ");
        detailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        journalPanel.add(detailLabel, BorderLayout.SOUTH);

        // Row selection listener
        journalTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = journalTable.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < mealEntries.size()) {
                MealEntry selectedMeal = mealEntries.get(selectedRow);
                detailLabel.setText(String.format("Protein: %.1fg | Carbs: %.1fg | Fat: %.1fg",
                        selectedMeal.getTotalProtein(), selectedMeal.getTotalCarbs(), selectedMeal.getTotalFat()));
            }
        });

        frame.setContentPane(journalPanel);
    }

    /**
     * Displays the journal UI window to the user.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Adds a new {@link MealEntry} to the journal if it's valid and not a duplicate.
     * 
     * <p>Duplicate entries (same date and meal type) are rejected and trigger a message.
     * If required fields (date or meal type) are missing, an error is shown as well.</p>
     *
     * @param entry the {@link MealEntry} to add to the journal.
     */
    public void addMealEntry(MealEntry entry) {
        for (MealEntry existing : mealEntries) {
            if (existing.getDate().equals(entry.getDate()) &&
                existing.getMealType().equals(entry.getMealType())) {
                detailLabel.setText("Duplicate meal entry for this date and type.");
                return;
            }
        }

        if (entry.getDate() == null || entry.getMealType() == null ||
            entry.getDate().isEmpty() || entry.getMealType().isEmpty()) {
            detailLabel.setText("Invalid meal entry. Missing required fields.");
            return;
        }

        mealEntries.add(entry);
        tableModel.addRow(new Object[] {
            entry.getDate(), entry.getMealType(), String.format("%.1f", entry.getTotalCalories())
        });
    }
}
