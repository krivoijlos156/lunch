package ru.golunch.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.golunch.model.Vote;
import ru.golunch.service.VoteService;
import ru.golunch.util.CheckTime;
import ru.golunch.util.exception.AlreadyVotedException;
import ru.golunch.web.SecurityUtilTEST;

import java.time.LocalTime;

import static ru.golunch.web.controller.RootController.REST_URL;


@RestController
@RequestMapping(value = REST_URL + "/vote", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    VoteService voteService;


    @PostMapping(value = "/{id}")
    public void save(@PathVariable("id") int restId) {
        int userId = SecurityUtilTEST.authUserId();
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
