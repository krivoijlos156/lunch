package ru.golunch.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.golunch.model.User;
import ru.golunch.repository.CrudUserRepository;

import java.util.List;

import static ru.golunch.util.ValidationUtil.checkNotFound;
import static ru.golunch.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final CrudUserRepository userRepository;

    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    public UserService(CrudUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return userRepository.save(user);
    }

    @Transactional
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(userRepository.save(user), user.getId());
    }

    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(userRepository.delete(id), id);
    }

    public User get(int id) {
        return checkNotFoundWithId(userRepository.getOne(id), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userRepository.findByEmail(email), "email=" + email);
    }

    public List<User> getAll() {
        return userRepository.findAll(SORT_NAME_EMAIL);
    }
}