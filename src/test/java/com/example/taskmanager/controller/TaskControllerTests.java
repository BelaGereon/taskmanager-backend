package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskRequestDTO;
import com.example.taskmanager.dto.TaskResponseDTO;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.utility.testdata.TaskFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.example.taskmanager.utility.testdata.TaskFactory.createTaskObject;
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
        Task task1 = new Task(1, "Title 1", "Description 1");
        Task task2 = new Task(2, "Title 2", "Description 2");
        Task task3 = new Task(3, "Title 3", "Description 3");

        when(mockTaskService.getAllTasks()).thenReturn(List.of(task1, task2, task3));

        when(mockTaskService.createTask(createTaskObject("Title 1", "Description 1"))).thenReturn(task1);
        when(mockTaskService.createTask(createTaskObject("Title 2", "Description 2"))).thenReturn(task2);
        when(mockTaskService.createTask(createTaskObject("Title 3", "Description 3"))).thenReturn(task3);

        when(mockTaskService.getTaskById(2)).thenReturn(task2);
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
    void shouldReturnCreatedTask() throws Exception {
        String taskToBeCreatedJson = """
            {
                "title": "Title 1",
                "description": "Description 1"
            }
        """;
        String createdTaskJson = """
            {
                "id": 1,
                "title": "Title 1",
                "description": "Description 1"
            }
        """;

        mockMvc.perform(post("/tasks")
                        .contentType("application/json")
                        .content(taskToBeCreatedJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(createdTaskJson));
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

        verify(mockTaskService).getTaskById(2);
    }

    @Test
    void shouldDeleteTask() throws Exception {
        Task taskToBeDeleted = TaskFactory.createTaskObject(42, "Task", "To delete");

        when(mockTaskService.getTaskById(42)).thenReturn(taskToBeDeleted);
        doNothing().when(mockTaskService).deleteTask(taskToBeDeleted);

        mockMvc.perform(delete("/tasks/42"))
                .andExpect(status().isNoContent());

        verify(mockTaskService).getTaskById(taskToBeDeleted.getId());
        verify(mockTaskService).deleteTask(taskToBeDeleted);
    }

    @Test
    void shouldUpdateTask() throws Exception {
        TaskResponseDTO updatedTask = new TaskResponseDTO(69, "Updated Title", "Old Description");

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
}
