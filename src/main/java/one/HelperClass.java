package one;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HelperClass {

    private String currentLocale = "en";
    private LocalizationService localizationService;
    private Map<String, String> messages;

    public HelperClass(MariaDbConnection dbConnection) {
        this.localizationService = new LocalizationService(dbConnection);
        messages = localizationService.getUIMessages("en");
    }

    public String getMessage(String key) {
        return messages.getOrDefault(key, key);
    }

    public void setLocale(String lang) {
        this.currentLocale = lang;
        messages = localizationService.getUIMessages(lang);
    }
    public String getCurrentLocale() {
        return currentLocale;
    }

}
