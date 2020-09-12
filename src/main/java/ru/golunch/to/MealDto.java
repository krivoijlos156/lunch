package ru.golunch.to;

public class MealDto extends AbstractDto{

    private String name;
    private int price;

    public MealDto() {
    }

    public MealDto(int id, String name, int price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
