package com.joaojunio.contact.services;

import com.joaojunio.contact.data.dto.ContactResponseDTO;
import com.joaojunio.contact.repositories.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    private final Logger logger = LoggerFactory.getLogger(ContactService.class.getName());

    @Autowired
    ContactRepository repository;

    public List<ContactResponseDTO> findAll() {
        return mockList();
    }

    private List<ContactResponseDTO> mockList() {
        List<ContactResponseDTO> list = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            list.add(new ContactResponseDTO(i + 1L, "Title 1", "Description 1", "Contact 1", null));
        }
        return list;
    }
}
