package ru.golunch.to;

import java.beans.ConstructorProperties;

public class MealDto extends AbstractDto {

    private String name;
    private int price;

    @ConstructorProperties({"id", "name", "price"})
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
}