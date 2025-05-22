package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskRequestDTO;
import com.example.taskmanager.dto.TaskResponseDTO;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.example.taskmanager.utility.testdata.TaskFactory.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService mockTaskService;

    @BeforeEach
    public void setupMocks() {
        TaskRequestDTO task1Request = createTaskRequestDto("Title 1", "Description 1");
        TaskResponseDTO task1Response = createTaskResponseDto(1, "Title 1", "Description 1");

        TaskRequestDTO task2Request = createTaskRequestDto("Title 2", "Description 2");
        TaskResponseDTO task2Response = createTaskResponseDto(2, "Title 2", "Description 2");

        TaskRequestDTO task3Request = createTaskRequestDto("Title 3", "Description 3");
        TaskResponseDTO task3Response = createTaskResponseDto(3, "Title 3", "Description 3");

        Task task1 = createTaskObject(1, "Title 1", "Description 1");
        Task task2 = createTaskObject(2, "Title 2", "Description 2");
        Task task3 = createTaskObject(3, "Title 3", "Description 3");

        when(mockTaskService.getAllTasks()).thenReturn(List.of(task1, task2, task3));

        when(mockTaskService.createTask(task1Request)).thenReturn(task1Response);
        when(mockTaskService.createTask(task2Request)).thenReturn(task2Response);
        when(mockTaskService.createTask(task3Request)).thenReturn(task3Response);

        when(mockTaskService.getTaskById(2)).thenReturn(task2);
        when(mockTaskService.getTaskByIdAsDto(2)).thenReturn(task2Response);
        when(mockTaskService.getAllTasksAsDTOs()).thenReturn(List.of(task1Response, task2Response, task3Response));

    }

    @Test
    void shouldReturnAllTasks() throws Exception {
        String expectedJson = """
            [
                {
                    "id": 1,
                    "title": "Title 1",
                    "description": "Description 1"
                },
                {
                    "id": 2,
                    "title": "Title 2",
                    "description": "Description 2"
                },
                {
                    "id": 3,
                    "title": "Title 3",
                    "description": "Description 3"
                }
            ]
        """;

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldReturnTheCorrectTaskAfterTaskCreation() throws Exception {
        String task1Json = """
            {
                "title": "Title 1",
                "description": "Description 1"
            }
        """;

        String task2Json = """
            {
                "title": "Title 2",
                "description": "Description 2"
            }
        """;

        String expectedTask1Json = """
            {
                "id": 1,
                "title": "Title 1",
                "description": "Description 1"
            }
        """;

        String expectedTask2Json = """
            {
                "id": 2,
                "title": "Title 2",
                "description": "Description 2"
            }
        """;

        mockMvc.perform(post("/tasks")
                        .contentType("application/json")
                        .content(task1Json))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedTask1Json));

        mockMvc.perform(post("/tasks")
                        .contentType("application/json")
                        .content(task2Json))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedTask2Json));
    }

    @Test
    void shouldReturnCorrectTaskById() throws Exception {
        String expectedTaskJson = """
            {
                "id": 2,
                "title": "Title 2",
                "description": "Description 2"
            }
        """;

        mockMvc.perform(get("/tasks/2"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedTaskJson));

        verify(mockTaskService).getTaskByIdAsDto(2);
    }

    @Test
    void shouldDeleteTask() throws Exception {
        Task taskToBeDeleted =createTaskObject(42, "Task", "To delete");

        when(mockTaskService.getTaskById(42)).thenReturn(taskToBeDeleted);
        doNothing().when(mockTaskService).deleteTask(taskToBeDeleted);

        mockMvc.perform(delete("/tasks/42"))
                .andExpect(status().isNoContent());

        verify(mockTaskService).getTaskById(taskToBeDeleted.getId());
        verify(mockTaskService).deleteTask(taskToBeDeleted);
    }

    @Test
    void shouldUpdateTask() throws Exception {
        TaskResponseDTO updatedTask = createTaskResponseDto(69, "Updated Title", "Old Description");

        when(mockTaskService.updateTask(eq(69), any(TaskRequestDTO.class))).thenReturn(updatedTask);

        String updatedTaskJson = """
                {
                    "id": 69,
                    "title": "Updated Title",
                    "description": "Old Description"
                }
            """;

        mockMvc.perform(put("/tasks/69")
                        .contentType("application/json")
                        .content(updatedTaskJson))
                .andExpect(status().isOk())
                .andExpect(content().json(updatedTaskJson));
    }

    @Test
    void givenRequestDto_whenCreatingTask_thenReturnResponseDto() throws Exception {
        TaskRequestDTO requestDTO = createTaskRequestDto("New Task", "Some Description");
        TaskResponseDTO responseDTO = createTaskResponseDto(1, "New Task", "Some Description");

        when(mockTaskService.createTask(requestDTO)).thenReturn(responseDTO);

        String requestJson = """
            {
                "title": "New Task",
                "description": "Some Description"
            }
        """;

        String responseJson = """
                {
                    "id": 1,
                    "title": "New Task",
                    "description": "Some Description"
                }
            """;

        mockMvc.perform(post("/tasks")
                        .contentType("application/json")
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson));
    }
}
