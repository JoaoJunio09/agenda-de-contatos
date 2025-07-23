package com.joaojunio.contact.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "tb_daily_contact_registration")
public class DailyContactRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "day", nullable = false)
    private Integer day;

    public DailyContactRegistration() {}

    public DailyContactRegistration(Long id, Long user_id, Integer day) {
        this.id = id;
        this.user_id = user_id;
        this.day = day;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
        DailyContactRegistration that = (DailyContactRegistration) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUser_id(), that.getUser_id()) && Objects.equals(getDay(), that.getDay());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser_id(), getDay());
    }
}