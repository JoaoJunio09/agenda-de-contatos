package com.joaojunio.contact.data.dto;

import com.joaojunio.contact.model.State;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class StateDTO implements Serializable {

    private Long id;
    private String name;
    private String acronym;

    public StateDTO() {}

    public StateDTO(Long id, String name, String acronym) {
        this.id = id;
        this.name = name;
        this.acronym = acronym;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(getId(), state.getId()) && Objects.equals(getName(), state.getName()) && Objects.equals(getAcronym(), state.getAcronym());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAcronym());
    }
}
