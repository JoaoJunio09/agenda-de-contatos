package com.joaojunio.contact.controllers;

import com.joaojunio.contact.data.dto.StateDTO;
import com.joaojunio.contact.model.State;
import com.joaojunio.contact.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequestMapping("/api/state/v1")
public class StateController {

    @Autowired
    private StateService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StateDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<StateDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<StateDTO> create(@RequestBody StateDTO stateDTO) {
        return ResponseEntity.ok(service.create(stateDTO));
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<StateDTO> update(@RequestBody StateDTO stateDTO) {
        return ResponseEntity.ok(service.update(stateDTO));
    }

    @DeleteMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
