package ru.golunch.service;

import ru.golunch.model.Vote;

import java.util.List;

public interface VoteService {

    public void update(Vote voteToday, int restId);

    public Vote create(int userId, int restId);

    public Vote getTodayForUser(int userId);

    public List<Vote> getAll();

    public List<Vote> getAllToday();

    public int countVotesForRestaurantToday(int restId);
}
