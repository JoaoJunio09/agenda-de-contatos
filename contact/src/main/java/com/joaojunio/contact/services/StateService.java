package com.joaojunio.contact.services;

import com.joaojunio.contact.data.dto.StateDTO;
import com.joaojunio.contact.model.State;
import com.joaojunio.contact.repositories.StateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.joaojunio.contact.mapper.ObjectMapper.parseObject;
import static com.joaojunio.contact.mapper.ObjectMapper.parseListObjects;

@Service
public class StateService {

    private final Logger logger = LoggerFactory.getLogger(StateService.class.getName());

    @Autowired
    StateRepository repository;

    public List<StateDTO> findAll() {

        logger.info("Finds all State");

        return parseListObjects(repository.findAll(), StateDTO.class);
    }

    public StateDTO findById(Long id) {

        logger.info("Finds by ID State");

        var entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Not Record this ID : " + id));
        return parseObject(entity, StateDTO.class);
    }

    public StateDTO create(StateDTO stateDTO) {

        logger.info("Create a new State");

        var state = parseObject(stateDTO, State.class);
        return parseObject(repository.save(state), StateDTO.class);
    }

    public StateDTO update(StateDTO stateDTO) {

        logger.info("Update a State");

        var entity = repository.findById(stateDTO.getId())
            .orElseThrow(() -> new RuntimeException("Not Record this ID : " + stateDTO.getId()));
        entity.setName(stateDTO.getName());
        entity.setAcronym(stateDTO.getAcronym());

        return parseObject(repository.save(entity), StateDTO.class);
    }

    public void delete(Long id) {

        logger.info("Delete a State");

        var state = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Not Record this ID : " + id));
        repository.delete(state);
    }
}
