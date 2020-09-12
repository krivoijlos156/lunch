package ru.golunch.web.controller;

import org.junit.jupiter.api.Test;
import ru.golunch.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends AbstractControllerTest {

    @Test
    void list() throws Exception {
        perform(get("/rest"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getRest() {
    }

    @Test
    void updateRestaurantName() {
    }

    @Test
    void addMeals() {
    }

    @Test
    void deleteMeals() {
    }

    @Test
    void updateMeals() {
    }

    @Test
    void createWithLocation() {
    }

    @Test
    void delete() {
    }
}