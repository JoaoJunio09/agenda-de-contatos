package com.joaojunio.contact.repositories;

import com.joaojunio.contact.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c WHERE c.user.id = :id")
    List<Contact> findContactByUser(@Param("id") Long id);

    @Query("SELECT c FROM Contact c WHERE c.title LIKE LOWER(CONCAT ('%', :search, '%')) AND c.user.id = :userId")
    List<Contact> findContactsBySearch(@Param("search") String search, @Param("userId") Long id);
}
