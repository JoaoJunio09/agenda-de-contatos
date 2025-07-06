package com.joaojunio.contact.data.dto;

import java.io.Serializable;
import java.util.Objects;

public class ContactByUserResponseDTO implements Serializable {

    private Long id;
    private String title;
    private String description;
    private String contact;

    public ContactByUserResponseDTO() {}

    public ContactByUserResponseDTO(Long id, String title, String description, String contact) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.contact = contact;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ContactByUserResponseDTO that = (ContactByUserResponseDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getContact(), that.getContact());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getTitle(), getDescription(), getContact());
    }
}
