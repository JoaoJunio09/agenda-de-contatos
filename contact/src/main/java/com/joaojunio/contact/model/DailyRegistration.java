package com.joaojunio.contact.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "tb_daily_registration")
public class DailyRegistration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user", nullable = false)
    private Long id_user;

    @Column(name = "day", nullable = false) // 1 - Segunda, 2 - Terça, 3 - Quarta ...
    private Integer day;

    public DailyRegistration() {}

    public DailyRegistration(Long id, Long id_user, Integer day) {
        this.id = id;
        this.id_user = id_user;
        this.day = day;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DailyRegistration that = (DailyRegistration) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getId_user(), that.getId_user()) && Objects.equals(getDay(), that.getDay());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getId_user(), getDay());
    }
}
