package com.joaojunio.contact.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joaojunio.contact.model.enums.UserStatus;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserResponseDTO extends RepresentationModel<UserResponseDTO> implements Serializable {

    private Long id;
    private String email;
    private Integer status;
    private PersonResponseDTO person;
    Set<ContactResponseDTO> contacts = new HashSet<>();

    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String email, PersonResponseDTO person) {
        this.id = id;
        this.email = email;
        setUserStatus(UserStatus.ACTIVE);
        this.person = person;
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

    public PersonResponseDTO getPerson() {
        return person;
    }

    public void setPerson(PersonResponseDTO person) {
        this.person = person;
    }

    @JsonProperty("userStatus")
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

    public Set<ContactResponseDTO> getContacts() {
        return contacts;
    }

    public void setContacts(Set<ContactResponseDTO> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserResponseDTO user = (UserResponseDTO) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
