package ru.golunch.util;

import ru.golunch.model.Restaurant;
import ru.golunch.model.User;
import ru.golunch.model.Vote;
import ru.golunch.util.exception.AlreadyVotedException;

import java.time.LocalTime;

public class CheckTime {

    public static final LocalTime ELEVEN_OCLOCK = LocalTime.of(11, 00);

    public static Vote checkVote(Vote voteToday, int restId, int userId) {
        if (voteToday == null) {
            Restaurant restaurant = new Restaurant(restId, null, null);
            User user = new User(userId, null, null, null, null);
            Vote newVote = new Vote(user, restaurant);
            return newVote;
        } else if (voteToday.getDateTime().toLocalTime().isAfter(ELEVEN_OCLOCK)) {
            throw new AlreadyVotedException("already voted today");
        }
        return voteToday;
    }
}
