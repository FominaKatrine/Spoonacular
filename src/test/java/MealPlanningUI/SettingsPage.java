package MealPlanningUI;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SettingsPage {

    private static SelenideElement helloTxt = $("div#tagLine");
    private static SelenideElement mealPlannerLnk = $("a[href='/meal-planner']");
    private static SelenideElement articleLnk = $("a[href='articles']");
    private static SelenideElement titleDietsTxt = $("div.profileSettings h3");
    private static SelenideElement titleIntolerancesTxt = $("div.profileSettings h3");
    private static SelenideElement dietChoiceRadio = $("input[type='radio'][name='diet']");
    private static SelenideElement intolerancesChoiceChbx = $("input[type='checkbox']");




    public boolean isVisibleHello(String name) {
        return helloTxt.should(Condition.text("Hi, I'm fominakat :)")).exists();
    }


    public void getPlan() {

        mealPlannerLnk.should(Condition.visible).click();
    }

    public void getArticle() {
        articleLnk.should(Condition.visible).click();
    }
    // выбираем радио-баттон
    public void choiceDiet(String value){
        titleDietsTxt.should(Condition.text("Diet"));
//        dietChoiceRadio.should(Condition.visible).selectRadio(value);
        dietChoiceRadio.setValue("No Diet");
        dietChoiceRadio.setValue("Vegan");
    }

    public void helpDiet(){

    }

    public void choiceIntolerances(){
        titleIntolerancesTxt.should(Condition.text("Intolerances"));
        intolerancesChoiceChbx.find("[value='Dairy']").click();

    }
}
