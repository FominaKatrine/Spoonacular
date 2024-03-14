package MealPlanning;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * тесты для Meal Planning Day
 */
public class MealPlanningTest {
    private static String dataMealPlan;
    private static String pathResources = "./src/test/resources/json/";

    //  private static String dataMealPlanEmpty;

    @BeforeAll
    static void baseSetUp() {
        File file = new File(pathResources + "ingredient.json");
        String tstamp = JsonPath.given(file).get("date").toString();
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());
        dataMealPlan = sdf.format(Instant.ofEpochSecond(Long.parseLong(tstamp)));
    }

    @Test
    @DisplayName("Add one position in meal plan")
    void test() {
        MealPlanningPage mealPlanDay = new MealPlanningPage();
        mealPlanDay.addToMealPlan(getData("ingredient.json"));
        JsonPath res = mealPlanDay.getMealPlan(dataMealPlan);
        // почистить план
        mealPlanDay.clearMealPlanDay(dataMealPlan);
        //анализ результата
        System.out.println(res.prettify());

        // тесты для проверки полученного json файла, но это вариант неудобен. далее будем использовать POJO
        assertEquals(105.02f, res.getFloat("nutritionSummary.nutrients.find {n->n.name == \"Calories\"}.amount"));
        assertEquals(105.02f, res.getFloat("nutritionSummaryBreakfast.nutrients.find {n->n.name == \"Calories\"}.amount"));
        assertEquals(0.0f, res.getFloat("nutritionSummaryLunch.nutrients.find {n->n.name == \"Calories\"}.amount"));
        assertEquals(0.0f, res.getFloat("nutritionSummaryDinner.nutrients.find {n->n.name == \"Calories\"}.amount"));

    }




    //region Service methode

    /**
     * получение строки с запросом в json фрмате для добавления в планнер
     *
     * @return
     */
    private String getData(String nameResources) {
        //TODO
        Path json = Path.of(pathResources + nameResources);
        String data = "";
        try {
            data = Files.readString(json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    //endregion Service methode


}
