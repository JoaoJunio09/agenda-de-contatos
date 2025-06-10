package com.joaojunio.contact.data.dto;

import com.joaojunio.contact.model.Person;
import com.joaojunio.contact.model.enums.UserAdmin;
import com.joaojunio.contact.model.enums.UserStatus;

import java.io.Serializable;
import java.util.Objects;

public class UserUpdateRequestDTO implements Serializable {

    private Long id;
    private String email;
    private String password;
    private Integer status;
    private Integer admin = 1;
    private Person person;

    public UserUpdateRequestDTO() {}

    public UserUpdateRequestDTO(Long id, String email, String password, Integer codeStatus, Integer code, Person person) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.person = person;
        setUserStatus(codeStatus);
        setUserAdmin(code);
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

    public void setUserStatus(Integer code) {
        UserStatus status = UserStatus.fromCode(code);

        this.status = status.getCode();
    }

    public UserAdmin getUserAdmin() {
        if (admin == null) return null;
        return UserAdmin.fromCode(admin);
    }

    public void setUserAdmin(Integer code) {
        UserAdmin admin = UserAdmin.fromCode(code);

        this.admin = admin.getCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        com.joaojunio.contact.data.dto.UserUpdateRequestDTO user = (com.joaojunio.contact.data.dto.UserUpdateRequestDTO) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
