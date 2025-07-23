package com.joaojunio.contact.repositories;

import com.joaojunio.contact.model.DailyRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyRegistrationRepository extends JpaRepository<DailyRegistration, Long> {

}
