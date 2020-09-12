package ru.golunch.web.controller.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.golunch.model.Meal;
import ru.golunch.to.MealDto;

@Component
public class MealConverter implements Converter<Meal, MealDto> {
    @Override
    public MealDto convert(Meal source) {
        return new MealDto(source.getId(), source.getName(), source.getPrice());
    }
}
