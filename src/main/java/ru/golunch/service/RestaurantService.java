package ru.golunch.service;

import ru.golunch.model.Restaurant;

import java.util.List;

public interface RestaurantService {

    public Restaurant create(Restaurant restaurant);

    public void delete(int id);

    public Restaurant get(int id);

    public List<Restaurant> getAll();

    public void updateName(int id, String newName);
}
