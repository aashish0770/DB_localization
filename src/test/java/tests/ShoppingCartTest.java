package tests;

import one.Item;
import one.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ShoppingCartTest {

    ShoppingCart shop;

    @BeforeEach
    void setUp() {
        shop = new ShoppingCart();
    }

    @Test
    void testTotalBill() {
        shop.addItem("apple", new Item(2, 3));
        shop.addItem("banana", new Item(1, 5));
        shop.addItem("milk", new Item(3, 2));
        assertEquals(17, shop.getTotalBill());
    }

    @Test
    void testTotalBillEmptyshop() {
        assertEquals(0, shop.getTotalBill());
    }

    @Test
    void testTotalBillAfterRemove() {
        shop.addItem("apple", new Item(2, 3));
        shop.addItem("banana", new Item(1, 5));
        shop.removeItem("apple");
        assertEquals(5, shop.getTotalBill());
    }

    @Test
    void testAddToCart() {
        shop.addItem("apple", new Item(2, 3));
        shop.addItem("banana", new Item(1, 5));
        assertTrue(shop.getItems().containsKey("apple"));
        assertTrue(shop.getItems().containsKey("banana"));
    }

    @Test
    void testRemoveItem() {
        shop.addItem("apple", new Item(2, 3));
        shop.removeItem("apple");
        assertFalse(shop.getItems().containsKey("apple"));

    }

    @Test
    void getItemTest() {
        shop.addItem("apple", new Item(2, 3));
        shop.addItem("banana", new Item(1, 5));
        shop.addItem("milk", new Item(3, 2));
        assertEquals(3, shop.getItems().size());
    }

    @Test
    void testDisplayBill() {
        shop.addItem("apple", new Item(2, 3));   // total = 6
        shop.addItem("banana", new Item(1, 5));  // total = 5

        String bill = shop.displayBill();

        assertTrue(bill.contains("apple: 6"));
        assertTrue(bill.contains("banana: 5"));
        assertTrue(bill.contains("| Total: 11"));
    }
}
