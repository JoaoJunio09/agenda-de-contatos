package com.joaojunio.contact.services;

import com.joaojunio.contact.controllers.PersonController;
import com.joaojunio.contact.data.dto.PersonDTO;
import com.joaojunio.contact.exceptions.NotFoundException;
import com.joaojunio.contact.model.Person;
import com.joaojunio.contact.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joaojunio.contact.mapper.ObjectMapper.parseListObjects;
import static com.joaojunio.contact.mapper.ObjectMapper.parseObject;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@Service
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonDTO> findAll() {

        logger.info("Finds All Person");

        var list = parseListObjects(repository.findAll(), PersonDTO.class);
        list.forEach(this::addHateoasLinks);

        return list;
    }

    public PersonDTO findById(Long id) {

        logger.info("Finds a Person");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not Found this ID : " + id));
        var dto = parseObject(entity, PersonDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public PersonDTO create(PersonDTO personDTO) {
        // antes de cadrastrar, o RG e CPF não podem ser iguais: concertar isso depois.

        logger.info("Create a new Person");

        if (personDTO == null) {
            //
        }

        var entity = parseObject(personDTO, Person.class);
        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public PersonDTO update(PersonDTO personDTO) {

        logger.info("Update a Person");

        var entity = repository.findById(personDTO.getId())
            .orElseThrow(() -> new NotFoundException("Not Found this ID : " + personDTO.getId()));
        entity.setFirstName(personDTO.getFirstName());
        entity.setLastName(personDTO.getLastName());
        entity.setEmail(personDTO.getEmail());
        entity.setAddress(personDTO.getAddress());
        entity.setAddress(personDTO.getAddress());
        entity.setBirthDate(personDTO.getBirthDate());
        entity.setNumber(personDTO.getNumber());

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public void delete(Long id) {

        logger.info("Delete a Person");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not Found this ID : " + id));

        repository.delete(entity);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("UPDATE"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
