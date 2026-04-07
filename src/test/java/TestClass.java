import one.Item;
import one.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestClass {

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


}

