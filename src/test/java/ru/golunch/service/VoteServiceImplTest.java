package ru.golunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.golunch.model.Vote;
import ru.golunch.repository.CrudVoteRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.golunch.util.RestaurantUtil.REST1_ID;
import static ru.golunch.util.UserUtil.USER_ID;
import static ru.golunch.util.VoteUtil.*;

@Transactional
class VoteServiceImplTest extends AbstractServiceTest {
    private static final Sort SORT_REGISTERED = Sort.by(Sort.Direction.ASC, "registered");

    @Autowired
    VoteService service;

    @Autowired
    CrudVoteRepository repository;

    @Test
    void update() {
        Vote expected = getUpdated();
        service.update(expected, REST1_ID);
        MATCHER_VOTE.assertMatch(service.getTodayForUser(USER_ID), expected);
    }

    @Test
    void create() {
        Vote expected = getNew();
        Vote actual = service.create(USER_ID + 4, REST1_ID);
        assertEquals(expected.getRestaurant().getId(), actual.getRestaurant().getId());
        assertEquals(expected.getUser().getId(), actual.getUser().getId());
    }


    @Test
    void getAll() {
        List<Vote> actual = service.getAll();
        assertEquals(VOTES.size() + 1, actual.size());
    }

    @Test
    void getAllToday() {
        List<Vote> actual = service.getAllToday();
        assertEquals(VOTES.size(), actual.size());
    }

    @Test
    void countVotesForRestaurantToday() {
        assertEquals(service.countVotesForRestaurantToday(REST1_ID), 2);
        assertEquals(service.countVotesForRestaurantToday(REST1_ID + 1), 1);
    }

    @Test
    void getTodayForUser() {
        repository.deleteById(VOTE1_ID + 4);
        assertNull(service.getTodayForUser(USER_ID + 4));
    }
}