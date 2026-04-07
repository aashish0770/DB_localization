package one;

import java.util.HashMap;

public class ShoppingCart {

    HashMap<String, Item> cart;

    public ShoppingCart() {
        this.cart = new HashMap<>();
    }

    public void addItem(String name, Item item) {
        cart.put(name, item);
    }

    public void removeItem(String name) {
        cart.remove(name);
    }

    public HashMap<String, Item> getCart() {
        return cart;
    }

    public int totalItems() {
        int total = 0;
        return total = cart.size();
    }

    // just in case for printing
    public HashMap<String, Item> getItems() {
        return cart;
    }

    public int getTotalBill() {
        int totalBill = 0;
        for (Item i : cart.values()) {
            totalBill += i.getTotal();
        }
        return totalBill;
    }

    public String displayBill() {
        StringBuilder sb = new StringBuilder();
        for (HashMap.Entry<String, Item> entry : cart.entrySet()) {
            sb.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue().getTotal())
                    .append("  ");
        }
        sb.append("| Total: ").append(getTotalBill());
        return sb.toString();
    }

}

