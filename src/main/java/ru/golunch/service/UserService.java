package ru.golunch.service;

import ru.golunch.model.User;

import java.util.List;

public interface UserService {
    User create(User user);

    void update(User user);

    void delete(int id);

    User get(int id);

    User getByEmail(String email);

    List<User> getAll();
}
