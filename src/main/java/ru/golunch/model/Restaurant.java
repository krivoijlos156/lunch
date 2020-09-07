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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rest_id")
    private List<Meal> meals;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, List<Meal> meals) {
        super(id, name);
        this.registered = LocalDate.now();
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void listMeals(List<Meal> meals) {
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
