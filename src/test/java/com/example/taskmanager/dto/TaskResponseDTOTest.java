package com.example.taskmanager.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Test
    void givenTitleAndDescription_whenConvertingToJson_thenReturnValidJsonString() throws JsonProcessingException {
        TaskResponseDTO requestDTO = new TaskResponseDTO(1, "Title", "Description");

        String expectedJson = "{\"id\":1,\"title\":\"Title\",\"description\":\"Description\"}";

        assertEquals(expectedJson, requestDTO.toJson());
    }

    @Test
    void givenTitleAndDescription_whenConvertingToJson_thenReturnCorrectValuesInJsonString() throws JsonProcessingException {
        int firstId = 1;
        String firstTitle = "First Title";
        String firstDescr = "First Description";

        int secondId = 2;
        String secondTitle = "Second Title";
        String secondDescr = "Second Description";

        TaskResponseDTO firstRequestDTO = new TaskResponseDTO(firstId, firstTitle, firstDescr);
        TaskResponseDTO secondRequestDTO = new TaskResponseDTO(secondId, secondTitle, secondDescr);

        String expectedJsonFirstRequest = "{" +
                "\"id\":" + firstId + "," +
                "\"title\":\"" + firstTitle + "\"," +
                "\"description\":\"" + firstDescr + "\"" +
                "}";

        String expectedJsonSecondRequest = "{" +
                "\"id\":" + secondId + "," +
                "\"title\":\"" + secondTitle + "\"," +
                "\"description\":\"" + secondDescr + "\"" +
                "}";

        assertEquals(expectedJsonFirstRequest, firstRequestDTO.toJson());
        assertEquals(expectedJsonSecondRequest, secondRequestDTO.toJson());
    }
}
