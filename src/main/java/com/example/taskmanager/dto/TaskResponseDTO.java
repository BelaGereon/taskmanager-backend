package com.example.taskmanager.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public record TaskResponseDTO(int id, String title, String description) {
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
