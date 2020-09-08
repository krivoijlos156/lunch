package ru.golunch.util;

import ru.golunch.TestMatcher;
import ru.golunch.model.Vote;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.golunch.model.AbstractBaseEntity.START_SEQ;
import static ru.golunch.util.RestaurantUtil.*;
import static ru.golunch.util.UserUtil.USER_ID;

public class VoteUtil {

    public static final int VOTE1_ID = START_SEQ + 19;
    public static final int VOTE2_ID = START_SEQ + 20;
    public static final int VOTE3_ID = START_SEQ + 21;

    public static TestMatcher<Vote> MATCHER_VOTE = new TestMatcher<>("registered");

    public static final Vote VOTE1 = new Vote(VOTE1_ID, USER_ID, REST1_ID);
    public static final Vote VOTE2 = new Vote(VOTE2_ID, USER_ID + 1, REST1_ID);
    public static final Vote VOTE3 = new Vote(VOTE3_ID, USER_ID + 2, REST2_ID);
    public static final Vote VOTE4 = new Vote(VOTE1_ID + 3, USER_ID + 3, REST3_ID);
    public static final Vote VOTE5 = new Vote(VOTE1_ID + 4, USER_ID + 4, REST1_ID);


    public static List<Vote> VOTES = Arrays.asList(VOTE1, VOTE2, VOTE3, VOTE4, VOTE5);

    public static Vote getNew() {
        return new Vote(null, LocalDateTime.now(), USER_ID, REST1_ID);
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(VOTE1);
        updated.setRestId(REST2_ID);
        updated.setDateTime(LocalDateTime.now());
        return updated;
    }
}
