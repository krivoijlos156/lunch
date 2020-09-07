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

    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn (name="rest_id")
    private Restaurant restaurant;

    @Column(name = "user_id", nullable = false)
    private int userId;

    public Vote() {
    }

    public Vote(int id, Restaurant restaurant, LocalDateTime dateTime, int userId) {
        super(id);
        this.restaurant = restaurant;
        this.dateTime = dateTime;
        this.userId = userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
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

    public Restaurant getRest() {
        return restaurant;
    }

    public void setRest(Restaurant rest) {
        this.restaurant = rest;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "dateTime=" + dateTime +
                ", restaurant=" + restaurant +
                ", userId=" + userId +
                '}';
    }
}
