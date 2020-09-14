package ru.golunch.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.golunch.model.AbstractBaseEntity;
import ru.golunch.model.Vote;
import ru.golunch.service.RestaurantService;
import ru.golunch.service.VoteService;
import ru.golunch.util.CheckTime;
import ru.golunch.util.exception.AlreadyVotedException;
import ru.golunch.web.SecurityUtil;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "vote", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    VoteService voteService;

    @GetMapping
    public Map<Integer, Integer> voting() {
        log.info("get rest: votes");
        Map<Integer, Integer> votes = new HashMap<>();
        List<Integer> listRestId = restaurantService.getAllToday()
                .stream()
                .map(AbstractBaseEntity::getId)
                .collect(Collectors.toList());

        for (Integer id : listRestId) {
            votes.put(id, voteService.countVotesForRestaurantToday(id));
        }
        return votes;
    }

    @PostMapping(value = "/{id}")
    public void save(@PathVariable("id") int restId) {
        int userId = SecurityUtil.authUserId();
        log.info("save vote from user {} for restaurant {}", userId, restId);
        Vote voteToday = voteService.getTodayForUser(userId);
        if (voteToday == null) {
            Vote newVote = voteService.create(userId, restId);
        } else if (CheckTime.assertVoteIsValid(LocalTime.now())) {
            voteService.update(voteToday, restId);
        } else {
            throw new AlreadyVotedException("already voted today");
        }
    }
}
