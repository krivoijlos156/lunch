package ru.golunch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.golunch.TestMatcher;
import ru.golunch.model.Role;
import ru.golunch.model.User;
import ru.golunch.repository.CrudUserRepository;
import ru.golunch.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
class UserServiceTest extends AbstractServiceTest {
    public static TestMatcher<User> MATCHER_USER = new TestMatcher<>("registered", "role");
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    public User user1;

    @Autowired
    UserService service;

    @Autowired
    CrudUserRepository repository;


    @BeforeEach
    void init() {
        user1 = repository.findByEmail("user1@yandex.ru");
    }

    @Test
    void update() {
        user1.setEmail("up@yandex.ru");
        user1.setName("Up_User");
        service.update(user1);
        MATCHER_USER.assertMatch(service.get(user1.getId()), user1);
    }

    @Test
    void create() {
        User expected = new User("NEW", "NEW@yandex.ru", "password", Role.USER);
        User actual = service.create(expected);
        MATCHER_USER.assertMatch(actual, expected);
    }

    @Test
    void delete() {
        int i = repository.delete(user1.getId());
        assertThrows(NotFoundException.class, () -> service.get(user1.getId()));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(10));
    }

    @Test
    void get() {
        MATCHER_USER.assertMatch(service.get(user1.getId()), user1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(10));
    }

    @Test
    void getAll() {
        List<User> actual = service.getAll();
        MATCHER_USER.assertMatch(actual, repository.findAll(SORT_NAME_EMAIL));
    }

    @Test
    void getByEmail() {
        MATCHER_USER.assertMatch(user1, service.getByEmail(user1.getEmail()));
    }

    @Test
    void getByEmailNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByEmail("mmm"));
    }
}