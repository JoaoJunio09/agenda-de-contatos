package com.joaojunio.contact.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.joaojunio.contact.model.enums.UserStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private Integer status = UserStatus.ACTIVE.getCode();

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "record_history_id", referencedColumnName = "id")
    private RecordHistory recordHistory;

    public User() {}

    public User(Long id, String email, String password, Person person, RecordHistory recordHistory) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.person = person;
        setUserStatus(UserStatus.ACTIVE);
        this.recordHistory = recordHistory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public UserStatus getUserStatus() {
        if (status == null) return null;
        return UserStatus.fromCode(status);
    }

    public void setUserStatus(UserStatus status) {
        if (status == null) {
            this.status = null;
        } else {
            this.status = status.getCode();
        }
    }


    public RecordHistory getRecordHistory() {
        return recordHistory;
    }

    public void setRecordHistory(RecordHistory recordHistory) {
        this.recordHistory = recordHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
