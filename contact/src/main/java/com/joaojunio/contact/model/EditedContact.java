package com.joaojunio.contact.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "tb_edited_contacts")
public class EditedContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    public EditedContact() {}

    public EditedContact(Long id, Long user_id) {
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
        EditedContact that = (EditedContact) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUser_id(), that.getUser_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser_id());
    }
}
