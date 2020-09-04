package ru.golunch.model;

import java.util.Set;

public class Restaurant extends AbstractNamedEntity{

    private Set<Meal> meals;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, Set<Meal> meals) {
        super(id, name);
        this.meals = meals;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "meals=" + meals +
                '}';
    }
}
