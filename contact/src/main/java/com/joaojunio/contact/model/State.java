package com.joaojunio.contact.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "tb_state")
public class State implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Column(name = "acronym", nullable = false, length = 2)
    private String acronym;

    public State() {}

    public State(Long id, String name, String acronym) {
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
