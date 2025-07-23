package com.joaojunio.contact.repositories;

import com.joaojunio.contact.model.AddedContacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddedContactsRepository extends JpaRepository<AddedContacts, Long> {
}
