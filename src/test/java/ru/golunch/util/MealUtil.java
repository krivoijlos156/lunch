package ru.golunch.util;

import ru.golunch.TestMatcher;
import ru.golunch.model.Meal;

import java.util.Arrays;
import java.util.List;

import static ru.golunch.model.AbstractBaseEntity.START_SEQ;
import static ru.golunch.util.RestaurantUtil.*;

public class MealUtil {

    public static final int MEAL1_ID = START_SEQ + 9;
    public static final int MEAL2_ID = START_SEQ + 10;
    public static final int MEAL3_ID = START_SEQ + 11;

    public static TestMatcher<Meal> MATCHER_MEAL = new TestMatcher<>();

    public static final Meal MEAL1 = new Meal(MEAL1_ID, "Каша1", REST1_ID, 200);
    public static final Meal MEAL2 = new Meal(MEAL2_ID, "Котлетка с пюре2", REST1_ID, 400);
    public static final Meal MEAL3 = new Meal(MEAL3_ID, "Борщ3", REST1_ID, 600);
    public static final Meal MEAL4 = new Meal(MEAL1_ID + 3, "Хлопья1", REST2_ID, 1100);
    public static final Meal MEAL5 = new Meal(MEAL1_ID + 4, "Бургер2", REST2_ID, 1900);
    public static final Meal MEAL6 = new Meal(MEAL1_ID + 5, "Wok3", REST2_ID, 1700);
    public static final Meal MEAL7 = new Meal(MEAL1_ID + 6, "Ряженка4", REST2_ID, 2000);
    public static final Meal MEAL8 = new Meal(MEAL1_ID + 7, "Мюсли1", REST3_ID, 100);
    public static final Meal MEAL9 = new Meal(MEAL1_ID + 8, "Солнка2", REST3_ID, 190);
    public static final Meal MEAL10 = new Meal(MEAL1_ID + 9, "Плов3", REST3_ID, 299);

    public static List<Meal> MEALS = Arrays.asList(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6, MEAL7, MEAL8, MEAL9, MEAL10);

    public static Meal getNew() {
        return new Meal(null, "New", REST1_ID, 5000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL1);
        updated.setName("UpdatedKotleta");
        updated.setPrice(60);
        return updated;
    }
}
