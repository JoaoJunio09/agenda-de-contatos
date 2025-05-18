package com.joaojunio.contact.services;

import com.joaojunio.contact.controllers.PersonController;
import com.joaojunio.contact.controllers.UserController;
import com.joaojunio.contact.data.dto.PersonDTO;
import com.joaojunio.contact.data.dto.UserDTO;
import com.joaojunio.contact.exceptions.NotFoundException;
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
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import static com.joaojunio.contact.mapper.ObjectMapper.parseListObjects;
import static com.joaojunio.contact.mapper.ObjectMapper.parseObject;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

        list.stream()
            .filter(user -> {
                Date dateAccessuser = user.getRecordHistory().getDatetimeAccess();
                if (dateAccessuser == null) return false;

                LocalDate dateAccess = Instant
                    .ofEpochMilli(dateAccessuser.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

                long daysAccess = ChronoUnit.DAYS.between(dateAccess, LocalDate.now());
                return daysAccess >= 30;
            })
            .forEach(user -> repository.inactiveUserStatus(UserStatus.INACTIVE.getCode(), user.getId()));

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

    public UserDTO create(UserDTO userDTO, HttpServletRequest request) {
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
        user.setRecordHistory(addUserAccessData(new RecordHistory(), request));

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
        dto.add(linkTo(methodOn(UserController.class).create(dto, null)).withRel("create").withType("POST"));
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

    private RecordHistory addUserAccessData(RecordHistory recordHistory, HttpServletRequest request) {
        recordHistory.setOperatingSystem(System.getProperty("os.name"));
        recordHistory.setBrowser(identifyBrowser(request.getHeader("User-Agent")));
        recordHistory.setIp(request.getRemoteAddr());
        LocalDate localDate40DiasAtras = LocalDate.now().minusDays(40);
        Instant instant = localDate40DiasAtras.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date data40DiasAtras = Date.from(instant);
        recordHistory.setDatetimeRegistration(new Date());
        recordHistory.setDatetimeAccess(data40DiasAtras);
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
