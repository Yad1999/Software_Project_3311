package loginprofile.test;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import org.jfree.chart.*;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class CanadaFoodGuideComparisonUI {

    private JPanel panel;
    private JComboBox<String> versionSelector;
    private JButton compareButton;
    private JPanel chartPanel;
    private JLabel feedbackLabel;  // New label for percentage feedback

    public CanadaFoodGuideComparisonUI() {
        initialize();
    }

    private void initialize() {
        panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout());

        versionSelector = new JComboBox<>(new String[] {"Canada Food Guide 2007", "Canada Food Guide 2019"});
        compareButton = new JButton("Compare My Diet");

        topPanel.add(new JLabel("Select CFG Version:"));
        topPanel.add(versionSelector);
        topPanel.add(compareButton);

        chartPanel = new JPanel(new BorderLayout());

        feedbackLabel = new JLabel(" ", SwingConstants.CENTER);  // Initially blank
        feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.add(feedbackLabel, BorderLayout.SOUTH);  // New label at bottom

        compareButton.addActionListener(e -> updateChart());
    }

    private void updateChart() {
        // Simulated data for visualization
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Vegetables & Fruits", 45);
        dataset.setValue("Whole Grains", 30);
        dataset.setValue("Protein Foods", 25);

        JFreeChart chart = ChartFactory.createPieChart(
                "Your Average Plate vs " + versionSelector.getSelectedItem(),
                dataset, true, true, false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));

        ChartPanel chartPane = new ChartPanel(chart);
        chartPane.setPreferredSize(new Dimension(600, 400));

        chartPanel.removeAll();
        chartPanel.add(chartPane, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();

        // Simulate alignment score (you'll replace this with actual logic later)
        int score = new Random().nextInt(21) + 75;  // 75% to 95%
        feedbackLabel.setText("✅ Your diet aligns " + score + "% with " + versionSelector.getSelectedItem() + ".");
    }

    public JPanel getPanel() {
        return panel;
    }
}
