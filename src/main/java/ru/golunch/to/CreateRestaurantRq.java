package ru.golunch.to;

import java.beans.ConstructorProperties;

public class CreateRestaurantRq extends AbstractDto {
    private String name;


    @ConstructorProperties({"id", "name"})
    public CreateRestaurantRq(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
