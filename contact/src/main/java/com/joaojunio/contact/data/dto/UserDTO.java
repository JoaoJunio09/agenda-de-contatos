package com.joaojunio.contact.data.dto;

import com.joaojunio.contact.model.Person;
import com.joaojunio.contact.model.User;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;

public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {

    private Long id;
    private String email;
    private String password;
    private PersonDTO person;

    public UserDTO() {}

    public UserDTO(Long id, String email, String password, PersonDTO person) {
        this.id = id;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
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
