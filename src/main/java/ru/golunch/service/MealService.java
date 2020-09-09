package ru.golunch.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.golunch.model.Meal;
import ru.golunch.repository.CrudMealRepository;

import java.util.List;

import static ru.golunch.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final CrudMealRepository mealRepository;

    public MealService(CrudMealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Transactional
    public void update(Meal meal) {
        Assert.notNull(meal, "meal must not be null");
        checkNotFoundWithId(mealRepository.save(meal), meal.getId());
    }

    @Transactional
    public Meal create(Meal meal) {
        Assert.notNull(meal, "meal must not be null");
        return mealRepository.save(meal);
    }


    public void delete(int id) {
        checkNotFoundWithId(mealRepository.delete(id) != 0, id);
    }

    public Meal get(int id) {
        return checkNotFoundWithId(mealRepository.findById(id).orElse(null), id);
    }

    public List<Meal> getAll() {
        return mealRepository.findAll();
    }
}