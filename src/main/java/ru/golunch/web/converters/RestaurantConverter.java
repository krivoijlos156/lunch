package ru.golunch.web.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.golunch.model.Meal;
import ru.golunch.model.Restaurant;
import ru.golunch.to.MealDto;
import ru.golunch.to.RestaurantDtoForConverter;

import java.util.stream.Collectors;

@Component
public class RestaurantConverter implements Converter<Restaurant, RestaurantDtoForConverter> {

    @Autowired
    Converter<Meal, MealDto> mealConverter;

    @Override
    public RestaurantDtoForConverter convert(Restaurant source) {
        return new RestaurantDtoForConverter(
                source.getId(),
                source.getName(),
                source.getRegistered(),
                source.getMeals()
                        .stream()
                        .map(mealConverter::convert)
                        .collect(Collectors.toList())
        );
    }
}
