package com.joaojunio.contact.services;

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

        return parseListObjects(repository.findAll(), UserDTO.class);
    }

    public UserDTO findById(Long id) {

        logger.info("Find a User");

        return parseObject(repository.findById(id), UserDTO.class);
    }

    public UserDTO create(UserDTO userDTO) {
        // Implementar:
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

        return parseObject(repository.save(user), UserDTO.class);
    }

    public UserDTO update(UserDTO userDTO) {

        logger.info("Update a User");

        return null;
    }
}
