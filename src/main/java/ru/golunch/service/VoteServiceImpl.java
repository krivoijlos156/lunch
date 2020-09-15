package ru.golunch.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.golunch.model.Restaurant;
import ru.golunch.model.User;
import ru.golunch.model.Vote;
import ru.golunch.repository.CrudRestaurantRepository;
import ru.golunch.repository.CrudUserRepository;
import ru.golunch.repository.CrudVoteRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.golunch.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService{
    private static final Sort SORT_REGISTERED = Sort.by(Sort.Direction.ASC, "registered");

    private final CrudVoteRepository voteRepository;
    private final CrudUserRepository userRepository;
    private final CrudRestaurantRepository restaurantRepository;

    public VoteServiceImpl(CrudVoteRepository voteRepository,
                           CrudUserRepository userRepository,
                           CrudRestaurantRepository crudRestaurantRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = crudRestaurantRepository;
    }

    @Override
    @Transactional
    public void update(Vote voteToday, int restId) {
        Restaurant restaurant = restaurantRepository.getOne(restId);
        voteToday.setRestaurant(restaurant);
        voteToday.setRegistered(LocalDateTime.now());
        checkNotFoundWithId(voteRepository.save(voteToday), voteToday.getId());
    }

    @Override
    @Transactional
    public Vote create(int userId, int restId) {
        User user = userRepository.getOne(userId);
        Restaurant restaurant = restaurantRepository.getOne(restId);
        return voteRepository.save(new Vote(user, restaurant));
    }

    @Override
    public Vote getTodayForUser(int userId) {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        return voteRepository.getBetweenDateTimeForUser(startOfToday, userId);
    }

    @Override
    public List<Vote> getAll() {
        return voteRepository.findAll(SORT_REGISTERED);
    }

    @Override
    public List<Vote> getAllToday() {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        return voteRepository.getBetweenDateTime(startOfToday);
    }

    @Override
    public int countVotesForRestaurantToday(int restId) {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        return voteRepository.countVotesForRestaurantToday(restId, startOfToday, startOfToday.plusDays(1));
    }
}