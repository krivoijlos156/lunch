package ru.golunch.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.golunch.model.Meal;
import ru.golunch.repository.CrudMealRepository;
import ru.golunch.service.MealService;

import java.util.List;

import static ru.golunch.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private final CrudMealRepository mealRepository;

    public MealServiceImpl(CrudMealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    @Transactional
    public void update(Meal meal) {
        Assert.notNull(meal, "meal must not be null");
        checkNotFoundWithId(mealRepository.save(meal), meal.getId());
    }

    @Override
    @Transactional
    public Meal create(Meal meal) {
        Assert.notNull(meal, "meal must not be null");
        return mealRepository.save(meal);
    }

    @Override
    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(mealRepository.delete(id) != 0, id);
    }

    @Override
    public Meal get(int id) {
        return checkNotFoundWithId(mealRepository.findById(id).orElse(null), id);
    }

    @Override
    public List<Meal> getAll() {
        return mealRepository.findAll();
    }
}