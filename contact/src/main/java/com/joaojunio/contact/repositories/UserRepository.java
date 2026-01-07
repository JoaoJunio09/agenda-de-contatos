package com.joaojunio.contact.repositories;

import com.joaojunio.contact.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE User user SET user.status = :statusCode WHERE user.id = :id")
    void inactiveUserStatus(@Param("statusCode") Integer statusCode, @Param("id") Long id);
/*
    @Query("SELECT u FROM User u WHERE u.firstName LIKE LOWER(CONCAT ('%', :search, '%')) AND u.id = :id")
    List<User> findUsersBySearch(@Param("search") String search, @Param("id") Long id);

 */

}
