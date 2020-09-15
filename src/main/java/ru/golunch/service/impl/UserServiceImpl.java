package ru.golunch.service.impl;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.golunch.model.User;
import ru.golunch.model.UserDetailsImpl;
import ru.golunch.repository.CrudUserRepository;
import ru.golunch.service.UserService;

import java.util.List;

import static ru.golunch.util.ValidationUtil.checkNotFound;
import static ru.golunch.util.ValidationUtil.checkNotFoundWithId;

@Service("UserService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService, UserService {

    private final CrudUserRepository userRepository;

    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    public UserServiceImpl(CrudUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(userRepository.save(user), user.getId());
    }

    @Override
    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(userRepository.delete(id) != 0, id);
    }

    @Override
    public User get(int id) {
        return checkNotFoundWithId(userRepository.findById(id).orElse(null), id);
    }

    @Override
    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userRepository.findByEmail(email), "email=" + email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll(SORT_NAME_EMAIL);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User byEmail = this.getByEmail(email);
        return new UserDetailsImpl(byEmail);
    }
}