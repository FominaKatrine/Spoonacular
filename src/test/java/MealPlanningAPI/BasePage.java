package MealPlanningAPI;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * класс для получения данных для доступа к API (username, hash, apiKey). Читаем из файла "spoona.properties"
 */
public class BasePage {
    private static String url;
    private static String apiKey;
    private static String hash;
    private static String user;

    public BasePage() {
        Properties properties = new Properties();
        try (FileInputStream dataFile = new FileInputStream("src/test/resources/spoona.properties")) {
            properties.load(dataFile);
            url = properties.getProperty("url");
            apiKey = properties.getProperty("apiKey");
            hash = properties.getProperty("hash_spoo");
            user = properties.getProperty("username");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static String getUrl() {
        return url;
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getHash() {
        return hash;
    }

    public static String getUser() {
        return user;
    }
}
