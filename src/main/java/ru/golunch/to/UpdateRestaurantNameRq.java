package ru.golunch.to;


import java.beans.ConstructorProperties;

public class UpdateRestaurantNameRq extends AbstractDto {
    private String newName;

    @ConstructorProperties({"id", "newName"})
    public UpdateRestaurantNameRq(int id, String newName) {
        super(id);
        this.newName = newName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
