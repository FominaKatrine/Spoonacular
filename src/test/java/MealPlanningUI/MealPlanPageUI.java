package MealPlanningUI;

import com.codeborne.selenide.*;

import static com.codeborne.selenide.Selenide.*;

public class MealPlanPageUI {

    private static ElementsCollection mpSlot22Items = $$("div#calendar tbody tr td#d2s2 div.mealPlannerItem");
    private static SelenideElement mpSlot32 = $("div#calendar tbody tr td#d3s2");


    //logout


    void moveItem() {

        mpSlot32.should(Condition.visible);
        actions()
                .dragAndDrop(mpSlot22Items.should(CollectionCondition.sizeGreaterThan(0)).get(0), mpSlot32)
                .build().perform();

        System.out.println();
        //
    }


}



