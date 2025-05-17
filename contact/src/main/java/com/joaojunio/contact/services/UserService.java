package com.joaojunio.contact.services;

import com.joaojunio.contact.controllers.PersonController;
import com.joaojunio.contact.controllers.UserController;
import com.joaojunio.contact.data.dto.PersonDTO;
import com.joaojunio.contact.data.dto.UserDTO;
import com.joaojunio.contact.exceptions.NotFoundException;
import com.joaojunio.contact.model.Person;
import com.joaojunio.contact.model.User;
import com.joaojunio.contact.repositories.PersonRepository;
import com.joaojunio.contact.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joaojunio.contact.mapper.ObjectMapper.parseListObjects;
import static com.joaojunio.contact.mapper.ObjectMapper.parseObject;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    @Autowired
    UserRepository repository;

    @Autowired
    PersonRepository personRepository;

    public List<UserDTO> findAll() {

        logger.info("Finds All User");

        var list = parseListObjects(repository.findAll(), UserDTO.class);
        list.forEach(this::addHateoasLinks);

        return list;
    }

    public UserDTO findById(Long id) {

        logger.info("Find a User");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException(("Not Found this ID : " + id)));
        var dto = parseObject(entity, UserDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public UserDTO create(UserDTO userDTO) {
        // Implementar:s
        // não adicionar novos person com o mesmo cpf e rg;
        // não adicionar novos user com o mesmo email;
        // o cadastro e login serão únicos.

        logger.info("Create a new User");

        if (userDTO.getId() != null) {
            throw new NotFoundException("Este usuário já existe.");
        }

        PersonDTO personDTO = userDTO.getPerson();
        Person person;

        if (personDTO.getId() != null) {
            person = personRepository.findById(personDTO.getId())
                .orElseThrow(() -> new NotFoundException("Pessoa não encontrada."));
        }
        else {
            person = personRepository.save(parseObject(personDTO, Person.class));
        }

        User user = parseObject(userDTO, User.class);
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPerson(person);

        var dto = parseObject(repository.save(user), UserDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public UserDTO update(UserDTO userDTO) {

        logger.info("Update a User");

        var entity = repository.findById(userDTO.getId())
            .orElseThrow(() -> new NotFoundException(("Not Found this ID : " + userDTO.getId())));
        entity.setEmail(userDTO.getEmail());
        entity.setPassword(userDTO.getPassword());

        var dto = parseObject(repository.save(entity), UserDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public void delete(Long id) {

        logger.info("Deleting one User");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException(("Not Found this ID : " + id)));

        repository.delete(entity);
    }

    private void addHateoasLinks(UserDTO dto) {
        dto.add(linkTo(methodOn(UserController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(UserController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(UserController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));

        if (dto.getPerson() != null) {
            dto.getPerson().add(linkTo(methodOn(PersonController.class).findById(dto.getPerson().getId())).withSelfRel().withType("GET"));
            dto.getPerson().add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
            dto.getPerson().add(linkTo(methodOn(PersonController.class).create(dto.getPerson())).withRel("create").withType("POST"));
            dto.getPerson().add(linkTo(methodOn(PersonController.class).update(dto.getPerson())).withRel("update").withType("PUT"));
            dto.getPerson().add(linkTo(methodOn(PersonController.class).delete(dto.getPerson().getId())).withRel("delete").withType("DELETE"));
        }
    }
}
