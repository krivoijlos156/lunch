package ru.golunch.util;


import ru.golunch.model.Restaurant;
import ru.golunch.to.AbstractDto;
import ru.golunch.util.exception.NotFoundException;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(AbstractDto entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(AbstractDto entity, int id) {
//      (http://stackoverflow.com/a/32728226/548473)
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }

    public static Restaurant checkNotFoundMealsInRest(Restaurant rest, int mealId) {
        if (rest.getMeals().stream().filter(meal -> meal.getId() == mealId).findFirst().orElse(null) == null) {
            throw new NotFoundException("Not found meal id: " + mealId + " in restaurant with id" + rest.getId());
        }
        return rest;
    }
}