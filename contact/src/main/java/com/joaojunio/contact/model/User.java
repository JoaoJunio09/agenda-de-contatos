package com.joaojunio.contact.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joaojunio.contact.model.enums.UserAdmin;
import com.joaojunio.contact.model.enums.UserStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @Column(name = "admin", nullable = false)
    private Integer admin = UserAdmin.NOT_ALLOWED.getCode();

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "record_history_id", referencedColumnName = "id")
    private RecordHistory recordHistory;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Contact> contacts = new HashSet<>();

    public User() {}

    public User(Long id, String email, String password, Person person, RecordHistory recordHistory) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.person = person;
        setUserStatus(UserStatus.ACTIVE);
        setUserAdmin(UserAdmin.NOT_ALLOWED);
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

    public UserAdmin getUserAdmin() {
        if (admin == null) return null;
        return UserAdmin.fromCode(admin);
    }

    public void setUserAdmin(UserAdmin admin) {
        if (admin == null) {
            this.admin = null;
        } else {
            this.admin = admin.getCode();
        }
    }

    public RecordHistory getRecordHistory() {
        return recordHistory;
    }

    public void setRecordHistory(RecordHistory recordHistory) {
        this.recordHistory = recordHistory;
    }

    @JsonIgnore
    public Set<Contact> getContacts() {
        return contacts;
    }

    public void addContacts(Contact contact) {
        contacts.add(contact);
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
