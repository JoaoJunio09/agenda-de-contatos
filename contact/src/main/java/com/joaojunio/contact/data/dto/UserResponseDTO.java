package com.joaojunio.contact.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.joaojunio.contact.model.enums.UserAdmin;
import com.joaojunio.contact.model.enums.UserStatus;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Relation(collectionRelation = "user")
public class UserResponseDTO extends RepresentationModel<UserResponseDTO> implements Serializable {

    private Long id;
    private String email;
    private Integer status;
    private Integer admin;
    private PersonResponseDTO person;

    @JsonIgnore
    Set<ContactResponseDTO> contacts = new HashSet<>();

    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String email, PersonResponseDTO person) {
        this.id = id;
        this.email = email;
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

    @JsonIgnore
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonIgnore
    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    @JsonProperty("userAdmin")
    public UserAdmin getUserAdmin() {
        if (admin == null) return null;
        return UserAdmin.fromCode(admin);
    }

    public void setUserAdmin(Integer code) {
        UserAdmin admin = UserAdmin.fromCode(code);

        this.admin = admin.getCode();
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
