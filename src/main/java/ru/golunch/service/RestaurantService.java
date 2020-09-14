package ru.golunch.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.golunch.model.Restaurant;
import ru.golunch.repository.CrudRestaurantRepository;

import java.util.List;

import static ru.golunch.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final CrudRestaurantRepository repository;
    private static final Sort SORT_REGISTERED = Sort.by(Sort.Direction.ASC, "registered");

    public RestaurantService(CrudRestaurantRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @CacheEvict(value = "restaurant", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Transactional
    @CacheEvict(value = "restaurant", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Cacheable("restaurant")
    public List<Restaurant> getAll() {
        return repository.findAll(SORT_REGISTERED);
    }

    @Transactional
    @CacheEvict(value = "restaurant", allEntries = true)
    public void updateName(int id, String newName) {
        Restaurant restaurant = checkNotFoundWithId(repository.findById(id).orElse(null), id);
        restaurant.setName(newName);
        repository.save(restaurant);
    }
}