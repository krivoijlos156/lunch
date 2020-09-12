package ru.golunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.golunch.model.Restaurant;
import ru.golunch.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.golunch.util.RestaurantUtil.*;

@Transactional
class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    RestaurantService service;


    @Test
    void create() {
        Restaurant expected = getNew();
        Restaurant actual = service.create(expected);
        MATCHER_REST.assertMatch(actual, expected);
    }

    @Test
    void delete() {
        service.delete(REST1_ID);
        assertThrows(NotFoundException.class, () -> service.get(REST1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(10));
    }

    @Test
    void get() {
        MATCHER_REST.assertMatch(service.get(REST1_ID), REST1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(10));
    }

    @Test
    void getAll() {
        List<Restaurant> actual = service.getAll();
        MATCHER_REST.assertMatch(actual, RESTS);
    }

    @Test
    void getAllToday() {
        Restaurant old = new Restaurant(null, "Old", LocalDate.now().minusDays(2));
        List<Restaurant> expected = service.getAll();
        service.create(old);
        List<Restaurant> actual = service.getAllToday();
        MATCHER_REST.assertMatch(actual, expected);
    }

    @Test
    void updateName() {
        Restaurant rest = getUpdated();
        service.updateName(REST1_ID, "UpdatedRest");
        MATCHER_REST.assertMatch(service.get(REST1_ID), rest);
    }
}