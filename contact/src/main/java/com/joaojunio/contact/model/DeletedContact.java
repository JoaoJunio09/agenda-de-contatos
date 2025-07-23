package com.joaojunio.contact.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "tb_deleted_contacts")
public class DeletedContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id", nullable = false)
    Long user_id;

    public DeletedContact() {}

    public DeletedContact(Long id, Long user_id) {
        this.id = id;
        this.user_id = user_id;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DeletedContact that = (DeletedContact) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUser_id(), that.getUser_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser_id());
    }
}
