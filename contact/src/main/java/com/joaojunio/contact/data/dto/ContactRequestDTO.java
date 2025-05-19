package com.joaojunio.contact.data.dto;

import java.io.Serializable;
import java.util.Objects;

public class ContactRequestDTO implements Serializable {

    private Long id;
    private String title;
    private String description;
    private String contact;
    private Long userId;

    public ContactRequestDTO() {}

    public ContactRequestDTO(Long id, String title, String description, String contact, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.contact = contact;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContactRequestDTO contact = (ContactRequestDTO) o;
        return Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
