package ru.golunch.to;

import java.beans.ConstructorProperties;

public class RestaurantDto extends AbstractDto {
    private String name;


    @ConstructorProperties({"id", "name"})
    public RestaurantDto(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
