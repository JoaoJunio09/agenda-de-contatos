package com.joaojunio.contact.controllers;

import com.joaojunio.contact.controllers.docs.UserControllerDocs;
import com.joaojunio.contact.data.dto.UserDetailsDTO;
import com.joaojunio.contact.data.dto.UserRequestDTO;
import com.joaojunio.contact.data.dto.UserResponseDTO;
import com.joaojunio.contact.data.dto.UserUpdateRequestDTO;
import com.joaojunio.contact.exceptions.NotFoundException;
import com.joaojunio.contact.exceptions.ObjectAlreadyExistsException;
import com.joaojunio.contact.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<UserResponseDTO>> findAll() {
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
}
