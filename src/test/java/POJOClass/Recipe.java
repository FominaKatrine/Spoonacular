package POJOClass;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Recipe {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;

    private List<Nutrient> nutrients;


    @JsonProperty("nutrition")
    public void setNutrients(HashMap<String, List<HashMap<String, String>>> nutrition) {


        List<HashMap<String, String>> listNutrients = nutrition.get("nutrients");
        nutrients = new ArrayList<>();
        double amount;
        for (HashMap<String, String> item : listNutrients) {
            try {
                amount = Double.parseDouble(item.get("amount"));
            } catch (NumberFormatException e) {
                amount = 0.0;
            }
            nutrients.add(new Nutrient(item.get("name"), amount, item.get("unit")));
        }
    }

    @JsonSetter("calories")
    public void setCalories(int value) {
        if (nutrients == null) {
            nutrients = new ArrayList<>();
        }
        nutrients.add(new Nutrient("Calories", (double) value, "kkal"));
    }

    @JsonSetter("protein")
    public void setProtein(String value) {
        if (nutrients == null) {
            nutrients = new ArrayList<>();
        }
        double protein = 0.0;
        try {
            protein = Integer.parseInt(value, 0, value.length() - 1, 10);
        } finally {
            nutrients.add(new Nutrient("Protein", protein, "g"));
        }

    }

    @JsonSetter("fat")
    public void setFat(String value) {
        if (nutrients == null) {
            nutrients = new ArrayList<>();
        }
        double fat = 0.0;
        try {
            fat = Integer.parseInt(value, 0, value.length() - 1, 10);
        } finally {
            nutrients.add(new Nutrient("Fat", fat, "g"));
        }

    }

    @JsonSetter("carbs")
    public void setCarbs(String value) {
        if (nutrients == null) {
            nutrients = new ArrayList<>();
        }
        double carbs = 0.0;
        try {
            carbs = Integer.parseInt(value, 0, value.length() - 1, 10);
        } finally {
            nutrients.add(new Nutrient("Carbs", carbs, "g"));
        }

    }


    public List<Nutrient> getNutrients() {
        return nutrients;
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


}