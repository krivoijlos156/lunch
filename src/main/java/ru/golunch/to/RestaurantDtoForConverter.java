package ru.golunch.to;

import java.time.LocalDate;
import java.util.List;

public class RestaurantDtoForConverter extends AbstractDto{
    private String name;
    private LocalDate registered;
    private List<MealDto> meals;

    public RestaurantDtoForConverter() {
    }

    public RestaurantDtoForConverter(Integer id, String name, LocalDate registered, List<MealDto> meals) {
        super(id);
        this.name = name;
        this.registered = registered;
        this.meals = meals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    public List<MealDto> getMeals() {
        return meals;
    }

    public void setMeals(List<MealDto> meals) {
        this.meals = meals;
    }
}
