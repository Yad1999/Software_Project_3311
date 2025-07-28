package loginprofile.test;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GoalSettingUI {

    private JPanel panel;
    private JTextField dateField;
    private JComboBox<String> mealTypeBox;
    private JComboBox<String> nutrient1Box, intensity1Box, nutrient2Box, intensity2Box;
    private JTextField preciseAmount1Field, preciseAmount2Field;
    private JButton submitButton;
    private JLabel messageLabel;

    public GoalSettingUI() {
        initialize();
    }

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

    private GridBagConstraints defaultConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

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

    private void addSeparator(GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;
    }

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

        // New button for applying swap
        JButton applyButton = new JButton("Apply Swap to Previous Meals");
        applyButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(compareFrame, "Swap applied to previous meals successfully!");
            compareFrame.dispose(); // closes the popup
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(applyButton);
        compareFrame.add(bottomPanel, BorderLayout.SOUTH);

        compareFrame.setVisible(true);
    }


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

    public JPanel getPanel() {
        return panel;
    }
}
