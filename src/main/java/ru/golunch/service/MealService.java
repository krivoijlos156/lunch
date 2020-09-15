package ru.golunch.service;

import ru.golunch.model.Meal;

import java.util.List;

public interface MealService {

    public void update(Meal meal);

    public Meal create(Meal meal);

    public void delete(int id);

    public Meal get(int id);

    public List<Meal> getAll();
}
