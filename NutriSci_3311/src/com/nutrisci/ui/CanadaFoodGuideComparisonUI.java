package com.nutrisci.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import org.jfree.chart.*;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 * The {@code CanadaFoodGuideComparisonUI} class provides a user interface for visualizing
 * how well a user's average diet aligns with a selected version of the Canada Food Guide.
 *
 * <p>Users can choose between the 2007 and 2019 versions of the Canada Food Guide.
 * Upon clicking "Compare My Diet", a pie chart is displayed showing the distribution
 * of food groups in the user's diet, and a percentage alignment score is shown at the bottom.</p>
 *
 * <p>This class currently uses simulated data and a random alignment score. Future versions
 * can replace the random score with actual dietary analysis logic.</p>
 */
public class CanadaFoodGuideComparisonUI {

    /** Main panel containing all UI components. */
    private JPanel panel;

    /** Dropdown for selecting Canada Food Guide version. */
    private JComboBox<String> versionSelector;

    /** Button to trigger comparison and chart generation. */
    private JButton compareButton;

    /** Panel to hold the JFreeChart component. */
    private JPanel chartPanel;

    /** Label for displaying feedback score about diet alignment. */
    private JLabel feedbackLabel;

    /**
     * Constructs the CanadaFoodGuideComparisonUI and initializes its components.
     */
    public CanadaFoodGuideComparisonUI() {
        initialize();
    }

    /**
     * Initializes the layout and components, including the chart area,
     * version selector, button, and feedback label.
     */
    private void initialize() {
        panel = new JPanel(new BorderLayout());

        // Top controls (dropdown and button)
        JPanel topPanel = new JPanel(new FlowLayout());
        versionSelector = new JComboBox<>(new String[] {
                "Canada Food Guide 2007", "Canada Food Guide 2019"
        });
        compareButton = new JButton("Compare My Diet");

        topPanel.add(new JLabel("Select CFG Version:"));
        topPanel.add(versionSelector);
        topPanel.add(compareButton);

        // Chart display area
        chartPanel = new JPanel(new BorderLayout());

        // Feedback label at bottom
        feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Add components to main panel
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.add(feedbackLabel, BorderLayout.SOUTH);

        // Action listener for comparison
        compareButton.addActionListener(e -> updateChart());
    }

    /**
     * Updates the chart and feedback label with a simulated comparison of the user's diet
     * against the selected Canada Food Guide version.
     *
     * <p>This version uses hardcoded data and a randomly generated alignment score.
     * The pie chart shows a breakdown of "Vegetables & Fruits", "Whole Grains", and "Protein Foods".</p>
     */
    private void updateChart() {
        // Simulated food group distribution
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Vegetables & Fruits", 45);
        dataset.setValue("Whole Grains", 30);
        dataset.setValue("Protein Foods", 25);

        JFreeChart chart = ChartFactory.createPieChart(
                "Your Average Plate vs " + versionSelector.getSelectedItem(),
                dataset, true, true, false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));

        ChartPanel chartPane = new ChartPanel(chart);
        chartPane.setPreferredSize(new Dimension(600, 400));

        chartPanel.removeAll();
        chartPanel.add(chartPane, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();

        // Simulated percentage match (replace with real logic in production)
        int score = new Random().nextInt(21) + 75;  // Generates between 75% and 95%
        feedbackLabel.setText("✅ Your diet aligns " + score + "% with " + versionSelector.getSelectedItem() + ".");
    }

    /**
     * Returns the main panel containing the entire comparison UI.
     *
     * @return the {@link JPanel} for embedding into a parent container.
     */
    public JPanel getPanel() {
        return panel;
    }
}
