package com.joaojunio.contact.repositories;

import com.joaojunio.contact.model.DailyContactRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyContactRegistrationRepository extends JpaRepository<DailyContactRegistration, Long> {
}
