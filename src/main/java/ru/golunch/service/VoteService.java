package ru.golunch.service;

import ru.golunch.model.Vote;

import java.util.List;

public interface VoteService {

    void update(Vote voteToday, int restId);

    Vote create(int userId, int restId);

    Vote getTodayForUser(int userId);

    List<Vote> getAll();

    List<Vote> getAllToday();

    int countVotesForRestaurantToday(int restId);
}
