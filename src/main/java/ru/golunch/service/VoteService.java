package ru.golunch.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.golunch.model.Restaurant;
import ru.golunch.model.Role;
import ru.golunch.model.User;
import ru.golunch.model.Vote;
import ru.golunch.repository.CrudRestaurantRepository;
import ru.golunch.repository.CrudUserRepository;
import ru.golunch.repository.CrudVoteRepository;
import ru.golunch.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.golunch.util.CheckTime.ELEVEN_OCLOCK;
import static ru.golunch.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {
    private static final Sort SORT_REGISTERED = Sort.by(Sort.Direction.ASC, "registered");

    private final CrudVoteRepository voteRepository;

    public VoteService(CrudVoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

//    @Transactional
//    public void update(int restId, int userId) {
//        checkNotFoundWithId(voteRepository.save(vote), vote.getId());
//    }

    @Transactional
    public Vote save(Vote vote) {
//        setUserId(vote, userId);
        return voteRepository.save(vote);
    }

//    public void delete(int id, int userId) {
//        checkNotFoundWithId(voteRepository.delete(id, userId) != 0, id);
//    }

//    public Vote get(int id, int userId) {
//        return checkNotFoundWithId(voteRepository
//                .findById(id)
//                .filter(vote -> vote.getUser().getId() == userId)
//                .orElse(null), id);
//    }

    public Vote getTodayForUser(int userId) {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        return voteRepository.getBetweenDateTimeForUser(startOfToday, userId);
    }

    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    public List<Vote> getAllToday() {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        return voteRepository.getBetweenDateTime(startOfToday);
    }

    public int countVotesForRestaurantToday(int restId){
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        return voteRepository.countVotesForRestaurantToday(restId, startOfToday, startOfToday.plusDays(1));
    }
}