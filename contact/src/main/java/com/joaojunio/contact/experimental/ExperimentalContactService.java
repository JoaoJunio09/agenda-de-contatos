package com.joaojunio.contact.experimental;

import com.joaojunio.contact.controllers.ContactController;
import com.joaojunio.contact.controllers.UserController;
import com.joaojunio.contact.data.dto.ContactResponseDTO;
import com.joaojunio.contact.data.dto.PersonResponseDTO;
import com.joaojunio.contact.data.dto.UserResponseDTO;
import com.joaojunio.contact.model.Contact;
import com.joaojunio.contact.model.User;
import com.joaojunio.contact.repositories.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ExperimentalContactService {

    private Logger logger = LoggerFactory.getLogger(ExperimentalContactService.class.getName());

    @Autowired
    ContactRepository repository;

    @Transactional(readOnly = true)
    public List<ContactResponseDTO> findContactsBySearch(ExperimentalContactSearchRequestDTO experimentalContactSearchRequestDTO) {

        logger.info("Finds Contacts by Search");

        List<Contact> list = repository.findContactsBySearch(
            experimentalContactSearchRequestDTO.getSearch(),
            experimentalContactSearchRequestDTO.getUserId()
        );
        return list.stream()
            .map(entity -> {
                ContactResponseDTO dto = new ContactResponseDTO();
                dto.setId(entity.getId());
                dto.setTitle(entity.getTitle());
                dto.setContact(entity.getContact());
                dto.setDescription(entity.getDescription());

                if (entity.getUser() == null) {
                    throw new IllegalArgumentException("User entity by Contact is null.");
                }

                addUser(entity.getUser(), dto);
                addHateoasLinks(dto);
                return dto;
            })
            .toList();
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
