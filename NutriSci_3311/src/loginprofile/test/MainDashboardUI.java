package loginprofile.test;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainDashboardUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JPanel navPanel;
    private CardLayout cardLayout;

    private JButton mealLogButton;
    private JButton profileButton;
    private JButton swapButton;
    private JButton visualizeButton;
    private JButton mealLogJournalButton;
    private JButton dietGuideButton;

    private GoalSettingUI goalSettingUI;
    private NutrientVisualizationUI visualizationUI;  // ← Newly added

    public MainDashboardUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("NutriSci Dashboard");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Top Navigation Panel
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

        // Main Panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Reusable UIs
        goalSettingUI = new GoalSettingUI();
        visualizationUI = new NutrientVisualizationUI(); // ← Create instance

        mainPanel.add(new JLabel("Welcome to NutriSci!"), "Home");
        mainPanel.add(new MealLogUI().getPanel(), "MealLog");
        mainPanel.add(new ProfileUI().getPanel(), "Profile");
        mainPanel.add(goalSettingUI.getPanel(), "Swaps");
        mainPanel.add(visualizationUI.getPanel(), "Visualize");
        mainPanel.add(new CanadaFoodGuideComparisonUI().getPanel(), "DietGuide");

        frame.add(mainPanel, BorderLayout.CENTER);

        // Button Actions
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

    public void show() {
        frame.setVisible(true);
    }
}
