package ru.golunch.util;

import ru.golunch.TestMatcher;
import ru.golunch.model.Restaurant;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static ru.golunch.model.AbstractBaseEntity.START_SEQ;

public class RestaurantUtil {

    public static final int REST1_ID = START_SEQ + 6;
    public static final int REST2_ID = START_SEQ + 7;
    public static final int REST3_ID = START_SEQ + 8;

    public static TestMatcher<Restaurant> MATCHER_REST = TestMatcher.usingFieldsWithIgnoringComparator(Restaurant.class, "registered", "meals");

    public static final Restaurant REST1 = new Restaurant(REST1_ID, "Жиденький");
    public static final Restaurant REST2 = new Restaurant(REST2_ID, "Мажор сити");
    public static final Restaurant REST3 = new Restaurant(REST3_ID, "Реальный пацан");

    public static final List<Restaurant> RESTS = Arrays.asList(REST1, REST2, REST3);

    public static Restaurant getNew() {
        return new Restaurant(null, "NewRest");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(REST1);
        updated.setName("UpdatedRest");
        updated.setRegistered(LocalDate.now());
        return updated;
    }
}