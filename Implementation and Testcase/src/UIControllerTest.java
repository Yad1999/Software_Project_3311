import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class UIControllerTest {

    private UIController controller;
    private Map<String, Meal> mealMap;
    private Map<String, Meal> swapMap;

    @BeforeEach
    void setUp() {
        mealMap = new HashMap<>();
        swapMap = new HashMap<>();

        // 提供 MealLogger 实现
        MealLogger mealLogger = mealId -> mealMap.get(mealId);

        // 提供 SwapEngine 实现
        SwapEngine swapEngine = mealId -> swapMap.get(mealId);

        // 提供 DataAnalyzer 实现
        DataAnalyzer analyzer = (original, swapped) ->
                new NutritionDiffData(original.getCalories(), swapped.getCalories());

        // 提供 ChartVisualizer 实现
        ChartVisualizer visualizer = diff ->
                "Calories: " + diff.getOriginalCalories() + " -> " + diff.getSwappedCalories();

        controller = new UIController(mealLogger, swapEngine, analyzer, visualizer);
    }

    @Test
    void testComparison_basicCase() {
        mealMap.put("001", new Meal("001", "Apple", 80));
        swapMap.put("001", new Meal("001", "Banana", 100));
        String result = controller.requestComparison("001");
        assertEquals("Calories: 80 -> 100", result);
    }

    @Test
    void testComparison_sameCalories() {
        mealMap.put("002", new Meal("002", "Bread", 120));
        swapMap.put("002", new Meal("002", "Rice", 120));
        String result = controller.requestComparison("002");
        assertEquals("Calories: 120 -> 120", result);
    }

    @Test
    void testComparison_missingOriginalMeal() {
        swapMap.put("003", new Meal("003", "Fish", 150));
        assertThrows(NullPointerException.class, () -> controller.requestComparison("003"));
    }

    @Test
    void testComparison_missingSwappedMeal() {
        mealMap.put("004", new Meal("004", "Egg", 90));
        assertThrows(NullPointerException.class, () -> controller.requestComparison("004"));
    }

    @Test
    void testComparison_emptyId() {
        mealMap.put("", new Meal("", "Water", 0));
        swapMap.put("", new Meal("", "Juice", 60));
        String result = controller.requestComparison("");
        assertEquals("Calories: 0 -> 60", result);
    }

    @Test
    void testComparison_caseSensitiveId() {
        mealMap.put("A01", new Meal("A01", "Tofu", 110));
        swapMap.put("a01", new Meal("a01", "Chicken", 200));
        assertThrows(NullPointerException.class, () -> controller.requestComparison("A01"));
    }

    @Test
    void testComparison_negativeCalories() {
        mealMap.put("005", new Meal("005", "Mystery", -50));
        swapMap.put("005", new Meal("005", "Salad", 100));
        String result = controller.requestComparison("005");
        assertEquals("Calories: -50 -> 100", result);
    }

    @Test
    void testComparison_largeCalories() {
        mealMap.put("006", new Meal("006", "Burger", 10000));
        swapMap.put("006", new Meal("006", "Lettuce", 5));
        String result = controller.requestComparison("006");
        assertEquals("Calories: 10000 -> 5", result);
    }

    @Test
    void testComparison_nullId() {
        assertThrows(NullPointerException.class, () -> controller.requestComparison(null));
    }

    @Test
    void testComparison_whitespaceId() {
        mealMap.put("   ", new Meal("   ", "Milk", 60));
        swapMap.put("   ", new Meal("   ", "Soy Milk", 80));
        String result = controller.requestComparison("   ");
        assertEquals("Calories: 60 -> 80", result);
    }

    @Test
    void testComparison_idOnlyInSwap() {
        swapMap.put("007", new Meal("007", "Steak", 400));
        assertThrows(NullPointerException.class, () -> controller.requestComparison("007"));
    }

    @Test
    void testComparison_idOnlyInLogger() {
        mealMap.put("008", new Meal("008", "Soup", 150));
        assertThrows(NullPointerException.class, () -> controller.requestComparison("008"));
    }

    @Test
    void testComparison_complexMealNames() {
        mealMap.put("009", new Meal("009", "Spicy@Noodle&Soup!", 300));
        swapMap.put("009", new Meal("009", "PlainRice", 200));
        String result = controller.requestComparison("009");
        assertEquals("Calories: 300 -> 200", result);
    }
}
