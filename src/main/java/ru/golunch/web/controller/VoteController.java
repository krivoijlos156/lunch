package ru.golunch.web.controller;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    VoteService voteService;

    @GetMapping("/vote")
    public Map<Integer, Integer> voting() {
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
        Vote voteToday = voteService.getTodayForUser(userId);
        if (voteToday == null) {
            Vote newVote = voteService.create(userId, restId);
        } else if (CheckTime.assertVoteIsValid(voteToday)) {
            voteService.update(voteToday, restId);
        }else {
            throw new AlreadyVotedException("already voted today");
        }
    }
}
