package com.joaojunio.contact.repositories;

import com.joaojunio.contact.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE User user SET user.status = :statusCode WHERE user.id = :id")
    void inactiveUserStatus(@Param("statusCode") Integer statusCode, @Param("id") Long id);

}
