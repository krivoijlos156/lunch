package ru.golunch.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "vote")
public class Vote extends AbstractBaseEntity {

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "rest_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Vote() {
    }

    public Vote(Vote vote) {
        this(vote.id, vote.dateTime, vote.user, vote.restaurant);
    }

    public Vote( User user, Restaurant restaurant) {
        this(null, LocalDateTime.now(), user, restaurant);
    }

    public Vote(Integer id, User user, Restaurant restaurant) {
        this(id, LocalDateTime.now(), user, restaurant);
    }

    public Vote(Integer id, LocalDateTime dateTime, User user, Restaurant restaurant) {
        super(id);
        this.restaurant = restaurant;
        this.dateTime = dateTime;
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
                "dateTime=" + dateTime +
//                ", restaurant=" + restaurant +
                ", user=" + user +
                '}';
    }
}