package ru.golunch.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.golunch.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.golunch.util.RestaurantUtil.MATCHER_REST;
import static ru.golunch.util.RestaurantUtil.RESTS;



class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RootController.REST_URL;


    @Test
    void list() throws Exception {
        perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_REST.contentJson(RESTS));
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