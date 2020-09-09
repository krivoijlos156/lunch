package ru.golunch.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.golunch.model.Restaurant;
import ru.golunch.model.Role;
import ru.golunch.model.User;
import ru.golunch.model.Vote;
import ru.golunch.repository.CrudUserRepository;
import ru.golunch.repository.CrudVoteRepository;
import ru.golunch.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.golunch.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {
    private static final Sort SORT_REGISTERED = Sort.by(Sort.Direction.ASC, "registered");

    private final CrudVoteRepository voteRepository;
    private final CrudUserRepository userRepository;

    public VoteService(CrudVoteRepository voteRepository, CrudUserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void update(Vote vote, int userId) {
        setUserId(vote, userId);
        checkNotFoundWithId(voteRepository.save(vote), vote.getId());
    }

    @Transactional
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
                .filter(vote -> vote.getUser().getId() == userId)
                .orElse(null), id);
    }

    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    public List<Vote> getAllToday() {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        return voteRepository.getBetweenDateTime(startOfToday, startOfToday.plusDays(1));
    }

    public int countVotesForRestaurantToday(int restId){
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        return voteRepository.countVotesForRestaurantToday(restId, startOfToday, startOfToday.plusDays(1));
    }

    protected void checkNewOld(Vote vote, int userId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            throw new NotFoundException("Not found entity with id: " + vote.getId());
        }
    }

    protected void setUserId(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        checkNewOld(vote, userId);
        vote.setUser(userRepository.findById(userId).orElse(null));
    }
}