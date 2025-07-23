package com.joaojunio.contact.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity(name = "tb_daily_login")
public class DailyLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "day", nullable = false) // 1 - Segunda, 2 - Terça, 3 - Quarta ...
    private Integer day;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    public DailyLogin() {}

    public DailyLogin(Long id, Long user_id, Integer day, Date date) {
        this.id = id;
        this.user_id = user_id;
        this.day = day;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DailyLogin that = (DailyLogin) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUser_id(), that.getUser_id()) && Objects.equals(getDay(), that.getDay());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser_id(), getDay());
    }
}
