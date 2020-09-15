package ru.golunch.service;

import ru.golunch.model.Meal;

import java.util.List;

public interface MealService {

    void update(Meal meal);

    Meal create(Meal meal);

    void delete(int id);

    Meal get(int id);

    List<Meal> getAll();
}
