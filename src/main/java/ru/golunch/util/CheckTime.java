package ru.golunch.util;

import java.time.LocalTime;

public class CheckTime {

    public static final LocalTime ELEVEN_OCLOCK = LocalTime.of(11, 0);

    public static boolean assertVoteIsValid(LocalTime now) {
        return now.isBefore(ELEVEN_OCLOCK);
    }
}
