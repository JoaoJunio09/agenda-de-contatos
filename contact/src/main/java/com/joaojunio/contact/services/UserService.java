package com.joaojunio.contact.services;

import com.joaojunio.contact.controllers.PersonController;
import com.joaojunio.contact.controllers.UserController;
import com.joaojunio.contact.data.dto.*;
import com.joaojunio.contact.exceptions.NotFoundException;
import com.joaojunio.contact.exceptions.ObjectAlreadyExistsException;
import com.joaojunio.contact.exceptions.RequiredObjectIsNullException;
import com.joaojunio.contact.model.*;
import com.joaojunio.contact.model.enums.UserAdmin;
import com.joaojunio.contact.model.enums.UserStatus;
import com.joaojunio.contact.repositories.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.joaojunio.contact.mapper.ObjectMapper.parseObject;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    @Autowired
    UserRepository repository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    DailyRegistrationRepository dailyRegistrationRepository;

    @Autowired
    DailyLoginRepository dailyLoginRepository;

    @Autowired
    AddedContactsRepository addedContactsRepository;

    @Autowired
    PagedResourcesAssembler<UserResponseDTO> assembler;

    @Transactional(readOnly = true)
    public List<UserResponseAllDTO> findAll() {

        logger.info("Finds All User");

        return repository.findAll().stream().map(entity -> {
            UserResponseAllDTO dto = new UserResponseAllDTO();
            dto.setId(entity.getId());
            dto.setEmail(entity.getEmail());
            dto.setPassword(entity.getPassword());
            dto.setUserStatus(entity.getUserStatus());
            if (entity.getUserAdmin() != null) {
                dto.setUserAdmin(entity.getUserAdmin().getCode());
            }
            else {
                dto.setUserAdmin(null);
            }

            if (entity.getPerson() != null) {
                addPersonAll(entity, dto);
            }

            addContactsAll(entity.getContacts(), dto);
            return dto;
        })
        .toList();
    }

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<UserResponseDTO>> findAll(Pageable pageable) {

        logger.info("Finds All User Pageable");

        var list = repository.findAll(pageable);

        var usersWithLinks = list
            .map(entity -> {
                UserResponseDTO dto = new UserResponseDTO();
                dto.setId(entity.getId());
                dto.setEmail(entity.getEmail());
                dto.setUserStatus(entity.getUserStatus());
                if (entity.getUserAdmin() != null) {
                    dto.setUserAdmin(entity.getUserAdmin().getCode());
                }
                else {
                    dto.setUserAdmin(null);
                }

                if (entity.getPerson() != null) {
                    addPerson(entity, dto);
                }

                addContacts(entity.getContacts(), dto);
                addHateoasLinks(dto);
                return dto;
            });

        Link findAllLink = WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(UserController.class)
                .findAll(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    String.valueOf(pageable.getSort())))
                .withSelfRel();

        return assembler.toModel(
            usersWithLinks,
            findAllLink
        );
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {

        logger.info("Find a User");

        UserResponseDTO dto = new UserResponseDTO();
        User entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException(("Not Found this ID : " + id)));

        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setAdmin(entity.getUserAdmin().getCode());
        dto.setStatus(entity.getUserStatus().getCode());

        if (entity.getPerson() != null) {
            addPerson(entity, dto);
        }

        addContacts(entity.getContacts(), dto);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional(readOnly = true)
    public UserDetailsDTO detailsUser(Long id) {

        logger.info("Find a details User");

        User entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException(("Not Found this ID : " + id)));
        var dto = parseObject(entity, UserDetailsDTO.class);

        if (entity.getUserAdmin() != null) {
            dto.setUserAdmin(entity.getUserAdmin().getCode());
        }
        else {
            dto.setUserAdmin(null);
        }

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

        LocalDate date = LocalDate.now();

        DayOfWeek dayOfTheWeek = date.getDayOfWeek();
        int day = dayOfTheWeek.getValue();

        DailyRegistration register = new DailyRegistration(null, userSaved.getId(), day);
        dailyRegistrationRepository.save(register);

        var dto = parseObject(userSaved, UserResponseDTO.class);
        addHateoasLinks(dto);

        return dto;
    }

    @Transactional
    public UserResponseDTO update(UserUpdateRequestDTO userDTO) {

        logger.info("Update a User");

        var entity = repository.findById(userDTO.getId())
            .orElseThrow(() -> new NotFoundException(("Not Found this ID : " + userDTO.getId())));
        entity.setEmail(userDTO.getEmail());
        entity.setPassword(userDTO.getPassword());

        entity.setUserStatus(UserStatus.fromCode(userDTO.getStatus()));
        entity.setUserAdmin(UserAdmin.fromCode(userDTO.getAdmin()));

        entity.getPerson().setFirstName(userDTO.getPerson().getFirstName());
        entity.getPerson().setLastName(userDTO.getPerson().getLastName());
        entity.getPerson().setGender(userDTO.getPerson().getGender());
        entity.getPerson().setRg(userDTO.getPerson().getRg());
        entity.getPerson().setCpf(userDTO.getPerson().getCpf());
        entity.getPerson().setNumber(userDTO.getPerson().getNumber());
        entity.getPerson().setComplement(userDTO.getPerson().getComplement());
        entity.getPerson().setNationality(userDTO.getPerson().getNationality());

        UserResponseDTO dto = new UserResponseDTO();
        var saveObject = repository.save(entity);
        dto.setId(saveObject.getId());
        dto.setEmail(saveObject.getEmail());
        dto.setUserAdmin(saveObject.getUserAdmin().getCode());
        dto.setUserStatus(saveObject.getUserStatus());

        if (saveObject.getPerson() == null) {
            throw new IllegalArgumentException("Person entity in User is null.");
        }

        addPerson(saveObject, dto);
        addContacts(saveObject.getContacts(), dto);
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

    @Transactional(readOnly = true)
    public List<DailyRegistration> getAllDailyRegisters() {

        logger.info("Geting all Daily Registers for Users");

        updateDateValues();
        return dailyRegistrationRepository.findAll();
    }

    private void updateDateValues() {

        logger.info("Removing all daily registers");

        LocalDate date = LocalDate.now();

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Integer dayValue = dayOfWeek.getValue();

        dailyRegistrationRepository.findAll()
            .forEach(dailyRegister -> {
                if (dayValue == 7) {
                    if (dailyRegister.getDay() < 7) dailyRegistrationRepository.delete(dailyRegister);
                }
                if (dailyRegister.getDay() > dayValue) dailyRegistrationRepository.delete(dailyRegister);
            });
    }

    @Transactional(readOnly = true)
    public List<DailyLogin> getAllDailyLogins() {

        logger.info("Geting all Daily Logins for Users");

        return dailyLoginRepository.findAll();
    }

    public DailyLogin registerDailyLogin(DailyLogin dailyLogin) {

        logger.info("Register daily login for User");

        dailyLogin.setDate(new Date());

        repository.findById(dailyLogin.getUser_id())
                .orElseThrow(() -> new NotFoundException("Not Found THIS id : " + dailyLogin.getUser_id()));
        DailyLogin entity = new DailyLogin(null, dailyLogin.getUser_id(), dailyLogin.getDay(), dailyLogin.getDate());
        return dailyLoginRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<AddedContacts> getAllAddedContacts() {

        logger.info("Geting all Addeds Contacts for Users");

        return addedContactsRepository.findAll();
    }

    public AddedContacts registerAddedContacts(AddedContacts contact) {

        logger.info("Register added contact for User");

        if (contact == null) {
            throw new IllegalArgumentException("Object AddedContacts this is null");
        }
        return addedContactsRepository.save(contact);
    }

    private void addPerson(User entity, UserResponseDTO dto) {
        PersonResponseDTO personDTO = new PersonResponseDTO();
        personDTO.setId(entity.getPerson().getId());
        personDTO.setEmail(entity.getPerson().getEmail());
        personDTO.setGender(entity.getPerson().getGender());
        personDTO.setFirstName(entity.getPerson().getFirstName());
        personDTO.setLastName(entity.getPerson().getLastName());
        personDTO.setBirthDate(entity.getPerson().getBirthDate());
        personDTO.setNationality(entity.getPerson().getNationality());
        personDTO.setPhone(entity.getPerson().getPhone());
        dto.setPerson(personDTO);
    }

    private void addPersonAll(User entity, UserResponseAllDTO dto) {
        PersonResponseDTO personDTO = new PersonResponseDTO();
        personDTO.setId(entity.getPerson().getId());
        personDTO.setEmail(entity.getPerson().getEmail());
        personDTO.setGender(entity.getPerson().getGender());
        personDTO.setFirstName(entity.getPerson().getFirstName());
        personDTO.setLastName(entity.getPerson().getLastName());
        personDTO.setBirthDate(entity.getPerson().getBirthDate());
        personDTO.setNationality(entity.getPerson().getNationality());
        personDTO.setPhone(entity.getPerson().getPhone());
        dto.setPerson(personDTO);
    }

    private void addContacts(Set<Contact> collection, UserResponseDTO dto) {
        Set<ContactResponseDTO> contacts = new HashSet<>();
        for (Contact contact : collection) {
            ContactResponseDTO contactDTO = new ContactResponseDTO();
            contactDTO.setId(contact.getId());
            contactDTO.setTitle(contact.getTitle());
            contactDTO.setDescription(contactDTO.getDescription());
            contactDTO.setContact(contact.getContact());
            contacts.add(contactDTO);
        }
        dto.setContacts(contacts);
    }

    private void addContactsAll(Set<Contact> collection, UserResponseAllDTO dto) {
        Set<ContactResponseDTO> contacts = new HashSet<>();
        for (Contact contact : collection) {
            ContactResponseDTO contactDTO = new ContactResponseDTO();
            contactDTO.setId(contact.getId());
            contactDTO.setTitle(contact.getTitle());
            contactDTO.setDescription(contactDTO.getDescription());
            contactDTO.setContact(contact.getContact());
            contacts.add(contactDTO);
        }
        dto.setContacts(contacts);
    }

    private void addHateoasLinks(UserResponseDTO dto) {
        dto.add(linkTo(methodOn(UserController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).findAll(1, 12, "asc")).withRel("findAll").withType("GET"));
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
