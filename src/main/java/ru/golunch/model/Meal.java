package ru.golunch.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@Table(name = "meals")
public class Meal extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 50000)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Meal() {
    }

    public Meal(Meal meal) {
        this(meal.id, meal.name, meal.price);
    }

    public Meal(String name, int price) {
        this(null, name, price);
    }

    public Meal(String name, Restaurant restaurant, int price) {
        this(null, name, price);
        this.restaurant = restaurant;
    }

    public Meal(int id, String name, Restaurant restaurant, int price) {
        this(id, name, price);
        this.restaurant = restaurant;
    }

    public Meal(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Meal{ " + name + ", " + price + '}';
    }
}
