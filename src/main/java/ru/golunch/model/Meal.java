package ru.golunch.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "rest_id"}, name = "meals_unique_name_rest_id_idx")})
public class Meal extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 50000)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id")
    private Restaurant restaurant;

    public Meal() {
    }

    public Meal(Meal meal) {
        this(meal.id, meal.name, meal.restaurant, meal.price);
    }

    public Meal(String name, Restaurant restaurant, int price) {
        this(null, name,restaurant,price);
    }

    public Meal(Integer id, String name, Restaurant restaurant, int price) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
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
