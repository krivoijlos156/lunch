package ru.golunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.golunch.model.Meal;
import ru.golunch.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.golunch.util.MealUtil.*;

@Transactional
class MealServiceTest extends AbstractServiceTest {

    @Autowired
    MealService service;


    @Test
    void update() {
        Meal expected = getUpdated();
        service.update(expected);
        MATCHER_MEAL.assertMatch(service.get(MEAL1_ID), expected);
    }

    @Test
    void create() {
        Meal expected = getNew();
        Meal actual = service.create(expected);
        MATCHER_MEAL.assertMatch(actual, expected);
    }

    @Test
    void delete() {
        service.delete(MEAL1_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(10));
    }

    @Test
    void get() {
        MATCHER_MEAL.assertMatch(service.get(MEAL1_ID), MEAL1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(10));
    }

    @Test
    void getAll() {
        List<Meal> actual = service.getAll();
        MATCHER_MEAL.assertMatch(actual, MEALS);
    }
}