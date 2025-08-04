package com.nutrisci.ui;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The {@code NutrientVisualizationUI} class provides a graphical user interface
 * for visualizing average daily nutrient intake over a selected time period.
 * 
 * <p>Users can select a date range and choose to view the top 5 or top 10 nutrients
 * from a simulated dataset. Nutrients can be visualized as a pie chart or a bar chart
 * using JFreeChart. The chart view can be toggled interactively.</p>
 * 
 * <p>This panel is designed to be embedded into a dashboard layout (e.g., {@code MainDashboardUI}).</p>
 */
public class NutrientVisualizationUI {

    /** The main container panel for this UI. */
    private JPanel mainPanel;

    /** Text fields for user input of start and end dates. */
    private JTextField startDateField, endDateField;

    /** Dropdown selector to choose between viewing top 5 or top 10 nutrients. */
    private JComboBox<String> topNSelector;

    /** Button to trigger chart visualization. */
    private JButton visualizeButton;

    /** Button to toggle between pie chart and bar chart. */
    private JButton toggleChartButton;

    /** Label to display a summary or progress message below the chart. */
    private JLabel summaryLabel;

    /** Panel used to display the chart component dynamically. */
    private JPanel chartContainer;

    /** Flag to determine whether the current chart is a pie chart or bar chart. */
    private boolean showingPie = true;

    /** Sample nutrient data to be visualized (simulated for demo/testing). */
    private Map<String, Double> sampleData = new LinkedHashMap<>();

    /**
     * Constructs the NutrientVisualizationUI and initializes all components.
     */
    public NutrientVisualizationUI() {
        initializeSampleData();
        initializeUI();
    }

    /**
     * Initializes the simulated nutrient intake data.
     */
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

    /**
     * Initializes the UI layout, input controls, buttons, chart area, and listeners.
     */
    private void initializeUI() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Top input panel
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

        // Chart container
        chartContainer = new JPanel(new BorderLayout());
        mainPanel.add(chartContainer, BorderLayout.CENTER);

        // Bottom panel with summary and action buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        summaryLabel = new JLabel(" ");
        bottomPanel.add(summaryLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(visualizeButton);
        buttonPanel.add(toggleChartButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Button listeners
        visualizeButton.addActionListener(e -> updateChart());

        toggleChartButton.addActionListener(e -> {
            showingPie = !showingPie;
            toggleChartButton.setText(showingPie ? "Switch to Bar Chart" : "Switch to Pie Chart");
            updateChart();
        });
    }

    /**
     * Updates the chart view based on the selected chart type and top N nutrients.
     * Generates a new chart and displays it within the chart container panel.
     */
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
        if (otherSum > 0) {
            displayData.put("Other", otherSum);
        }

        JFreeChart chart = showingPie
                ? createPieChart(displayData)
                : createBarChart(displayData);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));

        chartContainer.add(chartPanel, BorderLayout.CENTER);
        chartContainer.revalidate();
        chartContainer.repaint();

        summaryLabel.setText("You're at ~98% of the recommended daily portions. Keep going!");
    }

    /**
     * Creates a pie chart using the provided nutrient data.
     *
     * @param data a {@link Map} containing nutrient names and their corresponding percentages.
     * @return a {@link JFreeChart} pie chart.
     */
    private JFreeChart createPieChart(Map<String, Double> data) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        return ChartFactory.createPieChart("Average Daily Nutrient Intake", dataset, true, true, false);
    }

    /**
     * Creates a bar chart using the provided nutrient data.
     *
     * @param data a {@link Map} containing nutrient names and their corresponding percentages.
     * @return a {@link JFreeChart} bar chart.
     */
    private JFreeChart createBarChart(Map<String, Double> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Nutrient", entry.getKey());
        }
        return ChartFactory.createBarChart(
                "Average Daily Nutrient Intake",
                "Nutrient",
                "Percentage (%)",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
    }

    /**
     * Returns the main panel for embedding this UI in an external container.
     *
     * @return the main {@link JPanel} containing the entire visualization UI.
     */
    public JPanel getPanel() {
        return mainPanel;
    }
}
