package ru.golunch.util;

import ru.golunch.model.Vote;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class CheckTime {

    public static final LocalTime ELEVEN_OCLOCK = LocalTime.of(11, 0);

    public static boolean assertVoteIsValid(LocalTime now) {
        return now.isAfter(ELEVEN_OCLOCK);
    }
}
