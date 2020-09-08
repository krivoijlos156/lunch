package ru.golunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.golunch.model.Meal;
import ru.golunch.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.golunch.util.MealUtil.*;

class MealServiceTest extends AbstractServiceTest {

    @Autowired
    MealService service;

    @Test
    void update() {
        Meal update = getUpdated();
        service.update(update);
        MATCHER_MEAL.assertMatch(service.get(MEAL1_ID), update);
    }

    @Test
    void create() {
        Meal actual = service.create(MEAL1);
        MATCHER_MEAL.assertMatch(actual, MEAL1);
    }

    @Test
    void delete() {
        service.delete(MEAL1_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID));
    }

    @Test
    void get() {
        MATCHER_MEAL.assertMatch(service.get(MEAL1_ID), MEAL1);
    }

    @Test
    void getAll() {
        List<Meal> rests = service.getAll();
        MATCHER_MEAL.assertMatch(rests, MEALS);
    }
}