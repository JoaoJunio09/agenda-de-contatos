package com.joaojunio.contact.services;

import com.joaojunio.contact.controllers.ContactController;
import com.joaojunio.contact.data.dto.ContactRequestDTO;
import com.joaojunio.contact.data.dto.ContactResponseDTO;
import com.joaojunio.contact.exceptions.NotFoundException;
import com.joaojunio.contact.repositories.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joaojunio.contact.mapper.ObjectMapper.parseObject;
import static com.joaojunio.contact.mapper.ObjectMapper.parseListObjects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@Service
public class ContactService {

    private final Logger logger = LoggerFactory.getLogger(ContactService.class.getName());

    @Autowired
    ContactRepository repository;

    public List<ContactResponseDTO> findAll() {

        logger.info("Fiding All Contacts");

        var list = parseListObjects(repository.findAll(), ContactResponseDTO.class);
        list.forEach(this::addHateoasLinks);
        return list;
    }

    public ContactResponseDTO findById(Long id) {

        logger.info("Fiding a Contact");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not Found this ID : " + id));
        var dto = parseObject(entity, ContactResponseDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public ContactResponseDTO create(ContactRequestDTO contactDTO) {
        return null;
    }

    public ContactResponseDTO update(ContactRequestDTO contactDTO) {
        return null;
    }

    public void delete(Long id) {

    }

    private void addHateoasLinks(ContactResponseDTO dto) {
        dto.add(linkTo(methodOn(ContactController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(ContactController.class).findAll()).withRel("findAll").withType("GET"));
    }
}
