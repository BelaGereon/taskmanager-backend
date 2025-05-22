package com.example.taskmanager.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface TaskDTO {

    default String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
