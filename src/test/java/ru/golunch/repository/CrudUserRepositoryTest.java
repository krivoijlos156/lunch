package ru.golunch.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.golunch.model.Role;
import ru.golunch.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.golunch.UserUtil.USER;
import static ru.golunch.UserUtil.USER_ID;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles("postgres")
class CrudUserRepositoryTest {

    @Autowired
    CrudUserRepository userRepository;

    @Test
    void save() {
        User expected = new User(null, "User", "user@mail.ru", "user", Role.USER);
        userRepository.save(expected);
        User actual = userRepository.findByEmail("user@mail.ru");
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "id", "registered", "roles");
    }

    @Test
    void delete() {
        userRepository.delete(USER_ID);
    }

    @Test
    void get() {
        User user = userRepository.findById(USER_ID).get();
        assertThat(user).isEqualToIgnoringGivenFields(USER, "registered", "roles");
    }

    @Test
    void getByEmail() {
        userRepository.findByEmail("user@yandex.ru");
    }

    @Test
    void getAll() {
        List<User> users = userRepository.findAll();
    }
}