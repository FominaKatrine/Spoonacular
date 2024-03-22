package POJOClass;


import com.fasterxml.jackson.annotation.*;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Nutrient {

    @JsonProperty("name")
    private String name;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("unit")
    private String unit;

    public Nutrient(String name, Double amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return name + " = " + amount + ' ' + unit;
    }
}