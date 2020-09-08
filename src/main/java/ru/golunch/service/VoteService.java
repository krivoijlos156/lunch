package ru.golunch.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.golunch.model.Vote;
import ru.golunch.repository.CrudVoteRepository;
import ru.golunch.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.golunch.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final CrudVoteRepository voteRepository;

    public VoteService(CrudVoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public void update(Vote vote, int userId) {
        setUserId(vote, userId);
        checkNotFoundWithId(voteRepository.save(vote), vote.getId());
    }

    public Vote create(Vote vote, int userId) {
        setUserId(vote, userId);
        return voteRepository.save(vote);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(voteRepository.delete(id, userId) != 0, id);
    }

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(voteRepository
                .findById(id)
                .filter(vote -> vote.getUserId() == userId)
                .orElse(null), id);
    }

    public List<Vote> getAllToday() {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        return voteRepository.getBetweenDateTime(startOfToday, startOfToday.plusDays(1));
    }

//    public List<Vote> getAllYesterday() {
//        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
//        return voteRepository.getBetweenDateTime(startOfToday.minusDays(1), startOfToday);
//    }

    protected void checkNewOld(Vote vote, int userId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            throw new NotFoundException("Not found entity with id: " + vote.getId());
        }
    }

    protected void setUserId(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        checkNewOld(vote, userId);
        vote.setUserId(userId);
    }
}