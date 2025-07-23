package com.joaojunio.contact.repositories;

import com.joaojunio.contact.model.DailyLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyLoginRepository extends JpaRepository<DailyLogin, Long> {

}
