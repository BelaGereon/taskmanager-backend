package com.example.taskmanager.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskRequestDTOTest {

    @Test
    void givenTitle_whenConstructingRequestDTO_thenTitleIsCorrect() {
        TaskRequestDTO taskRequestDTO1 = new TaskRequestDTO("DTO Title");
        TaskRequestDTO taskRequestDTO2 = new TaskRequestDTO("Different DTO Title");

        assertEquals("DTO Title", taskRequestDTO1.getTitle());
        assertEquals("Different DTO Title", taskRequestDTO2.getTitle());
    }
}
