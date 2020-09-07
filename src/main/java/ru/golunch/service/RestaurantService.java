package ru.golunch.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.golunch.model.Restaurant;
import ru.golunch.repository.CrudRestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.golunch.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final CrudRestaurantRepository restaurantRepository;
    private static final Sort SORT_REGISTERED = Sort.by(Sort.Direction.ASC, "registered");

    public RestaurantService(CrudRestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }

    public void delete(int id) {
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }

    public Restaurant getWithMeals(int id) {
        return checkNotFoundWithId(restaurantRepository.getWithMeals(id), id);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll(SORT_REGISTERED);
    }

    public List<Restaurant> getAllToday() {
        return restaurantRepository.findAllByRegisteredAfter(LocalDate.now());
    }

//    public List<Restaurant> getAllBetweenDate(LocalDate from, LocalDate to) {
//        return restaurantRepository.findByRegisteredAfterAndRegisteredBefore(from,to);
//    }
}