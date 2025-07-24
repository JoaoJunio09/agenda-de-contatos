package com.joaojunio.contact.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.joaojunio.contact.controllers.docs.ContactControllerDocs;
import com.joaojunio.contact.data.dto.ContactByUserResponseDTO;
import com.joaojunio.contact.data.dto.ContactRequestDTO;
import com.joaojunio.contact.data.dto.ContactResponseDTO;
import com.joaojunio.contact.data.dto.UserResponseDTO;
import com.joaojunio.contact.model.DailyContactRegistration;
import com.joaojunio.contact.model.DeletedContact;
import com.joaojunio.contact.model.EditedContact;
import com.joaojunio.contact.services.ContactService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequestMapping(value = "/api/contact/v1")
@Tag(name = "Contact", description = "Documentation of the User entity.")
public class ContactController implements ContactControllerDocs {

    @Autowired
    private ContactService service;

    @GetMapping(
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<List<ContactResponseDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(
        value = "/{id}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<ContactResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping(
        value = "/findContactsByUser/{id}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<List<ContactByUserResponseDTO>> findContactsByUser(Long id) {
        return ResponseEntity.ok().body(service.findByContactsByUser(id));
    }

    @PostMapping(
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        },
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<ContactResponseDTO> create(@RequestBody ContactRequestDTO contactDto) {
        return ResponseEntity.ok().body(service.create(contactDto));
    }

    @PutMapping(
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        },
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<ContactResponseDTO> update(@RequestBody ContactRequestDTO contactDto) {
        return ResponseEntity.ok().body(service.update(contactDto));
    }

    @DeleteMapping(
        value = "/{id}"
    )
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(
        value = "/getEditedContacts",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<List<EditedContact>> getAllEditedContacts() {
        return ResponseEntity.ok().body(service.getAllEditedContacts());
    }

    @PostMapping(
        value = "/registerEditedContact",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        },
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<EditedContact> registerEditedContact(@RequestBody EditedContact editedContact) {
        return ResponseEntity.ok().body(service.registerEditedContact(editedContact));
    }

    @GetMapping(
        value = "/getDeletedContacts",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<List<DeletedContact>> getAllDeletedContacts() {
        return ResponseEntity.ok().body(service.getAllDeletedContacts());
    }

    @PostMapping(
        value = "/registerDeletedContact",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        },
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<DeletedContact> registerDeletedContact(@RequestBody DeletedContact deletedContact) {
        return ResponseEntity.ok().body(service.registerDeletedContact(deletedContact));
    }

    @GetMapping(
        value = "/getDailyContactRegistration",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<List<DailyContactRegistration>> getAllDailyContactRegistration() {
        return ResponseEntity.ok().body(service.getAllDailyContactRegistration());
    }

    @GetMapping(
        value = "findContactsBySearch/{search}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<List<ContactResponseDTO>> findContactsBySearch(@PathVariable String search, @RequestBody UserResponseDTO user) {
        return ResponseEntity.ok().body(service.findContactsBySearch(search, user));
    }
}
