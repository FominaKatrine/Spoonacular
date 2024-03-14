package POJOClass;

import com.fasterxml.jackson.annotation.*;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Recipe {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("calories")
    private int calories;
    @JsonProperty("carbs")
    private String carbs;
    @JsonProperty("fat")
    private String fat;
    @JsonProperty("protein")
    private String protein;
    @JsonProperty("nutrition")
    private Nutrition nutrition;


    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCarbs() {
        try {
            return Integer.parseInt(this.carbs, 0, this.carbs.length() - 1, 10);

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public int getFat() {
        try {
            return Integer.parseInt(this.fat, 0, this.fat.length() - 1, 10);

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public int getProtein() {
        try {
            return Integer.parseInt(this.protein, 0, this.protein.length() - 1, 10);

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return 0;
        }


    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }
}