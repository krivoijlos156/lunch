package ru.golunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.golunch.model.Restaurant;
import ru.golunch.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.golunch.util.RestaurantUtil.*;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    RestaurantService service;

    @Test
    void create() {
        Restaurant actual = service.create(REST1);
        MATCHER_REST.assertMatch(actual, REST1);
    }

    @Test
    void update() {
        Restaurant update = getUpdated();
        service.update(update);
        MATCHER_REST.assertMatch(service.getWithMeals(REST1_ID), update);
    }

    @Test
    @Transactional
    void getWithMeals() {
        MATCHER_REST.assertMatch(service.getWithMeals(REST1_ID), REST1);
    }

//    @Test
//    void get() {
//        MATCHER_REST.assertMatch(service.get(REST1_ID), REST1);
//    }

    @Test
    void getAll() {
        List<Restaurant> rests = service.getAll();
        MATCHER_REST.assertMatch(rests, Arrays.asList(REST1, REST2, REST3));
    }

    @Test
    void getAllToday() {
        List<Restaurant> rests = service.getAllToday();
        MATCHER_REST.assertMatch(rests, Arrays.asList(REST1, REST2, REST3));
    }

    @Test
    void delete() {
        service.delete(REST1_ID);
        assertThrows(NotFoundException.class, () -> service.getWithMeals(REST1_ID));
    }
}