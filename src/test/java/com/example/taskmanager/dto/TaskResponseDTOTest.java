package com.example.taskmanager.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskResponseDTOTest {

    @Test
    void givenId_whenConstructingResponseDTO_thenIdIsCorrect() {
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();

        assertEquals(1, taskResponseDTO.getId());
    }
}
