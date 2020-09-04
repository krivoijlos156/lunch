package ru.golunch.model;

public class Meal extends AbstractNamedEntity {

    private int price;

    public Meal() {
    }

    public Meal(Integer id, String name, int price, String description) {
        super(id, name);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Meal{ " + name + ", " + price + '}';
    }
}
