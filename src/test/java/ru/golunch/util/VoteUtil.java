package ru.golunch.util;

import ru.golunch.TestMatcher;
import ru.golunch.model.Vote;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.golunch.model.AbstractBaseEntity.START_SEQ;
import static ru.golunch.util.RestaurantUtil.*;
import static ru.golunch.util.UserUtil.*;

public class VoteUtil {

    public static final int VOTE1_ID = START_SEQ + 19;
    public static final int VOTE2_ID = START_SEQ + 20;
    public static final int VOTE3_ID = START_SEQ + 21;

    public static TestMatcher<Vote> MATCHER_VOTE = TestMatcher.usingFieldsWithIgnoringComparator(Vote.class, "registered");

    public static final Vote VOTE1 = new Vote(VOTE1_ID, USER1, REST1);
    public static final Vote VOTE2 = new Vote(VOTE2_ID, USER2, REST1);
    public static final Vote VOTE3 = new Vote(VOTE3_ID, USER3, REST2);
    public static final Vote VOTE4 = new Vote(VOTE1_ID + 3, USER4, REST3);
    public static final Vote OLD_VOTE5 = new Vote(VOTE1_ID + 4,  USER5, REST1);


    public static List<Vote> VOTES = Arrays.asList(VOTE1, VOTE2, VOTE3, VOTE4);

    public static Vote getNew() {
        return new Vote(USER5, REST1);
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(VOTE1);
        updated.setRestaurant(REST2);
        updated.setDateTime(LocalDateTime.now());
        return updated;
    }
}