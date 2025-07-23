package com.joaojunio.contact.repositories;

import com.joaojunio.contact.model.DeletedContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedContactRepository extends JpaRepository<DeletedContact, Long> {
}
