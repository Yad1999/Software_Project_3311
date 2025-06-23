public class UIController {
    private MealLogger mealLogger;
    private SwapEngine swapEngine;
    private DataAnalyzer dataAnalyzer;
    private ChartVisualizer chartVisualizer;

    public UIController(MealLogger mealLogger, SwapEngine swapEngine,
                        DataAnalyzer dataAnalyzer, ChartVisualizer chartVisualizer) {
        this.mealLogger = mealLogger;
        this.swapEngine = swapEngine;
        this.dataAnalyzer = dataAnalyzer;
        this.chartVisualizer = chartVisualizer;
    }

    public String requestComparison(String mealId) {
        Meal original = mealLogger.getOriginalMeal(mealId);
        Meal swapped = swapEngine.getSwappedMeal(mealId);
        NutritionDiffData diff = dataAnalyzer.analyzeNutrition(original, swapped);
        return chartVisualizer.generateComparisonChart(diff);
    }
}

