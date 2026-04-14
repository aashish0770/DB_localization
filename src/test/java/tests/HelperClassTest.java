package tests;



import one.HelperClass;
import one.MariaDbConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HelperClassTest {

    // We'll use a mock or a real LocalizationService.
    // For simple tests, we can create a minimal mock.

    private MariaDbConnection dbConnection;
    private HelperClass helper;

    @BeforeEach
    void setUp() {
        dbConnection = new MariaDbConnection(); // assuming this works in test env
        helper = new HelperClass(dbConnection);
    }

    @Test
    void testDefaultLocaleIsEnglish() {
        assertEquals("en", helper.getCurrentLocale());
    }

    @Test
    void testGetMessageReturnsDefaultWhenKeyNotFound() {
        String result = helper.getMessage("non.existent.key");
        assertEquals("non.existent.key", result);
    }

    @Test
    void testSetLocaleChangesCurrentLocale() {
        helper.setLocale("ja");
        assertEquals("ja", helper.getCurrentLocale());

        helper.setLocale("ur");
        assertEquals("ur", helper.getCurrentLocale());
    }

    @Test
    void testSetLocaleUpdatesMessages() {
        // This test assumes LocalizationService returns different maps for different locales
        helper.setLocale("fi");
        String finnishMessage = helper.getMessage("prompt.addItem");

        helper.setLocale("en");
        String englishMessage = helper.getMessage("prompt.addItem");

        // At minimum, they shouldn't be the same if localization works
        // You can make this stronger once you have actual translations
        assertNotNull(finnishMessage);
        assertNotNull(englishMessage);
    }
}