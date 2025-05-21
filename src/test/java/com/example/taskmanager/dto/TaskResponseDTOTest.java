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
                        "Response DTO Description 2"
                );
        TaskResponseDTO taskResponseDTO2 =
                new TaskResponseDTO(
                        2,
                        "Response DTO Title 2",
                        "Response DTO Description 2"
                );

        assertEquals(1, taskResponseDTO1.getId());
        assertEquals(1, taskResponseDTO1.getId());
        assertEquals(1, taskResponseDTO1.getId());

        assertEquals(2, taskResponseDTO2.getId());
        assertEquals("Response DTO Title 2", taskResponseDTO2.getTitle());
        assertEquals("Response DTO Description 2", taskResponseDTO2.getDescription());
    }
}
