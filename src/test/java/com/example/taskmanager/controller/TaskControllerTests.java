package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskRequestDTO;
import com.example.taskmanager.dto.TaskResponseDTO;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
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

    public static final TaskRequestDTO TASK_REQUEST_DTO_1 = createTaskRequestDto("Title 1", "Description 1");
    public static final TaskRequestDTO TASK_REQUEST_DTO_2 = createTaskRequestDto("Title 2", "Description 2");
    public static final TaskResponseDTO TASK_RESPONSE_DTO_1 = createTaskResponseDto(1, "Title 1", "Description 1");
    public static final TaskResponseDTO TASK_RESPONSE_DTO_2 = createTaskResponseDto(2, "Title 2", "Description 2");
    public static final TaskResponseDTO TASK_RESPONSE_DTO_3 = createTaskResponseDto(3, "Title 3", "Description 3");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    @Autowired
    private TaskService mockTaskService;

    @Test
    void shouldReturnAllTasks() throws Exception {
        mockGetAllTasksAsDtos();

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
        mockCreateTask(TASK_REQUEST_DTO_1, TASK_RESPONSE_DTO_1);
        mockCreateTask(TASK_REQUEST_DTO_2, TASK_RESPONSE_DTO_2);

        mockMvc.perform(post("/tasks")
                        .contentType("application/json")
                        .content(TASK_REQUEST_DTO_1.toJson()))
                .andExpect(status().isCreated())
                .andExpect(content().json(TASK_RESPONSE_DTO_1.toJson()));

        mockMvc.perform(post("/tasks")
                        .contentType("application/json")
                        .content(TASK_REQUEST_DTO_2.toJson()))
                .andExpect(status().isCreated())
                .andExpect(content().json(TASK_RESPONSE_DTO_2.toJson()));
    }

    @Test
    void givenRequestWithId_whenGettingTaskById_thenReturnCorrectTaskById() throws Exception {
        mockGetTaskByIdAsDto(1, TASK_RESPONSE_DTO_1);
        mockGetTaskByIdAsDto(2, TASK_RESPONSE_DTO_2);

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(TASK_RESPONSE_DTO_1.toJson()));
        verify(mockTaskService).getTaskByIdAsDto(1);

        mockMvc.perform(get("/tasks/2"))
                .andExpect(status().isOk())
                .andExpect(content().json(TASK_RESPONSE_DTO_2.toJson()));
        verify(mockTaskService).getTaskByIdAsDto(2);
    }

    @Test
    void shouldDeleteTask() throws Exception {
        Task taskToBeDeleted = createTaskObject(42, "Task", "To delete");

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

        mockMvc.perform(put("/tasks/69")
                        .contentType("application/json")
                        .content(updatedTask.toJson()))
                .andExpect(status().isOk())
                .andExpect(content().json(updatedTask.toJson()));
    }

    @Test
    void givenRequestDto_whenCreatingTask_thenReturnResponseDto() throws Exception {
        TaskRequestDTO requestDTO = createTaskRequestDto("New Task", "Some Description");
        TaskResponseDTO responseDTO = createTaskResponseDto(1, "New Task", "Some Description");

        mockCreateTask(requestDTO, responseDTO);

        mockMvc.perform(post("/tasks")
                        .contentType("application/json")
                        .content(requestDTO.toJson()))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseDTO.toJson()));
    }

    private void mockGetAllTasksAsDtos() {
        when(mockTaskService.getAllTasksAsDTOs()).thenReturn(List.of(TASK_RESPONSE_DTO_1, TASK_RESPONSE_DTO_2, TASK_RESPONSE_DTO_3));
    }

    private void mockCreateTask(TaskRequestDTO requestDto, TaskResponseDTO responseDTO) {
        when(mockTaskService.createTask(requestDto)).thenReturn(responseDTO);
    }

    private void mockGetTaskByIdAsDto(int id, TaskResponseDTO responseDTO) {
        when(mockTaskService.getTaskByIdAsDto(id)).thenReturn(responseDTO);
    }
}
