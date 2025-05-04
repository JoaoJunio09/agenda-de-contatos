package com.joaojunio.contact.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tb_person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", length = 80, nullable = false)
    private String firstName;

    @Column(name = "lastName", length = 80, nullable = false)
    private String lastName;

    @Column(name = "birthDate", nullable = false)
    private Date birthDate;

    @Column(name = "email", length = 80, nullable = false)
    private String email;

    @Column(name = "phone", length = 11, nullable = false)
    private String phone;

    @Column(name = "address", length = 120, nullable = false)
    private String address;

    @Column(name = "complement", length = 30, nullable = false)
    private String complement;

    @Column(name = "gender", length = 9, nullable = false)
    private String gender;

    @Column(name = "number", length = 10)
    private Integer number;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "cpf", length = 11, nullable = false)
    private String cpf;

    @Column(name = "rg", length = 9, nullable = false)
    private String rg;

    public Person() {}

    public Person(
        Long id, String firstName, String lastName, Date birthDate,
        String email, String phone, String address, String complement,
        String gender, Integer number, String nationality, String cpf, String rg
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.complement = complement;
        this.gender = gender;
        this.number = number;
        this.nationality = nationality;
        this.cpf = cpf;
        this.rg = rg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
