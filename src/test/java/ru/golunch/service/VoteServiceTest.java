package ru.golunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.golunch.model.Vote;
import ru.golunch.util.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.golunch.util.UserUtil.USER_ID;
import static ru.golunch.util.VoteUtil.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    VoteService service;

    @Test
    void update() {
        Vote update = getUpdated();
        service.update(update, USER_ID);
        MATCHER_VOTE.assertMatch(service.get(VOTE1_ID, USER_ID), update);
    }

    @Test
    void create() {
        Vote actual = service.create(VOTE1, USER_ID);
        MATCHER_VOTE.assertMatch(actual, VOTE1);
    }

    @Test
    void delete() {
        service.delete(VOTE1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(VOTE1_ID, USER_ID));
    }

    @Test
    void get() {
        Vote actual = service.get(VOTE1_ID, USER_ID);
//        VOTE1.setDateTime(actual.getDateTime());
        MATCHER_VOTE.assertMatch(actual, VOTE1);
//        assertThat(service.get(VOTE1_ID, USER_ID)).isEqualToIgnoringGivenFields(VOTE1, "registered");
//        assertThat(service.get(VOTE1_ID, USER_ID).getRestId()).isEqualTo(VOTE1.getRestId());
    }

    @Test
    void getAllToday() {
        List<Vote> rests = service.getAllToday();
        MATCHER_VOTE.assertMatch(rests, VOTES);
    }
}