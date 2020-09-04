package ru.golunch.model;

import java.time.LocalDateTime;

public class Vote extends AbstractBaseEntity {

    private LocalDateTime dateTime;

    private int restId;

    private int userId;

    public Vote() {
    }

    public Vote(int id, int restId, LocalDateTime dateTime, int userId) {
        super(id);
        this.restId = restId;
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

    public int getRestId() {
        return restId;
    }

    public void setRestId(int restId) {
        this.restId = restId;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "dateTime=" + dateTime +
                ", userId=" + userId +
                '}';
    }
}
