package POJOClass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanDay {
    @JsonProperty("nutritionSummary")
    private List<Nutrient> nutritionSummary;

    @JsonProperty("nutritionSummaryBreakfast")
    private List<Nutrient> nutritionSummaryBreakfast;
    @JsonProperty("nutritionSummaryLunch")
    private List<Nutrient> nutritionSummaryLunch;
    @JsonProperty("nutritionSummaryDinner")
    private List<Nutrient> nutritionSummaryDinner;

    public PlanDay() {
    }

    public List<Nutrient> getNutritionSummary() {
        return nutritionSummary;
    }

    public void setNutritionSummary(List<Nutrient> nutritionSummary) {
        this.nutritionSummary = nutritionSummary;
    }

    public List<Nutrient> getNutritionSummaryBreakfast() {
        return nutritionSummaryBreakfast;
    }

    public void setNutritionSummaryBreakfast(List<Nutrient> nutritionSummaryBreakfast) {
        this.nutritionSummaryBreakfast = nutritionSummaryBreakfast;
    }

    public List<Nutrient> getNutritionSummaryLunch() {
        return nutritionSummaryLunch;
    }

    public void setNutritionSummaryLunch(List<Nutrient> nutritionSummaryLunch) {
        this.nutritionSummaryLunch = nutritionSummaryLunch;
    }

    public List<Nutrient> getNutritionSummaryDinner() {
        return nutritionSummaryDinner;
    }

    public void setNutritionSummaryDinner(List<Nutrient> nutritionSummaryDinner) {
        this.nutritionSummaryDinner = nutritionSummaryDinner;
    }
}
