package ru.golunch.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.golunch.model.Meal;
import ru.golunch.model.Restaurant;
import ru.golunch.service.MealService;
import ru.golunch.service.RestaurantService;
import ru.golunch.to.MealDto;
import ru.golunch.to.RestaurantDto;
import ru.golunch.to.UpdateRestaurantNameRq;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static ru.golunch.util.ValidationUtil.*;
import static ru.golunch.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = "restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    @Autowired
    RestaurantService restService;

    @Autowired
    MealService mealService;

    @Autowired
    Converter<Restaurant, RestaurantDto> restaurantConverter;

    @Autowired
    Converter<Meal, MealDto> mealConverter;

    @GetMapping()
    public List<RestaurantDto> list() {
        return restService.getAllToday().stream().map(restaurantConverter::convert).collect(Collectors.toList());
    }

    @GetMapping("/{restId}")
    public RestaurantDto getRest(@PathVariable int restId) {
        return restaurantConverter.convert(restService.get(restId));
    }

//    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void update(@RequestBody Restaurant restaurant, @PathVariable int id) {
//        assureIdConsistent(restaurant, id);
//        service.update(restaurant);
//    }

    @PostMapping("/updateName/{restId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRestaurantName(@PathVariable int restId, @RequestBody UpdateRestaurantNameRq rq) {
        assureIdConsistent(rq, restId);
        restService.updateName(restId, rq.getNewName());
    }

    @PostMapping("/{restId}/menu/addMeal/")
    public void addMeals(@PathVariable int restId, @RequestBody MealDto mealDto) {
        Restaurant restaurant = restService.get(restId);
        mealService.update(new Meal(mealDto.getName(), restaurant, mealDto.getPrice()));
    }

    @DeleteMapping("/{restId}/menu/deleteMeal/{mealId}")
    public void deleteMeals(@PathVariable int restId, @PathVariable int mealId) {
        checkNotFoundMealsInRest(restService.get(restId), mealId);
        mealService.delete(mealId);
    }

    @PostMapping("{restId}/menu/addMeal/{mealId}")
    public void updateMeals(@PathVariable int restId, @PathVariable int mealId, @RequestBody MealDto mealDto) {
        assureIdConsistent(mealDto, mealId);
        Restaurant restaurant = checkNotFoundMealsInRest(restService.get(restId), mealId);
        mealService.update(new Meal(mealDto.getId(), mealDto.getName(), restaurant, mealDto.getPrice()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
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
        restService.delete(restId);
    }
}
