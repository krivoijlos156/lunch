package ru.golunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.golunch.model.User;


public interface CrudUserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Query("DELETE FROM User u WHERE u.id=?1")
    int delete(int id);

    void deleteById(int id);

    User findByEmail(String email);
}