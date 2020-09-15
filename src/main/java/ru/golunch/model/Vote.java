package ru.golunch.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "vote")
public class Vote extends AbstractBaseEntity {

    @Column(name = "registered", nullable = false)
    @NotNull
    private LocalDateTime registered;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "rest_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Vote() {
    }

    public Vote(Vote vote) {
        this(vote.id, vote.registered, vote.user, vote.restaurant);
    }

    public Vote( User user, Restaurant restaurant) {
        this(null, LocalDateTime.now(), user, restaurant);
    }

    public Vote(Integer id, User user, Restaurant restaurant) {
        this(id, LocalDateTime.now(), user, restaurant);
    }

    public Vote(Integer id, LocalDateTime registered, User user, Restaurant restaurant) {
        super(id);
        this.restaurant = restaurant;
        this.registered = registered;
        this.user = user;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setRegistered(LocalDateTime dateTime) {
        this.registered = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "dateTime=" + registered +
                ", user=" + user +
                '}';
    }
}