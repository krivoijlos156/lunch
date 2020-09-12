package ru.golunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.golunch.model.User;
import ru.golunch.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.golunch.util.UserUtil.*;

@Transactional
class UserServiceTest extends AbstractServiceTest {

    @Autowired
    UserService service;

    @Test
    void update() {
        User expected = getUpdated();
        service.update(expected);
        MATCHER_USER.assertMatch(service.get(USER_ID), expected);
    }

    @Test
    void create() {
        User expected = getNew();
        User actual = service.create(expected);
        MATCHER_USER.assertMatch(actual, expected);
    }

    @Test
    void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(10));
    }

    @Test
    void get() {
        MATCHER_USER.assertMatch(service.get(USER_ID), USER1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(10));
    }

    @Test
    void getAll() {
        List<User> actual = service.getAll();
        MATCHER_USER.assertMatch(actual, USERS);
    }

    @Test
    void getByEmail() {
        MATCHER_USER.assertMatch(USER1, service.getByEmail(USER1.getEmail()));
    }

    @Test
    void getByEmailNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByEmail("mmm"));
    }
}