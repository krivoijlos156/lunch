package ru.golunch.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.golunch.TestMatcher;
import ru.golunch.model.Restaurant;
import ru.golunch.model.Role;
import ru.golunch.model.User;
import ru.golunch.model.Vote;
import ru.golunch.repository.CrudRestaurantRepository;
import ru.golunch.repository.CrudUserRepository;
import ru.golunch.repository.CrudVoteRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Transactional
class VoteServiceTest extends AbstractServiceTest {
    public static TestMatcher<Vote> MATCHER_VOTE = TestMatcher.usingFieldsWithIgnoringComparator(Vote.class, "registered");
    public static final int USER_ID = 100000;
    public static final int VOTE_ID = 100019;
    public static final int REST_ID = 100006;
    private static final Sort SORT_REGISTERED = Sort.by(Sort.Direction.ASC, "registered");

    public Vote vote;

    @Autowired
    VoteService service;

    @Autowired
    CrudVoteRepository repository;

    @Autowired
    CrudUserRepository userRepository;

    @Autowired
    CrudRestaurantRepository restaurantRepository;

    @BeforeEach
    void init() {
        vote = repository.findById(VOTE_ID).orElse(null);
    }

    @Test
    void update() {
        Restaurant upRest = restaurantRepository.getOne(REST_ID);
        upRest.setName("NEW");
        upRest.setId(null);
        Restaurant newRest = restaurantRepository.save(upRest);
        vote.setRestaurant(newRest);
        service.update(vote, REST_ID);
        MATCHER_VOTE.assertMatch(repository.findById(vote.getId()).orElse(null), vote);
    }

    @Test
    void create() {
        User newUser = userRepository.save(new User("name", "mane@er.ru", "sss", Role.USER));
        Vote expected = new Vote(newUser, restaurantRepository.getOne(REST_ID));
        Vote actual = service.create(newUser.getId(), REST_ID);
        expected.setId(actual.getId());
        assertThat(actual.getUser()).isEqualToIgnoringGivenFields(expected.getUser(), "registered");
        Assertions.assertEquals(actual, expected);
        Assertions.assertEquals(actual.getRestaurant().getId(), expected.getRestaurant().getId());
    }


    @Test
    void getAll() {
        List<Vote> actual = service.getAll();
        MATCHER_VOTE.assertMatch(actual, repository.findAll());
    }

    @Test
    void getAllToday() {
        Vote oldVote = new Vote(null, LocalDateTime.now().minusDays(2),
                userRepository.getOne(USER_ID),
                restaurantRepository.getOne(REST_ID));
        List<Vote> expected = repository.findAll();
        repository.save(oldVote);
        List<Vote> actual = service.getAllToday();
        MATCHER_VOTE.assertMatch(actual, expected);
    }

    @Test
    void countVotesForRestaurantToday() {
        Vote oldVote = new Vote(null, LocalDateTime.now().minusDays(2),
                userRepository.getOne(USER_ID),
                restaurantRepository.getOne(REST_ID));
        repository.save(oldVote);
        assertEquals(service.countVotesForRestaurantToday(REST_ID), 3);
        assertEquals(service.countVotesForRestaurantToday(REST_ID + 1), 1);
    }

    @Test
    void getTodayForUser() {
        Vote expected = new Vote(null, LocalDateTime.now().minusDays(2),
                userRepository.getOne(USER_ID),
                restaurantRepository.getOne(REST_ID));
        repository.save(expected);
        repository.deleteById(vote.getId());
        assertNull(service.getTodayForUser(USER_ID));
    }
}