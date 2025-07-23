package chart_test_demo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * PieChartDemo creates and displays a simple pie chart using the JFreeChart library.
 * <p>
 * This example demonstrates how to:
 * <ul>
 *   <li>Create a dataset for the pie chart</li>
 *   <li>Generate a pie chart using ChartFactory</li>
 *   <li>Display the chart in a simple Swing window using ChartFrame</li>
 * </ul>
 * 
 * Requirements:
 * - JFreeChart 1.5.3 or later
 * - Maven dependency for org.jfree:jfreechart in pom.xml
 * 
 * How to run:
 * Just execute the main method to see the pie chart window.
 * 
 * Author: AJ
 * Date: 2025-06-22
 */
public class PieChart_Demo {

    /**
     * The main method creates a pie chart with sample fruit data
     * and displays it in a Swing window.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a dataset with example values
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Eggs", 210);
        dataset.setValue("Rice", 206);
        dataset.setValue("Hazelnut Latte", 260);
        dataset.setValue("Banana", 89);

        // Create a pie chart from the dataset
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Meal Calorie Distribution", // chart title
                dataset,              // data
                true,                 // include legend
                true,                 // tooltips
                false                 // URLs
        );

        // Display the chart in a window
        ChartFrame frame = new ChartFrame("Demo JFreeChart", pieChart);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // center on screen
        frame.setVisible(true);
    }
}