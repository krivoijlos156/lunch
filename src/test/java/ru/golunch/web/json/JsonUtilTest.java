package ru.golunch.web.json;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.golunch.TestMatcher;
import ru.golunch.model.User;
import ru.golunch.service.UserService;


@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles("hsqldb")
class JsonUtilTest {

    @Autowired
    UserService service;

    @Test
    void readWriteValue() throws Exception {
        User byEmail = service.getByEmail("user1@yandex.ru");
        String json = JsonUtil.writeValue(byEmail);
        System.out.println(json);
        User user = JsonUtil.readValue(json, User.class);
        TestMatcher<User> matcher = TestMatcher.usingFieldsWithIgnoringComparator(User.class, "registered", "role");
        matcher.assertMatch(user, byEmail);
    }
}