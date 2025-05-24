package com.joaojunio.contact.services;

import com.joaojunio.contact.controllers.PersonController;
import com.joaojunio.contact.controllers.UserController;
import com.joaojunio.contact.data.dto.PersonRequestDTO;
import com.joaojunio.contact.data.dto.UserRequestDTO;
import com.joaojunio.contact.data.dto.UserResponseDTO;
import com.joaojunio.contact.exceptions.NotFoundException;
import com.joaojunio.contact.exceptions.ObjectAlreadyExistsException;
import com.joaojunio.contact.exceptions.RequiredObjectIsNullException;
import com.joaojunio.contact.model.Person;
import com.joaojunio.contact.model.RecordHistory;
import com.joaojunio.contact.model.User;
import com.joaojunio.contact.model.enums.UserStatus;
import com.joaojunio.contact.repositories.PersonRepository;
import com.joaojunio.contact.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.joaojunio.contact.mapper.ObjectMapper.parseListObjects;
import static com.joaojunio.contact.mapper.ObjectMapper.parseObject;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    @Autowired
    UserRepository repository;

    @Autowired
    PersonRepository personRepository;

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {

        logger.info("Finds All User");

        var list = parseListObjects(repository.findAll(), UserResponseDTO.class);
        list.forEach(this::addHateoasLinks);

        return list;
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {

        logger.info("Find a User");

        User entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException(("Not Found this ID : " + id)));
        UserResponseDTO dto = parseObject(entity, UserResponseDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public UserResponseDTO create(UserRequestDTO userDTO, HttpServletRequest request) {

        logger.info("Create a new User");

        if (userDTO == null) throw new RequiredObjectIsNullException();
        else {
            var list = repository.findAll().stream()
                .filter(user ->
                    user.getPerson().getCpf().equalsIgnoreCase(userDTO.getPerson().getCpf()) ||
                    user.getPerson().getRg().equalsIgnoreCase(userDTO.getPerson().getRg()) ||
                    user.getEmail().equalsIgnoreCase(userDTO.getEmail())
                )
                .toList();

            if (!list.isEmpty() || userDTO.getId() != null) {
                throw new ObjectAlreadyExistsException("Usuário ja cadastrado no sistema.");
            }
        }

        PersonRequestDTO personDTO = userDTO.getPerson();
        Person person;

        if (personDTO.getId() != null) {
            person = personRepository.findById(personDTO.getId())
                .orElseThrow(() -> new NotFoundException("Pessoa não encontrada."));
        }
        else {
            person = personRepository.save(parseObject(personDTO, Person.class));
        }

        RecordHistory recordHistory = addUserAccessData(request);

        User user = parseObject(userDTO, User.class);
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(user.getPassword());
        user.setUserStatus(UserStatus.ACTIVE);
        user.setPerson(person);
        user.setRecordHistory(recordHistory);

        User userSaved = repository.save(user);
        var dto = parseObject(userSaved, UserResponseDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    @Transactional
    public UserResponseDTO update(UserRequestDTO userDTO) {

        logger.info("Update a User");

        var entity = repository.findById(userDTO.getId())
            .orElseThrow(() -> new NotFoundException(("Not Found this ID : " + userDTO.getId())));
        entity.setEmail(userDTO.getEmail());
        entity.setPassword(userDTO.getPassword());

        var dto = parseObject(repository.save(entity), UserResponseDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    public void delete(Long id) {

        logger.info("Deleting one User");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not Found this ID : " + id));
        var person = personRepository.findById(entity.getPerson().getId())
            .orElseThrow(() -> new NotFoundException("Not Found this ID : " + id));

        repository.delete(entity);
        personRepository.delete(person);
    }

    private void addHateoasLinks(UserResponseDTO dto) {
        dto.add(linkTo(methodOn(UserController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).create(null, null)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(UserController.class).update(null)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(UserController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));

        if (dto.getPerson() != null) {
            dto.getPerson().add(linkTo(methodOn(PersonController.class).findById(dto.getPerson().getId())).withSelfRel().withType("GET"));
            dto.getPerson().add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
            dto.getPerson().add(linkTo(methodOn(PersonController.class).create(null)).withRel("create").withType("POST"));
            dto.getPerson().add(linkTo(methodOn(PersonController.class).update(null)).withRel("update").withType("PUT"));
            dto.getPerson().add(linkTo(methodOn(PersonController.class).delete(dto.getPerson().getId())).withRel("delete").withType("DELETE"));
        }
    }

    private RecordHistory addUserAccessData(HttpServletRequest request) {
        RecordHistory recordHistory = new RecordHistory();
        recordHistory.setIp(request.getRemoteAddr());
        recordHistory.setOperatingSystem(System.getProperty("os.name"));
        recordHistory.setBrowser(identifyBrowser(request.getHeader("User-Agent")));
        recordHistory.setDatetimeRegistration(new Date());
        recordHistory.setDatetimeAccess(new Date());
        return recordHistory;
    }

    private String identifyBrowser(String userAgent) {
        if (userAgent != null) {
            if (userAgent.contains("Chrome")) {
                return "Google Chrome";
            }
            else if (userAgent.contains("Firefox")) {
                return "Firefox";
            }
            else if (userAgent.contains("Safari")) {
                return "Safari";
            }
            else {
                return "Navegador desconhecido";
            }
        }
        return "Navegador não identificado";
    }
}
