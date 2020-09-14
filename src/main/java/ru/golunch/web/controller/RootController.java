package ru.golunch.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.golunch.model.Restaurant;
import ru.golunch.service.RestaurantService;
import ru.golunch.to.RestaurantDto;

import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping( produces = MediaType.APPLICATION_JSON_VALUE)
public class RootController {

    static final String REST_URL = "/restaurant";

    @Autowired
    RestaurantService restService;


    @Autowired
    Converter<Restaurant, RestaurantDto> restaurantConverter;


    @GetMapping
    public String root() {
        return "redirect:restaurant";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping ("/restaurant")
    public List<RestaurantDto> list() {
        return restService.getAll().stream().map(restaurantConverter::convert).collect(Collectors.toList());
    }
}
