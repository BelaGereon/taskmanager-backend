package com.example.taskmanager.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskRequestDTOTest {

    @Test
    void givenTitleAndDescription_whenConstructingRequestDTO_thenTitleAndDescriptionAreCorrect() {
        TaskRequestDTO taskRequestDTO1 = new TaskRequestDTO("DTO Title", "DTO Description");
        TaskRequestDTO taskRequestDTO2 =
                new TaskRequestDTO("Different DTO Title", "Different DTO Description");

        assertEquals("DTO Title", taskRequestDTO1.getTitle());
        assertEquals("DTO Description", taskRequestDTO1.getDescription());

        assertEquals("Different DTO Title", taskRequestDTO2.getTitle());
        assertEquals("Different DTO Description", taskRequestDTO2.getDescription());
    }
}
