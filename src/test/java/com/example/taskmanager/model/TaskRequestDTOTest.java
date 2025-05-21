package com.example.taskmanager.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskRequestDTOTest {

    @Test
    void shouldCreateTaskRequestDTOWithTheCorrectTitle() {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO();

        assertEquals("DTO Title", taskRequestDTO.getTitle());
    }
}
