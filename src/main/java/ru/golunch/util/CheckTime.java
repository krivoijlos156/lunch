package ru.golunch.util;

import ru.golunch.model.Vote;

import java.time.LocalTime;

public class CheckTime {

    public static final LocalTime ELEVEN_OCLOCK = LocalTime.of(11, 0);

    public static boolean assertVoteIsValid(Vote voteToday) {
        return voteToday.getDateTime().toLocalTime().isAfter(ELEVEN_OCLOCK);
    }
}
