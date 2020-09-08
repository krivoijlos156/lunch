package ru.golunch.util;

import ru.golunch.TestMatcher;
import ru.golunch.model.Restaurant;
import ru.golunch.model.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

import static ru.golunch.model.AbstractBaseEntity.START_SEQ;
import static ru.golunch.util.MealUtil.*;

public class RestaurantUtil {

    public static final int REST1_ID = START_SEQ+6;
    public static final int REST2_ID = START_SEQ+7;
    public static final int REST3_ID = START_SEQ+8;

    public static TestMatcher<Restaurant> MATCHER_REST= new TestMatcher<>("registered", "votes");

    public static final Restaurant REST1 = new Restaurant(REST1_ID, "Жиденький",
            MEALS.stream().filter(meal -> meal.getRestId()==REST1_ID).collect(Collectors.toList()));
    public static final Restaurant REST2 = new Restaurant(REST2_ID, "Мажор сити",
            MEALS.stream().filter(meal -> meal.getRestId()==REST2_ID).collect(Collectors.toList()));
    public static final Restaurant REST3 = new Restaurant(REST3_ID, "Реальный пацан",
            MEALS.stream().filter(meal -> meal.getRestId()==REST3_ID).collect(Collectors.toList()));


    public static Restaurant getNew() {
        return new Restaurant(null, "NewRest", Arrays.asList(MEAL1,MEAL3));
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(REST1);
        updated.setName("UpdatedRest");
        updated.setRegistered(LocalDate.now());
        return updated;
    }
}
