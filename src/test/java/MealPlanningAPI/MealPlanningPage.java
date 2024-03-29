package MealPlanningAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * методы для тестирование раздела Meal Planning Day (план на день)
 */
public class MealPlanningPage extends BasePage {
    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;

    public MealPlanningPage() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(getUrl() + "mealplanner/" + getUser())
                .addQueryParam("apiKey", getApiKey())
                .addQueryParam("hash", getHash())
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }


    public void addToMealPlan(String data) {
        given()
                .spec(requestSpec)
                .body(data)
                .when()
                .post("/items")
                .then()
                .spec(responseSpec)
                .assertThat()
                .body("status", equalTo("success"));
    }


    public JsonPath getMealPlan(String dataMealPlan) {
        return given()
                .spec(requestSpec)
                .pathParam("date", dataMealPlan)
                .when()
                .get("/day/{date}/")
                .then()
                .spec(responseSpec)
                .extract()
                .jsonPath();
    }


    public void clearMealPlanDay(String dataMealPlan) {
        given()
                .spec(requestSpec)
                .pathParam("date", dataMealPlan)
                .when()
                .delete("/day/{date}/")
                .then()
                .spec(responseSpec)
                .assertThat()
                .body("status", equalTo("success"));
    }

    public void deleteItemMealPlanDay(int id) {
        given()
                .spec(requestSpec)
                .pathParam("id", id)
                .when()
                .delete("/items/{id}/")
                .then().log().body()
                .spec(responseSpec);

    }


    public void getMealPlanDayEmpty(String dataMealPlanEmpty) {
        given()
                .spec(requestSpec)
                .pathParam("date", dataMealPlanEmpty)
                .when()
                .get("/day/{date}/")
                .then()
                .statusCode(400)
                .assertThat()
                .body("status", equalTo("failure"))
                .body("code", equalTo(400))
                .body("message", equalTo("No meals planned for that day"));
    }


}