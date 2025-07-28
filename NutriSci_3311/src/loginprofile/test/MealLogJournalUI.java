package loginprofile.test;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MealLogJournalUI {

    private JFrame frame;
    private JPanel journalPanel;
    private JTable journalTable;
    private DefaultTableModel tableModel;
    private JLabel detailLabel;
    private List<MealEntry> mealEntries = new ArrayList<>();

    public MealLogJournalUI() {
        initialize();
    }

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

        // Detail label
        detailLabel = new JLabel(" ");
        detailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        journalPanel.add(detailLabel, BorderLayout.SOUTH);

        // Selection listener
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

    public void show() {
        frame.setVisible(true);
    }

    public void addMealEntry(MealEntry entry) {
        for (MealEntry existing : mealEntries) {
            if (existing.getDate().equals(entry.getDate()) && existing.getMealType().equals(entry.getMealType())) {
                detailLabel.setText("Duplicate meal entry for this date and type.");
                return;
            }
        }

        if (entry.getDate() == null || entry.getMealType() == null || entry.getDate().isEmpty() || entry.getMealType().isEmpty()) {
            detailLabel.setText("Invalid meal entry. Missing required fields.");
            return;
        }

        mealEntries.add(entry);
        tableModel.addRow(new Object[] {
            entry.getDate(), entry.getMealType(), String.format("%.1f", entry.getTotalCalories())
        });
    }
}
