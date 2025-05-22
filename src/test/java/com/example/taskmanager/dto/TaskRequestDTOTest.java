package com.example.taskmanager.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskRequestDTOTest {

    @Test
    void givenTitleAndDescription_whenConstructingRequestDTO_thenTitleAndDescriptionAreCorrect() {
        TaskRequestDTO taskRequestDTO1 = new TaskRequestDTO("DTO Title", "DTO Description");
        TaskRequestDTO taskRequestDTO2 =
                new TaskRequestDTO("Different DTO Title", "Different DTO Description");

        assertEquals("DTO Title", taskRequestDTO1.title());
        assertEquals("DTO Description", taskRequestDTO1.description());

        assertEquals("Different DTO Title", taskRequestDTO2.title());
        assertEquals("Different DTO Description", taskRequestDTO2.description());
    }

    @Test
    void givenTitleAndDescription_whenConvertingToJson_thenReturnValidJsonString() throws JsonProcessingException {
        TaskRequestDTO requestDTO = new TaskRequestDTO("Title", "Description");

        String expectedJson = "{\"title\":\"Title\",\"description\":\"Description\"}";

        assertEquals(expectedJson, requestDTO.toJson());
    }

    @Test
    void givenTitleAndDescription_whenConvertingToJson_thenReturnCorrectValuesInJsonString() throws JsonProcessingException {
        String firstTitle = "First Title";
        String firstDescr = "First Description";
        String secondTitle = "Second Title";
        String secondDescr = "Second Description";

        TaskRequestDTO firstRequestDTO = new TaskRequestDTO(firstTitle, firstDescr);
        TaskRequestDTO secondRequestDTO = new TaskRequestDTO(secondTitle, secondDescr);

        String expectedJsonFirstRequest = "{" +
                "\"title\":\"" + firstTitle +
                "\",\"description\":\"" + firstDescr + "\"" +
                "}";

        String expectedJsonSecondRequest = "{" +
                "\"title\":\"" + secondTitle +
                "\",\"description\":\"" + secondDescr + "\"" +
                "}";

        assertEquals(expectedJsonFirstRequest, firstRequestDTO.toJson());
        assertEquals(expectedJsonSecondRequest, secondRequestDTO.toJson());
    }
}
