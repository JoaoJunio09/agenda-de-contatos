package com.joaojunio.contact.controllers;

import com.joaojunio.contact.data.dto.UserDTO;
import com.joaojunio.contact.exceptions.NotFoundException;
import com.joaojunio.contact.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/v1")
public class UserController implements com.joaojunio.contact.controllers.docs.UserControllerDocs {

    @Autowired
    private UserService service;

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Override
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Override
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @CrossOrigin("http://localhost:5050")
    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Override
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok().body(service.create(userDTO));
        }
        catch (NotFoundException e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                    "title", "Não foi possível cadastrar.",
                    "message", e.getMessage()
                ));
        }
    }
}
