package ru.golunch.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.golunch.model.Restaurant;
import ru.golunch.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping(value = "rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootController {

    @Autowired
    RestaurantService service;

    @GetMapping()
    public List<Restaurant> list() {
        return service.getAllToday();
    }
}
