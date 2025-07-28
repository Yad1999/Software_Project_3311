package loginprofile.test;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class NutrientVisualizationUI {

    private JPanel mainPanel;
    private JTextField startDateField, endDateField;
    private JComboBox<String> topNSelector;
    private JButton visualizeButton, toggleChartButton;
    private JLabel summaryLabel;
    private JPanel chartContainer;
    private boolean showingPie = true;

    private Map<String, Double> sampleData = new LinkedHashMap<>();

    public NutrientVisualizationUI() {
        initializeSampleData(); // Simulated values for now
        initializeUI();
    }

    private void initializeUI() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Top control panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        startDateField = new JTextField();
        endDateField = new JTextField();
        topNSelector = new JComboBox<>(new String[]{"Top 5", "Top 10"});
        visualizeButton = new JButton("Visualize");
        toggleChartButton = new JButton("Switch to Bar Chart");

        inputPanel.add(new JLabel("Start Date (YYYY-MM-DD):"));
        inputPanel.add(startDateField);
        inputPanel.add(new JLabel("End Date (YYYY-MM-DD):"));
        inputPanel.add(endDateField);
        inputPanel.add(new JLabel("Top Nutrients:"));
        inputPanel.add(topNSelector);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Chart panel
        chartContainer = new JPanel(new BorderLayout());
        mainPanel.add(chartContainer, BorderLayout.CENTER);

        // Bottom controls
        JPanel bottomPanel = new JPanel(new BorderLayout());
        summaryLabel = new JLabel(" ");
        bottomPanel.add(summaryLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(visualizeButton);
        buttonPanel.add(toggleChartButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Listeners
        visualizeButton.addActionListener(e -> updateChart());
        toggleChartButton.addActionListener(e -> {
            showingPie = !showingPie;
            toggleChartButton.setText(showingPie ? "Switch to Bar Chart" : "Switch to Pie Chart");
            updateChart();
        });
    }

    private void initializeSampleData() {
        sampleData.put("Carbohydrates", 55.0);
        sampleData.put("Protein", 18.0);
        sampleData.put("Fat", 22.0);
        sampleData.put("Fiber", 3.0);
        sampleData.put("Sodium", 1.0);
        sampleData.put("Sugar", 0.5);
        sampleData.put("Calcium", 0.3);
        sampleData.put("Iron", 0.2);
        sampleData.put("Vitamin C", 0.2);
        sampleData.put("Other", 0.8);
    }

    private void updateChart() {
        chartContainer.removeAll();

        int topN = topNSelector.getSelectedIndex() == 0 ? 5 : 10;
        Map<String, Double> displayData = new LinkedHashMap<>();
        double otherSum = 0;
        int count = 0;

        for (Map.Entry<String, Double> entry : sampleData.entrySet()) {
            if (count < topN) {
                displayData.put(entry.getKey(), entry.getValue());
            } else {
                otherSum += entry.getValue();
            }
            count++;
        }
        if (otherSum > 0) displayData.put("Other", otherSum);

        JFreeChart chart = showingPie ? createPieChart(displayData) : createBarChart(displayData);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));

        chartContainer.add(chartPanel, BorderLayout.CENTER);
        chartContainer.revalidate();
        chartContainer.repaint();

        summaryLabel.setText("You're at ~98% of the recommended daily portions. Keep going!");
    }

    private JFreeChart createPieChart(Map<String, Double> data) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        return ChartFactory.createPieChart("Average Daily Nutrient Intake", dataset, true, true, false);
    }

    private JFreeChart createBarChart(Map<String, Double> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Nutrient", entry.getKey());
        }

        return ChartFactory.createBarChart("Average Daily Nutrient Intake", "Nutrient", "Percentage (%)",
                dataset, PlotOrientation.VERTICAL, false, true, false);
    }

    public JPanel getPanel() {
        return mainPanel;
    }
}
