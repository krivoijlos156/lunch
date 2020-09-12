package ru.golunch.web.controller;

import org.junit.jupiter.api.Test;
import ru.golunch.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    @Test
    void voting() throws Exception {
        perform(get("/rest/vote"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void save() {
    }
}