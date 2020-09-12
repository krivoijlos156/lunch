package ru.golunch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.golunch.TestMatcher;
import ru.golunch.model.Meal;
import ru.golunch.model.Restaurant;
import ru.golunch.repository.CrudRestaurantRepository;
import ru.golunch.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
class RestaurantServiceTest extends AbstractServiceTest {
    public static TestMatcher<Restaurant> MATCHER_REST = TestMatcher.usingFieldsWithIgnoringComparator(Restaurant.class, "registered");
    private static final Sort SORT_REGISTERED = Sort.by(Sort.Direction.ASC, "registered");
    public Restaurant rest1;

    @Autowired
    RestaurantService service;

    @Autowired
    CrudRestaurantRepository repository;


    @BeforeEach
    void init() {
        rest1 = repository.findByName("Жиденький");
    }

    @Test
    void create() {
        Restaurant expected = new Restaurant("New", new Meal("Juju", rest1, 1000));
        Restaurant actual = service.create(expected);
        MATCHER_REST.assertMatch(actual, expected);
    }

    @Test
    void delete() {
        service.delete(rest1.getId());
        assertThrows(NotFoundException.class, () -> service.get(rest1.getId()));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(10));
    }

    @Test
    void get() {
        MATCHER_REST.assertMatch(service.get(rest1.getId()), rest1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(10));
    }

    @Test
    void getAll() {
        List<Restaurant> actual = service.getAll();
        MATCHER_REST.assertMatch(actual, repository.findAll(SORT_REGISTERED));
    }

    @Test
    void getAllToday() {
        Restaurant old = new Restaurant(null, "Old", LocalDate.now().minusDays(2), List.of(new Meal("Juju", rest1, 1000)));
        List<Restaurant> expected = repository.findAll();
        repository.save(old);
        List<Restaurant> actual = service.getAllToday();
        MATCHER_REST.assertMatch(actual, expected);
    }

    @Test
    void updateName() {
        rest1.setName("Update");
        service.updateName(rest1.getId(), "Update");
        MATCHER_REST.assertMatch(service.get(rest1.getId()), rest1);
    }
}