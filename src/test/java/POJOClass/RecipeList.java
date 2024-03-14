package POJOClass;

import java.util.List;

import com.fasterxml.jackson.annotation.*;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//        "results",
//        "offset",
//        "number",
//        "totalResults"
//})

public class RecipeList {

    @JsonProperty("results")
    private List<Recipe> recipes;


    public List<Recipe> getRecipeList() {
        return recipes;
    }

    public void setRecipeList(List<Recipe> recipes) {
        this.recipes = recipes;
    }

}