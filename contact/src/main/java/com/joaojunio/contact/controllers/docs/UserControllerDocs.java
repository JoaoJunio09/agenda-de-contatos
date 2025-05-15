package com.joaojunio.contact.controllers.docs;

import com.joaojunio.contact.data.dto.UserDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserControllerDocs {

    ResponseEntity<List<UserDTO>> findAll();

    ResponseEntity<UserDTO> findById(@PathVariable Long id);

    ResponseEntity<?> create(@RequestBody UserDTO userDTO);
}
