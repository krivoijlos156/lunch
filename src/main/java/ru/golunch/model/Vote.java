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

//    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
//    @JoinColumn (name="rest_id")
//    private Restaurant restaurant;

    @Column(name = "rest_id", nullable = false)
    private int restId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    public Vote() {
    }

    public Vote(Vote vote) {
        this(vote.id, vote.dateTime, vote.userId, vote.restId);
    }

    public Vote(Integer id, int userId, int restId) {
        this(id, LocalDateTime.now(), userId, restId);
    }

    public Vote(Integer id,  LocalDateTime dateTime, int userId, int restId) {
        super(id);
//        this.restaurant = restaurant;
        this.restId=restId;
        this.dateTime = dateTime;
        this.userId = userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getRestId() {
        return restId;
    }

    public void setRestId(int restId) {
        this.restId = restId;
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

//    public Restaurant getRest() {
//        return restaurant;
//    }
//
//    public void setRest(Restaurant rest) {
//        this.restaurant = rest;
//    }

    @Override
    public String toString() {
        return "Vote{" +
                "dateTime=" + dateTime +
//                ", restaurant=" + restaurant +
                ", userId=" + userId +
                '}';
    }
}
