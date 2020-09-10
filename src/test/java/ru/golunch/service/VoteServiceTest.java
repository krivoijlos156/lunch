package ru.golunch.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.golunch.TestMatcher;
import ru.golunch.model.Restaurant;
import ru.golunch.model.Vote;
import ru.golunch.repository.CrudRestaurantRepository;
import ru.golunch.repository.CrudUserRepository;
import ru.golunch.repository.CrudVoteRepository;
import ru.golunch.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class VoteServiceTest extends AbstractServiceTest {
    public static TestMatcher<Vote> MATCHER_VOTE = new TestMatcher<>("registered");
    public static final int USER_ID = 100000; //magic number. Control in db (first User)
    public static final int VOTE_ID = 100019; //magic number. Control in db (first vote)
    public static final int REST_ID = 100006; //magic number. Control in db (first restaurant)
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
        Restaurant upRest = restaurantRepository.findById(REST_ID).orElse(null);
        upRest.setName("NEW");
        upRest.setId(null);
        Restaurant newRest = restaurantRepository.save(upRest);
        vote.setRestaurant(newRest);
        service.save(vote);
        MATCHER_VOTE.assertMatch(repository.findById(vote.getId()).orElse(null), vote);
    }

    @Test
    void create() {
        Vote expected = new Vote(userRepository.findById(USER_ID).orElse(null),
                restaurantRepository.findById(REST_ID).orElse(null));
        Vote actual = service.save(expected);
        MATCHER_VOTE.assertMatch(actual, expected);
    }

//    @Test
//    void delete() {
//        service.delete(vote.getId(), USER_ID);
//        assertThrows(NotFoundException.class, () -> service.get(vote.getId(), USER_ID));
//    }

//    @Test
//    void deleteNotFound() {
//        assertThrows(NotFoundException.class, () -> service.delete(10, USER_ID));
//    }

//    @Test
//    void get() {
//        Vote actual = service.get(vote.getId(), USER_ID);
//        MATCHER_VOTE.assertMatch(actual, vote);
//    }
//
//    @Test
//    void getNotFound() {
//        assertThrows(NotFoundException.class, () -> service.get(10, USER_ID));
//    }

    @Test
    void getAll() {
        List<Vote> actual = service.getAll();
        MATCHER_VOTE.assertMatch(actual, repository.findAll());
    }

    @Test
    void getAllToday() {
        Vote oldVote = new Vote(null, LocalDateTime.now().minusDays(2),
                userRepository.findById(USER_ID).orElse(null),
                restaurantRepository.findById(REST_ID).orElse(null));
        List<Vote> expected = repository.findAll();
        repository.save(oldVote);
        List<Vote> actual = service.getAllToday();
        MATCHER_VOTE.assertMatch(actual, expected);
    }

    @Test
    void countVotesForRestaurantToday() {
        Vote oldVote = new Vote(null, LocalDateTime.now().minusDays(2),
                userRepository.findById(USER_ID).orElse(null),
                restaurantRepository.findById(REST_ID).orElse(null));
        repository.save(oldVote);
        assertEquals(service.countVotesForRestaurantToday(REST_ID), 3);
        assertEquals(service.countVotesForRestaurantToday(REST_ID + 1), 1);
    }

    @Test
    void getTodayForUser() {
        Vote expected = new Vote(null, LocalDateTime.now().minusDays(2),
                userRepository.findById(USER_ID).orElse(null),
                restaurantRepository.findById(REST_ID).orElse(null));
        service.save(expected);
        repository.deleteById(vote.getId());
        assertNull(service.getTodayForUser(USER_ID));
    }
}