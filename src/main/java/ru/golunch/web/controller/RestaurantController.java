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
import ru.golunch.to.MealDto;
import ru.golunch.to.RestaurantDto;
import ru.golunch.to.UpdateRestaurantNameRq;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.golunch.util.ValidationUtil.*;
import static ru.golunch.util.ValidationUtil.assureIdConsistent;
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
    Converter<Restaurant, RestaurantDto> restaurantConverter;

    @Autowired
    VoteService voteService;

    @GetMapping("vote")
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


    @GetMapping("/{restId}")
    public RestaurantDto getRest(@PathVariable int restId) {
        log.info("get restaurant {}", restId);
        return restaurantConverter.convert(restService.get(restId));
    }

//    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void update(@RequestBody Restaurant restaurant, @PathVariable int id) {
////        assureIdConsistent(restaurant, id);
//        restService.delete(restaurant.getId());
//    }

    @PostMapping("/updateName/{restId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRestaurantName(@PathVariable int restId, @RequestBody UpdateRestaurantNameRq rq) {
        log.info("update restaurant {} name to {}", restId, rq.getNewName());
        assureIdConsistent(rq, restId);
        restService.updateName(restId, rq.getNewName());
    }

    @PostMapping(value = "/{restId}/menu/addMeal", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addMeals(@PathVariable int restId, @RequestBody MealDto mealDto) {
        log.info("add meal {} for restaurant {}", mealDto.getId(), restId);
        Restaurant restaurant = restService.get(restId);
        mealService.update(new Meal(mealDto.getName(), restaurant, mealDto.getPrice()));
    }

    @DeleteMapping("/{restId}/menu/deleteMeal/{mealId}")
    public void deleteMeals(@PathVariable int restId, @PathVariable int mealId) {
        log.info("delete meal {} for restaurant {}", mealId, restId);
        checkNotFoundMealsInRest(restService.get(restId), mealId);
        mealService.delete(mealId);
    }

    @PostMapping(value = "/{restId}/menu/upMeal/{mealId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateMeals(@PathVariable int restId, @PathVariable int mealId, @RequestBody MealDto mealDto) {
        log.info("update meal {} for restaurant {}", mealDto.getId(), restId);
        assureIdConsistent(mealDto, mealId);
        Restaurant restaurant = checkNotFoundMealsInRest(restService.get(restId), mealId);
        mealService.update(new Meal(mealDto.getId(), mealDto.getName(), restaurant, mealDto.getPrice()));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestWithLocation(@RequestBody Restaurant restaurant) {
        log.info("create new restaurant");
        checkNew(restaurant);
        Restaurant created = restService.create(restaurant);
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
