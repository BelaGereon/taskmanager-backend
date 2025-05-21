package com.example.taskmanager.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskResponseDTOTest {

    @Test
    void givenParameters_whenConstructingResponseDTO_thenParametersAreCorrect() {
        TaskResponseDTO taskResponseDTO1 =
                new TaskResponseDTO(
                        1,
                        "Response DTO Title 1",
                        "Response DTO Description 1"
                );
        TaskResponseDTO taskResponseDTO2 =
                new TaskResponseDTO(
                        2,
                        "Response DTO Title 2",
                        "Response DTO Description 2"
                );

        assertEquals(1, taskResponseDTO1.id());
        assertEquals("Response DTO Title 1", taskResponseDTO1.title());
        assertEquals("Response DTO Description 1", taskResponseDTO1.description());

        assertEquals(2, taskResponseDTO2.id());
        assertEquals("Response DTO Title 2", taskResponseDTO2.title());
        assertEquals("Response DTO Description 2", taskResponseDTO2.description());
    }
}
