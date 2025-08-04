package com.nutrisci.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The {@code MainDashboardUI} class represents the main dashboard window of the NutriSci application.
 * 
 * <p>This dashboard serves as a centralized navigation hub for users to access different features,
 * including logging meals, managing profiles, performing food swaps, visualizing nutrient intake,
 * viewing a meal journal, and comparing their diet to the Canada Food Guide.</p>
 * 
 * <p>The dashboard uses a {@link CardLayout} to switch between panels dynamically based on user actions.</p>
 */
public class MainDashboardUI {

    /** Main application frame. */
    private JFrame frame;

    /** Panel that holds all sub-panels using a CardLayout. */
    private JPanel mainPanel;

    /** Top navigation bar with feature buttons. */
    private JPanel navPanel;

    /** Layout manager for switching views in mainPanel. */
    private CardLayout cardLayout;

    // Navigation buttons
    private JButton mealLogButton;
    private JButton profileButton;
    private JButton swapButton;
    private JButton visualizeButton;
    private JButton mealLogJournalButton;
    private JButton dietGuideButton;

    // Feature-specific UI panels
    private MealSwapUI mealSwapUI;
    private NutrientVisualizationUI visualizationUI;

    /**
     * Constructs the main dashboard UI and initializes its components.
     */
    public MainDashboardUI() {
        initialize();
    }

    /**
     * Initializes the GUI components, navigation, and card-based layout switching logic.
     */
    private void initialize() {
        frame = new JFrame("NutriSci Dashboard");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // --- Navigation Panel ---
        navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        mealLogButton = new JButton("Log Meal");
        profileButton = new JButton("Profile");
        swapButton = new JButton("Meal Swaps");
        visualizeButton = new JButton("Visualize");
        mealLogJournalButton = new JButton("Meal Journal");
        dietGuideButton = new JButton("Canada Food Guide");

        navPanel.add(mealLogButton);
        navPanel.add(profileButton);
        navPanel.add(swapButton);
        navPanel.add(visualizeButton);
        navPanel.add(mealLogJournalButton);
        navPanel.add(dietGuideButton);

        frame.add(navPanel, BorderLayout.NORTH);

        // --- Main Panel with CardLayout ---
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Instantiate sub-UIs
        mealSwapUI = new MealSwapUI();
        visualizationUI = new NutrientVisualizationUI();

        // Add sub-panels to card layout
        mainPanel.add(new JLabel("Welcome to NutriSci!"), "Home");
        mainPanel.add(new MealLogUI().getPanel(), "MealLog");
        mainPanel.add(new ProfileUI().getPanel(), "Profile");
        mainPanel.add(mealSwapUI.getPanel(), "Swaps");
        mainPanel.add(visualizationUI.getPanel(), "Visualize");
        mainPanel.add(new CanadaFoodGuideComparisonUI().getPanel(), "DietGuide");

        frame.add(mainPanel, BorderLayout.CENTER);

        // --- Button Actions ---
        mealLogButton.addActionListener(e -> cardLayout.show(mainPanel, "MealLog"));
        profileButton.addActionListener(e -> cardLayout.show(mainPanel, "Profile"));
        swapButton.addActionListener(e -> cardLayout.show(mainPanel, "Swaps"));
        visualizeButton.addActionListener(e -> cardLayout.show(mainPanel, "Visualize"));
        mealLogJournalButton.addActionListener(e -> {
            MealLogJournalUI journal = new MealLogJournalUI();
            journal.show();
        });
        dietGuideButton.addActionListener(e -> cardLayout.show(mainPanel, "DietGuide"));
    }

    /**
     * Displays the main dashboard window.
     */
    public void show() {
        frame.setVisible(true);
    }
}
