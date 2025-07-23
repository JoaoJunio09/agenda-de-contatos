package com.joaojunio.contact.repositories;

import com.joaojunio.contact.model.EditedContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditedContactRepository extends JpaRepository<EditedContact, Long> {
}
