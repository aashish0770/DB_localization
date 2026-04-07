package one;

public class Item {
    double price;
    int quantity;

    public Item(double price, int quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotal() {
        return (int) getPrice() * getQuantity();
    }

}