package MealPlanningUI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class MealPlanTestUI extends AbstractTest {

    private static String login = "e.fomina.smr@mail.ru";
    private static String password = "password";


    @BeforeEach
    void init() {
        open(getBaseUrl());
    }

    @Test
    void test() {
        HomePage homePage = new HomePage();
        homePage.login(login, password);

        SettingsPage settingsPage = new SettingsPage();

        settingsPage.choiceDiet("Vegan");
        settingsPage.choiceIntolerances();

        System.out.println();

    }

}
