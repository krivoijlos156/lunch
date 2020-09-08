package ru.golunch.util;

import ru.golunch.TestMatcher;
import ru.golunch.model.Role;
import ru.golunch.model.User;

import java.util.Collections;
import java.util.Date;

import static ru.golunch.model.AbstractBaseEntity.START_SEQ;

public class UserUtil {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 5;

    public static TestMatcher<User> MATCHER_USER = new TestMatcher<>("registered", "role");

    public static final User USER = new User(USER_ID, "User1", "user1@yandex.ru", "password", Role.USER);
    public static final User NEW_USER = new User(USER_ID + 25, "User100", "user100@yandex.ru", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setEmail("update@yandex.ru");
        updated.setRoles(Collections.singleton(Role.ADMIN));
        return updated;
    }
}
