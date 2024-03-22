package MealPlanningUI;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class HomePage {
    private static SelenideElement startNowBtn = $("a#startNowButton");
    private static SelenideElement signInForm = $("div#signIn");
    private static SelenideElement userNameField = $("input[name='email']");
    private static SelenideElement passwordField = $("input[type='password']");
    private static SelenideElement submitBtn = $("button[type='submit']");

    private static SelenideElement welcomeModal = $("div#welcomeModal");
    private static SelenideElement skip = $("div b.skip");


    public void login(String login, String password){
        startNowBtn.should(Condition.visible).click();
        signInForm.should(Condition.text("Sign In Chef"));
        userNameField.should(Condition.visible).setValue(login);
        passwordField.should(Condition.visible).setValue(password);
        submitBtn.should(Condition.visible).click();

        if (welcomeModal.should(Condition.visible).exists()){
            skip.should(Condition.visible).click();
        }
    }
}
