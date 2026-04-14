package tests;

import one.Local;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class LocalTest {

    @Test
    void testGetBundleEnglish() {
        ResourceBundle bundle = Local.getBundle("en");
        assertNotNull(bundle);
    }

    @Test
    void testGetBundleFinnish() {
        ResourceBundle bundle = Local.getBundle("fi");
        assertNotNull(bundle);
    }

    @Test
    void testBundleContainsKey() {
        ResourceBundle bundle = Local.getBundle("en");
        assertTrue(bundle.containsKey("prompt.itemName"));
    }
}