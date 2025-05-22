package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.example.taskmanager.utility.JsonUtils.toJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskService taskService;

    @AfterEach
    void cleanInMemoryDB() {
        taskService.resetCounterAndTasksList();
    }

    @Test
    void shouldCreateTask() throws Exception {
        Task task = new Task(1, "Integration Title", "Integration Description");
        sendPostRequestWithTaskObject(task);
    }

    @Test
    void shouldReturnListOfAllCreatedTasks() throws Exception {
        List<Task> listOfCreatedTasks = setupTasks();

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(listOfCreatedTasks)));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        Task taskToBeUpdated = new Task(1, "Original Title", "Original Description");
        sendPostRequestWithTaskObject(taskToBeUpdated);

        Task updatedTask = new Task(1, "Updated Title", "Updated Description");

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(updatedTask)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(updatedTask)));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

    }

    @Test
    void shouldDeleteTask() throws Exception {
        List<Task> listOfCreatedTasks = setupTasks();

        assertEquals(3, listOfCreatedTasks.size());

        mockMvc.perform(delete("/tasks/2"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    private List<Task> setupTasks() throws Exception {
        Task task1 = new Task(1, "Integration Title 1", "Integration Description 1");
        sendPostRequestWithTaskObject(task1);

        Task task2 = new Task(2, "Integration Title 2", "Integration Description 2");
        sendPostRequestWithTaskObject(task2);

        Task task3 = new Task(3, "Integration Title 3", "Integration Description 3");
        sendPostRequestWithTaskObject(task3);

        return List.of(task1, task2, task3);
    }

    private void sendPostRequestWithTaskObject(Task task) throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.description").value(task.getDescription()));
    }
}
