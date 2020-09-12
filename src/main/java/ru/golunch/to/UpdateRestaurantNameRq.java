package ru.golunch.to;


public class UpdateRestaurantNameRq extends AbstractDto{
    private String newName;


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
