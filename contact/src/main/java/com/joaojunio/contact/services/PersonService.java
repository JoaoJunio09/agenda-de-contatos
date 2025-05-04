package com.joaojunio.contact.services;

import com.joaojunio.contact.data.dto.PersonDTO;
import com.joaojunio.contact.model.Person;
import com.joaojunio.contact.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joaojunio.contact.mapper.ObjectMapper.parseListObjects;
import static com.joaojunio.contact.mapper.ObjectMapper.parseObject;

import java.util.Date;
import java.util.List;

@Service
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonDTO> findAll() {

        logger.info("Finds All Person");

        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {

        logger.info("Finds a Person");

        return parseObject(repository.findById(id), PersonDTO.class);
    }

    public PersonDTO create(PersonDTO personDTO) {
        // antes de cadrastrar, o RG e CPF não podem ser iguais: concertar isso depois.

        logger.info("Create a new Person");

        var entity = parseObject(personDTO, Person.class);
        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO personDTO) {

        logger.info("Update a Person");

        return null;
    }

    public void delete(PersonDTO personDTO) {

        logger.info("Delete a Person");

    }
}
