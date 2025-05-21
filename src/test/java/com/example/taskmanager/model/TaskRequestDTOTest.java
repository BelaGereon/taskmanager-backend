package com.example.taskmanager.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskRequestDTOTest {

    @Test
    void shouldCreateTaskRequestDTOWithTheCorrectTitle() {
        TaskRequestDTO taskRequestDTO1 = new TaskRequestDTO();
        TaskRequestDTO taskRequestDTO2 = new TaskRequestDTO();

        assertEquals("DTO Title", taskRequestDTO1.getTitle());
        assertEquals("Different  DTO Title", taskRequestDTO2.getTitle());
    }
}
