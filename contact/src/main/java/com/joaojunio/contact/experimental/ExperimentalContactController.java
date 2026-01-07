package com.joaojunio.contact.experimental;

import com.joaojunio.contact.data.dto.ContactResponseDTO;
import com.joaojunio.contact.data.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/experimental")
public class ExperimentalContactController {

    @Autowired
    private ExperimentalContactService service;

    @PostMapping(
        value = "/findContactsBySearch",
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
    public ResponseEntity<List<ContactResponseDTO>> findContactsBySearch(@RequestBody ExperimentalContactSearchRequestDTO search) {
        return ResponseEntity.ok().body(service.findContactsBySearch(search));
    }
}
