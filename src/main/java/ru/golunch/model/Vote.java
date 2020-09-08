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

    private int userId;//todo remove

    public Vote() {
    }

    public Vote(Vote vote) {
        this(vote.id, vote.dateTime, vote.userId, vote.restaurant);
    }

    public Vote(Integer id, int userId, Restaurant restaurant) {
        this(id, LocalDateTime.now(), userId, restaurant);
    }

    public Vote(Integer id, LocalDateTime dateTime, int userId, Restaurant restaurant) {
        super(id);
        this.restaurant = restaurant;
        this.dateTime = dateTime;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "dateTime=" + dateTime +
//                ", restaurant=" + restaurant +
                ", userId=" + userId +
                '}';
    }
}