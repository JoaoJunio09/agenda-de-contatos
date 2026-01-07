package com.joaojunio.contact.services;

import com.joaojunio.contact.controllers.ContactController;
import com.joaojunio.contact.controllers.UserController;
import com.joaojunio.contact.data.dto.*;
import com.joaojunio.contact.exceptions.NotFoundException;
import com.joaojunio.contact.exceptions.ObjectAlreadyExistsException;
import com.joaojunio.contact.model.*;
import com.joaojunio.contact.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.joaojunio.contact.mapper.ObjectMapper.parseObject;
import static com.joaojunio.contact.mapper.ObjectMapper.parseListObjects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    private final Logger logger = LoggerFactory.getLogger(ContactService.class.getName());

    @Autowired
    ContactRepository repository;

    @Autowired
    EditedContactRepository editedContactRepository;

    @Autowired
    DeletedContactRepository deletedContactRepository;

    @Autowired
    DailyContactRegistrationRepository dailyContactRegistrationRepository;

    @Transactional(readOnly = true)
    public List<ContactResponseDTO> findAll() {

        logger.info("Fiding All Contacts");

        var list = repository.findAll();

        return list.stream().map(entity -> {
            ContactResponseDTO dto = new ContactResponseDTO();
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setContact(entity.getContact());

            if (entity.getUser() == null) {
                throw new IllegalArgumentException("User entity by Contact is null.");
            }

            addUser(entity.getUser(), dto);
            addHateoasLinks(dto);
            return dto;
        })
        .toList();
    }

    @Transactional(readOnly = true)
    public ContactResponseDTO findById(Long id) {

        logger.info("Fiding a Contact");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not Found this ID : " + id));

        ContactResponseDTO dto = new ContactResponseDTO();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContact(entity.getContact());

        if (entity.getUser() == null) {
            throw new IllegalArgumentException("User entity by Contact is null.");
        }

        addUser(entity.getUser(), dto);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ContactByUserResponseDTO> findByContactsByUser(Long id) {

        logger.info("Finding Contacts by User");

        return parseListObjects(repository.findContactByUser(id), ContactByUserResponseDTO.class);
    }

    public ContactResponseDTO create(ContactRequestDTO contactDTO) {

        logger.info("Creating a new Contact");

        var list = repository.findContactByUser(contactDTO.getUser().getId());

        list.forEach(contact -> {
            if (contactDTO.getContact().equalsIgnoreCase(contact.getContact())) {
                throw new ObjectAlreadyExistsException(
                    "O Usuário : " + contactDTO.getUser().getPerson().getFirstName() + " já possui este contato adicionado."
                );
            }
        });

        var entity = parseObject(contactDTO, Contact.class);
        var dto = parseObject(repository.save(entity), ContactResponseDTO.class);

        LocalDate date = LocalDate.now();

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Integer day = dayOfWeek.getValue();

        dailyContactRegistrationRepository.save(new DailyContactRegistration(
            null, contactDTO.getUser().getId(), day
        ));

        addHateoasLinks(dto);
        return dto;
    }

    public ContactResponseDTO update(ContactRequestDTO contactDTO) {

        logger.info("Updating a Contact");
        
        var entity = repository.findById(contactDTO.getId())
            .orElseThrow(() -> new NotFoundException("Not found this ID : " + contactDTO.getId()));
        System.out.println(contactDTO.getId());
        entity.setTitle(contactDTO.getTitle());
        entity.setDescription(contactDTO.getDescription());
        entity.setContact(contactDTO.getContact());

        var dto = parseObject(repository.save(entity), ContactResponseDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {

        logger.info("Deleting one Contact by ID");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found Contact this ID : " + id));
        repository.delete(entity);
    }

    public List<EditedContact> getAllEditedContacts() {

        logger.info("Getting all edited contacts");
        return editedContactRepository.findAll();
    }

    public EditedContact registerEditedContact(EditedContact editedContact) {

        logger.info("Creating a new edited contact");
        return editedContactRepository.save(editedContact);
    }

    public List<DeletedContact> getAllDeletedContacts() {

        logger.info("Getting all deleted contacts");
        return deletedContactRepository.findAll();
    }

    public DeletedContact registerDeletedContact(DeletedContact deletedContact) {

        logger.info("Creating a new deleted contact");
        return deletedContactRepository.save(deletedContact);
    }

    @Transactional(readOnly = true)
    public List<DailyContactRegistration> getAllDailyContactRegistration() {

        logger.info("Getting all daily registration of Contact Entity.");
        return dailyContactRegistrationRepository.findAll();
    }

    private void addUser(User entity, ContactResponseDTO dto) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(entity.getPerson().getId());
        userDTO.setEmail(entity.getPerson().getEmail());
        userDTO.setUserAdmin(entity.getUserAdmin().getCode());
        userDTO.setUserStatus(entity.getUserStatus());

        if (entity != null) {
            PersonResponseDTO personDTO = new PersonResponseDTO();
            personDTO.setId(entity.getPerson().getId());
            personDTO.setEmail(entity.getPerson().getEmail());
            personDTO.setGender(entity.getPerson().getGender());
            personDTO.setFirstName(entity.getPerson().getFirstName());
            personDTO.setLastName(entity.getPerson().getLastName());
            personDTO.setBirthDate(entity.getPerson().getBirthDate());
            personDTO.setNationality(entity.getPerson().getNationality());
            personDTO.setPhone(entity.getPerson().getPhone());
            userDTO.setPerson(personDTO);
        }
        dto.setUser(userDTO);
    }

    private void addHateoasLinks(ContactResponseDTO dto) {
        dto.add(linkTo(methodOn(ContactController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(ContactController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(ContactController.class).create(null)).withRel("create").withType("POST"));

        if (dto.getUser() != null) {
            dto.getUser().add(linkTo(methodOn(UserController.class).findById(dto.getId())).withSelfRel().withType("GET"));
            dto.getUser().add(linkTo(methodOn(UserController.class).findAll(1, 12, "asc")).withRel("findAll").withType("GET"));
            dto.getUser().add(linkTo(methodOn(UserController.class).create(null, null)).withRel("create").withType("POST"));
            dto.getUser().add(linkTo(methodOn(UserController.class).update(null)).withRel("update").withType("PUT"));
            dto.getUser().add(linkTo(methodOn(UserController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        }
    }
}
