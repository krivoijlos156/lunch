package ru.golunch.util;

import ru.golunch.model.Vote;

import java.time.LocalDate;

public class CheckTime {
    static public Vote checkToday(Vote vote) {
        return vote.getDateTime().toLocalDate().equals(LocalDate.now()) ? vote : null;
    }
}
