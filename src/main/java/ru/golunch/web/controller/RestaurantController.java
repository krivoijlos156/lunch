package ru.golunch.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.golunch.model.AbstractBaseEntity;
import ru.golunch.model.Meal;
import ru.golunch.model.Restaurant;
import ru.golunch.service.MealService;
import ru.golunch.service.RestaurantService;
import ru.golunch.service.VoteService;
import ru.golunch.to.FullRestaurantDto;
import ru.golunch.to.MealDto;
import ru.golunch.to.RestaurantDto;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.golunch.util.ValidationUtil.*;
import static ru.golunch.web.controller.RootController.REST_URL;

@RestController
@RequestMapping(value = REST_URL + "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    RestaurantService restService;

    @Autowired
    MealService mealService;

    @Autowired
    VoteService voteService;

    @Autowired
    Converter<Restaurant, FullRestaurantDto> restaurantConverter;

    @GetMapping("/allvote")
    public Map<Integer, Integer> voting() {
        log.info("get rest: votes");
        Map<Integer, Integer> votes = new HashMap<>();
        List<Integer> listRestId = restService.getAll()
                .stream()
                .map(AbstractBaseEntity::getId)
                .collect(Collectors.toList());

        for (Integer id : listRestId) {
            votes.put(id, voteService.countVotesForRestaurantToday(id));
        }
        return votes;
    }

    @GetMapping("/vote/{restId}")
    public int vote(@PathVariable int restId) {
        log.info("get vote restaurant {}", restId);
        return voteService.countVotesForRestaurantToday(restId);
    }

    @GetMapping("/{restId}")
    public FullRestaurantDto getRest(@PathVariable int restId) {
        log.info("get restaurant {}", restId);
        return restaurantConverter.convert(restService.get(restId));
    }

    @PostMapping("/updateName/{restId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRestaurantName(@PathVariable int restId, @RequestBody RestaurantDto rq) {
        log.info("update restaurant {} name to {}", restId, rq.getName());
        assureIdConsistent(rq, restId);
        restService.updateName(restId, rq.getName());
    }

    @PostMapping(value = "/{restId}/menu/addMeal", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addMeal(@PathVariable int restId, @RequestBody MealDto mealDto) {
        log.info("add meal {} for restaurant {}", mealDto.getId(), restId);
        Restaurant restaurant = restService.get(restId);
        mealService.create(new Meal(mealDto.getName(), restaurant, mealDto.getPrice()));
    }

    @DeleteMapping("/{restId}/menu/deleteMeal/{mealId}")
    public void deleteMeal(@PathVariable int restId, @PathVariable int mealId) {
        log.info("delete meal {} for restaurant {}", mealId, restId);
        checkNotFoundMealsInRest(restService.get(restId), mealId);
        mealService.delete(mealId);
    }

    @PostMapping(value = "/{restId}/menu/upMeal/{mealId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateMeal(@PathVariable int restId, @PathVariable int mealId, @RequestBody MealDto mealDto) {
        log.info("update meal {} for restaurant {}", mealDto.getId(), restId);
        assureIdConsistent(mealDto, mealId);
        Restaurant restaurant = checkNotFoundMealsInRest(restService.get(restId), mealId);
        mealService.update(new Meal(mealDto.getId(), mealDto.getName(), restaurant, mealDto.getPrice()));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestWithLocation(@RequestBody RestaurantDto restaurant) {
        log.info("create new restaurant");
        checkNew(restaurant);
        if (restaurant.getId() != null) {
            throw new RuntimeException("todo");
        }
        Restaurant created = restService.create(new Restaurant(restaurant.getName()));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("restaurant/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{restId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restId) {
        log.info("delete  restaurant{}", restId);
        restService.delete(restId);
    }
}
