package ru.golunch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.golunch.TestMatcher;
import ru.golunch.model.Meal;
import ru.golunch.model.Restaurant;
import ru.golunch.repository.CrudMealRepository;
import ru.golunch.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
class MealServiceTest extends AbstractServiceTest {
    public static TestMatcher<Meal> MATCHER_MEAL = TestMatcher.usingFieldsWithIgnoringComparator(Meal.class);
    public Meal meal1;

    @Autowired
    MealService service;

    @Autowired
    CrudMealRepository repository;


    @BeforeEach
    void init() {
        meal1 = repository.findByName("Завтрак, 1 рест");
    }

    @Test
    void update() {
        meal1.setPrice(1000);
        meal1.setName("Up_Rest");
        service.update(meal1);
        MATCHER_MEAL.assertMatch(service.get(meal1.getId()), meal1);
    }

    @Test
    void create() {
        Meal expected = new Meal("honey", new Restaurant("New Place", meal1), 600);
        Meal actual = service.create(expected);
        MATCHER_MEAL.assertMatch(actual, expected);
    }

    @Test
    void delete() {
        service.delete(meal1.getId());
        assertThrows(NotFoundException.class, () -> service.get(meal1.getId()));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(10));
    }

    @Test
    void get() {
        MATCHER_MEAL.assertMatch(service.get(meal1.getId()), meal1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(10));
    }

    @Test
    void getAll() {
        List<Meal> actual = service.getAll();
        MATCHER_MEAL.assertMatch(actual, repository.findAll());
    }
}