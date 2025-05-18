package com.joaojunio.contact.data.dto;

import com.joaojunio.contact.model.RecordHistory;
import com.joaojunio.contact.model.User;
import com.joaojunio.contact.model.enums.UserStatus;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;

public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {

    private Long id;
    private String email;
    private String password;
    private Integer status = UserStatus.ACTIVE.getCode();
    private PersonDTO person;
    private RecordHistory recordHistory;

    public UserDTO() {}

    public UserDTO(Long id, String email, String password, PersonDTO personDTO, RecordHistory recordHistory) {
        this.id = id;
        this.email = email;
        this.password = password;
        setUserStatus(UserStatus.ACTIVE);
        this.person = personDTO;
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

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
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
