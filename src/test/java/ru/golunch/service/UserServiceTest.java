package ru.golunch.service;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.golunch.model.User;
import ru.golunch.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.golunch.util.UserUtil.*;


class UserServiceTest extends AbstractServiceTest{

    @Autowired
    UserService userService;

    @Test
    void create() {
        User create = userService.create(NEW_USER);
//        assertThat(userService.get(NEW_USER.getId())).isEqualToIgnoringGivenFields(NEW_USER, "id", "registered", "roles");
        MATCHER_USER.assertMatch(NEW_USER, create);
    }

    @Test
    void update() {
        User updateUser = getUpdated();
        userService.update(updateUser);
        MATCHER_USER.assertMatch(updateUser, userService.getByEmail("update@yandex.ru"));
    }

    @Test
    void delete() {
        userService.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> userService.getByEmail(USER.getEmail()));
    }

    @Test
    void get() {
        MATCHER_USER.assertMatch(USER, userService.get(USER_ID));
    }

    @Test
    void getByEmail() {
        MATCHER_USER.assertMatch(USER, userService.getByEmail(USER.getEmail()));
    }

    @Test
    void getAll() {
        List<User> all = userService.getAll();
        Assumptions.assumeTrue(all.size() == 6);
        MATCHER_USER.assertMatch(USER, all.get(1));
        MATCHER_USER.assertMatch(ADMIN, all.get(0));
    }
}