package com.joaojunio.contact.controllers;

import com.joaojunio.contact.data.dto.ContactResponseDTO;
import com.joaojunio.contact.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/contact/v1")
public class ContactController {

    @Autowired
    private ContactService service;

    @GetMapping
    public ResponseEntity<List<ContactResponseDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }
}
