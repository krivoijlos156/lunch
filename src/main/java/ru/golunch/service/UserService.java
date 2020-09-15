package ru.golunch.service;

import ru.golunch.model.User;

import java.util.List;

public interface UserService {
    public User create(User user);

    public void update(User user);

    public void delete(int id);

    public User get(int id);

    public User getByEmail(String email);

    public List<User> getAll();
}
