package ru.golunch.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.golunch.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

//    @Transactional
//    @Modifying
//    @Query("INSERT INTO Vote (rest_id, user_id) select (?1, ?2)")
//    Vote save(int restId, int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.dateTime DESC")
    List<Vote> getAll(@Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE  v.dateTime >= :startDate ORDER BY v.dateTime DESC")
    List<Vote> getBetweenDateTime(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId and  v.dateTime >= :startDate")
    Vote getBetweenDateTimeForUser(@Param("startDate") LocalDateTime startDate, @Param("userId") int userId);

    @Query("SELECT COUNT(*) FROM Vote v WHERE v.restaurant.id=:restId " +
            "AND v.dateTime >= :startDate " +
            "AND v.dateTime < :endDate")
    int countVotesForRestaurantToday(@Param("restId") int restId,
                                     @Param("startDate") LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate);
}