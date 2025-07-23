package com.joaojunio.contact.controllers;

import com.joaojunio.contact.controllers.docs.UserControllerDocs;
import com.joaojunio.contact.data.dto.*;
import com.joaojunio.contact.exceptions.NotFoundException;
import com.joaojunio.contact.exceptions.ObjectAlreadyExistsException;
import com.joaojunio.contact.model.AddedContacts;
import com.joaojunio.contact.model.DailyLogin;
import com.joaojunio.contact.model.DailyRegistration;
import com.joaojunio.contact.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/v1")
@Tag(name = "User", description = "Documentation of the User entity.")
public class UserController implements UserControllerDocs {

    @Autowired
    private UserService service;

    @GetMapping(
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<List<UserResponseAllDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(
        value = "/pageable",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<PagedModel<EntityModel<UserResponseDTO>>> findAll(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "12") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "email"));
        return ResponseEntity.ok(service.findAll(pageable));
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
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping(
        value = "/{id}/details",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<UserDetailsDTO> detailsUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.detailsUser(id));
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
    public ResponseEntity<?> create(@RequestBody UserRequestDTO userDTO, HttpServletRequest request) {
        try {
            return ResponseEntity.ok().body(service.create(userDTO, request));
        }
        catch (NotFoundException e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                    "title", "Não foi possível cadastrar.",
                    "message", e.getMessage()
                ));
        }
        catch (ObjectAlreadyExistsException e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                    "title", "Não foi possível cadastrar.",
                    "message", e.getMessage()
                ));
        }
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
    public ResponseEntity<UserResponseDTO> update(@RequestBody UserUpdateRequestDTO userDTO) {
        System.out.println(userDTO.getPerson().getGender());
        System.out.println(userDTO.getAdmin());
        return ResponseEntity.ok().body(service.update(userDTO));
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
        value = "/getDailyRegisters",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<List<DailyRegistration>> getAllDailyRegisters() {
        return ResponseEntity.ok().body(service.getAllDailyRegisters());
    }

    @GetMapping(
        value = "/getDailyLogins",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<List<DailyLogin>> getAllDailyLogins() {
        return ResponseEntity.ok().body(service.getAllDailyLogins());
    }

    @PostMapping(
        value = "/registerDailyLogin",
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
    public ResponseEntity<DailyLogin> registerDailyLogin(DailyLogin dailyLogin) {
        return ResponseEntity.ok().body(service.registerDailyLogin(dailyLogin));
    }

    @GetMapping(
        value = "/getAddedContacts",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    @Override
    public ResponseEntity<List<AddedContacts>> getAllAddedContacts() {
        return ResponseEntity.ok().body(service.getAllAddedContacts());
    }

    @PostMapping(
        value = "/registerAddedContact",
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
    public ResponseEntity<AddedContacts> registerAddedContacts(AddedContacts addedContacts) {
        return ResponseEntity.ok().body(service.registerAddedContacts(addedContacts));
    }
}
