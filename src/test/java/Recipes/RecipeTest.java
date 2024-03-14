package Recipes;

import POJOClass.Nutrient;
import POJOClass.Recipe;
import POJOClass.RecipeList;
import POJOClass.Taste;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * различные способы передачи данных в параметризованные тесты
 * различные варианты работы с JSON ответом: разные способы валидации ответа.
 */
public class RecipeTest extends AbstractTest {
    static RequestSpecification requestSpec;
    static ResponseSpecification responseSpec;
    private static final Logger logger = LoggerFactory.getLogger("testRecipe");

    @BeforeAll
    public static void baseSetUp() {

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(getUrl() + "recipes")
                .addQueryParam("apiKey", getApiKey())
                .addHeader("Content-Type", "application/json")
                .addFilter(new AllureRestAssured())
                .build();
        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
        logger.info("Общие настройки для тестов сформированы");
    }

    static Stream<Arguments> mapData() {
        HashMap <String, Number> pasta = new HashMap<>();
        pasta.put("maxCalories", 250.00);
        pasta.put("maxCarbs", 50.00);
        pasta.put("number", 5);

        HashMap <String, Number>  apple = new HashMap<>();
        apple.put("maxCalories", 150.00);
        apple.put("maxFat", 10.00);
        apple.put("number", 2);

        HashMap <String, Number>  chicken = new HashMap<>();
        chicken.put("maxCalories", 350.00);
        chicken.put("minProtein", 25.00);
        chicken.put("number", 5);

        return Stream.of(
                Arguments.of(pasta, "pasta"),
                Arguments.of(apple, "apple"),
                Arguments.of(chicken, "chicken"));

    }

    @ParameterizedTest
    @MethodSource("mapData")
    @DisplayName("Search Recipes")
    void searchRecipesTest(HashMap<String, Double> param, String query) {
        RecipeList recipeList = given()
                .spec(requestSpec)
                .queryParam("query", query)
                .queryParams(param)
                .when()
                .get("/complexSearch")
                .then()
                .spec(responseSpec)
                .assertThat()
                .body("results.title", everyItem(containsStringIgnoringCase(query)))
                .extract()
                .as(RecipeList.class);

        // формирование листа нутриентов
        List<Nutrient> nutrientList = recipeList.getRecipeList().stream()
                .map(x -> x.getNutrition().getNutrients())
                .flatMap(List::stream)
                .toList();

        System.out.println(nutrientList.toString());
        //проверим, что все нутриенты соответствуют переданным параметрам

        for (Map.Entry<String, Double> item : param.entrySet()) {
            if (item.getKey().startsWith("max")) {
                String nutrient = item.getKey().substring(3);
                assertThat(nutrientList.stream().filter(e -> e.getName().equals(nutrient)).toList(),
                        everyItem(hasProperty("amount", lessThanOrEqualTo(item.getValue()))));
            } else if (item.getKey().startsWith("min")) {
                String nutrient = item.getKey().substring(3);
                assertThat(nutrientList.stream().filter(e -> e.getName().equals(nutrient)).toList(),
                        everyItem(hasProperty("amount", greaterThanOrEqualTo(item.getValue()))));
            }

        }

    //TODO тесты с взаимоисключающими параметрами (будут позже)


    }


    @ParameterizedTest(name = "{0}: min={1}, max={2}")
    @CsvSource(value = {" Carbs,10, 50", "Fat, 50, 100"})
    @DisplayName("Search Recipes By Nutrients")
    void searchRecipesByNutrients(String nutrient, int minValue, int maxValue) throws JsonProcessingException {
        //TODO сделать параметриацию для "carbs": "33g", "fat": "10g",  "protein": "2g",
        //TODO калории  "calories": 226
        String response = given()
                .spec(requestSpec)
                .queryParams("min" + nutrient, minValue, "max" + nutrient, maxValue, "number", 2)
                .when()
                .get("/findByNutrients")
                .then().log().body()
                .spec(responseSpec)
                .extract()
                .asString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Recipe> recipeList = objectMapper.readValue(response, new TypeReference<>() {
        });
        //TODO доделать для остальных аргументов

        assertThat(recipeList, everyItem(hasProperty(nutrient.toLowerCase(), greaterThanOrEqualTo(minValue))));
        assertThat(recipeList, everyItem(hasProperty(nutrient.toLowerCase(), lessThanOrEqualTo(maxValue))));


    }

    @ParameterizedTest(name = "{index} -search: {arguments}")
    @ValueSource(strings = {"chick", "cheese", "pasta"})
    @DisplayName("Autocomplete Recipe Search")
    void autocompleteRecipeSearch(String query) {
        int number = 10;
        logger.info("поиск рецептов: 'Содержит в заголовке: '" + query + "'");
        given()
                .spec(requestSpec)
                .queryParam("query", query)
                .queryParam("number", number)
                .when()
                .get("/autocomplete")
                .then().spec(responseSpec)
                .assertThat()
                .body("title", everyItem(containsStringIgnoringCase(query)))
                .body("size()", lessThanOrEqualTo(number));
    }

    @Test
    @DisplayName("Equipment by ID")
    void equipmentByID() {
        String recipeID = "1003464";
        String[] equipment = {"pie form", "bowl", "oven", "frying pan"};
        given()
                .spec(requestSpec)
                .pathParam("id", recipeID)
                .when()
                .get("{id}/equipmentWidget.json")
                .then().log().body()
                .spec(responseSpec)
                .assertThat()
                .body("equipment", hasSize(equipment.length))
                .body("equipment.name", (containsInAnyOrder(equipment)));
    }

    @Test
    @DisplayName("Taste by ID")
    void tasteByID() {
        String id = "69095";
        Taste taste = given()
                .spec(requestSpec)
                .pathParam("id", id)
                .when()
                .get("{id}/tasteWidget.json")
                .then().log().body()
                .spec(responseSpec)
                .extract()
                .as(Taste.class);

        assertThat(taste.getBitterness(), equalTo(19.25));
    }


    @Test
    @DisplayName("Nutrition by ID")
    void nutritionByID() {
        int id = 1003464;
        given()
                .spec(requestSpec)
                .pathParam("id", id)
                .when()
                .get("{id}/nutritionWidget.json")
                .then()
                .spec(responseSpec)
                .assertThat()
                .body("nutrients.find{n->n.name == \"Calories\"}.amount", equalTo(899.16f))
                .body("nutrients.find{n->n.name == \"Sugar\"}.amount", equalTo(21.98f))
                .body("nutrients.find{n->n.name == \"Protein\"}.amount", equalTo(11.64f));
    }


}
