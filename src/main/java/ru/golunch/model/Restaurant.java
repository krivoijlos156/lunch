package ru.golunch.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate registered;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "restaurant")
    private List<Meal> meals;

    public Restaurant() {
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.id, restaurant.name, restaurant.meals);
    }

    public Restaurant(Integer id, String name, List<Meal> meals) {
        this(id, name, LocalDate.now(), meals);
    }

    public Restaurant(Integer id, String name, LocalDate date, List<Meal> meals) {
        super(id, name);
        this.registered = date;
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "meals=" + meals +
                '}';
    }
}