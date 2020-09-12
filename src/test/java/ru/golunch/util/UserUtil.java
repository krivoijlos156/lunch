package ru.golunch.util;

import ru.golunch.TestMatcher;
import ru.golunch.model.Role;
import ru.golunch.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.golunch.model.AbstractBaseEntity.START_SEQ;

public class UserUtil {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 5;

    public static TestMatcher<User> MATCHER_USER = TestMatcher.usingFieldsWithIgnoringComparator(User.class, "registered", "role");

    public static final User USER1 = new User(USER_ID, "User1", "user1@yandex.ru", "password", Role.USER);
    public static final User USER2 = new User(USER_ID + 1, "User2", "user2@yandex.ru", "password", Role.USER);
    public static final User USER3 = new User(USER_ID + 2, "User3", "user3@yandex.ru", "password", Role.USER);
    public static final User USER4 = new User(USER_ID + 3, "User4", "user4@yandex.ru", "password", Role.USER);
    public static final User USER5 = new User(USER_ID + 4, "User5", "user5@yandex.ru", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);

    public static final List<User> USERS = Arrays.asList(ADMIN, USER1, USER2, USER3, USER4, USER5);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setEmail("update@yandex.ru");
        updated.setRoles(Collections.singleton(Role.ADMIN));
        return updated;
    }
}