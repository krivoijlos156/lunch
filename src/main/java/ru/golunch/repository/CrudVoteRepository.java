package ru.golunch.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.golunch.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.userId=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    List<Vote> findByDateTimeAfterAndDateTimeBefore(LocalDateTime from, LocalDateTime to);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.dateTime DESC")
    List<Vote> getAll(@Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE  v.dateTime >= :startDate AND v.dateTime < :endDate ORDER BY v.dateTime DESC")
    List<Vote> getBetweenDateTime(@Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId and  v.dateTime >= :startDate AND v.dateTime < :endDate ORDER BY v.dateTime DESC")
    List<Vote> getBetweenDateTimeForUser(@Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate,
                                         @Param("userId") int userId);
}