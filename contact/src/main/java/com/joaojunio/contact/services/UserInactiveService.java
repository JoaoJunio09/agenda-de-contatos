package com.joaojunio.contact.services;

import com.joaojunio.contact.model.User;
import com.joaojunio.contact.model.enums.UserStatus;
import com.joaojunio.contact.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class UserInactiveService {

    private final Logger logger = LoggerFactory.getLogger(UserInactiveService.class.getName());

    @Autowired
    private UserRepository repository;

    @Scheduled(cron = "0 0 0 * * *")
    public void inactivateInactiveUsers() {
        var list = repository.findAll();
        
        list.stream()
            .filter(this::isInactiveFor30Days)
            .forEach(user -> {
                repository.inactiveUserStatus(UserStatus.INACTIVE.getCode(), user.getId());
                System.out.println("Imprimir relatório de usuários INATIVADOS.");
            });
    }

    private boolean isInactiveFor30Days(User user) {
        Date dateAccessuser = user.getRecordHistory().getDatetimeAccess();
        if (dateAccessuser == null) return false;

        LocalDate dateAccess = Instant
                .ofEpochMilli(dateAccessuser.getTime())
                .atZone(ZoneId.systemDefault()).toLocalDate();

        long daysAccess = ChronoUnit.DAYS.between(dateAccess, LocalDate.now());
        return daysAccess >= 30;
    }
}
