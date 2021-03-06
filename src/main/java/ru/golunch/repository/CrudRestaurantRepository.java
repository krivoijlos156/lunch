package ru.golunch.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.golunch.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    Restaurant findByName(String name);

    List<Restaurant> findAll();

    List<Restaurant> findAllByRegisteredAfter(LocalDate localDate);
}