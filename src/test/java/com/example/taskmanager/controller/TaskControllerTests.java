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

import static com.example.taskmanager.utility.JsonUtils.toJson;
import static com.example.taskmanager.utility.testdata.TaskFactory.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class TaskControllerTests {

    public static final TaskRequestDTO TASK_REQUEST_DTO_1 = createTaskRequestDto("Title 1", "Description 1");
    public static final String TASK_REQUEST_DTO_1_JSON = toJson(TASK_REQUEST_DTO_1);
    public static final TaskRequestDTO TASK_REQUEST_DTO_2 = createTaskRequestDto("Title 2", "Description 2");
    public static final TaskResponseDTO TASK_RESPONSE_DTO_1 = createTaskResponseDto(1, "Title 1", "Description 1");
    public static final String TASK_RESPONSE_DTO_1_JSON = toJson(TASK_RESPONSE_DTO_1);
    public static final TaskResponseDTO TASK_RESPONSE_DTO_2 = createTaskResponseDto(2, "Title 2", "Description 2");
    public static final String TASK_RESPONSE_DTO_2_JSON = toJson(TASK_RESPONSE_DTO_2);
    public static final TaskResponseDTO TASK_RESPONSE_DTO_3 = createTaskResponseDto(3, "Title 3", "Description 3");
    public static final List<TaskResponseDTO> TASK_RESPONSE_DTO_LIST = List.of(TASK_RESPONSE_DTO_1, TASK_RESPONSE_DTO_2, TASK_RESPONSE_DTO_3);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    @Autowired
    private TaskService mockTaskService;

    @Test
    void shouldReturnAllTasks() throws Exception {
        mockGetAllTasksAsDTOs();

        String expectedJson = toJson(TASK_RESPONSE_DTO_LIST);

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
                        .content(TASK_REQUEST_DTO_1_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(TASK_RESPONSE_DTO_1_JSON));

        mockMvc.perform(post("/tasks")
                        .contentType("application/json")
                        .content(toJson(TASK_REQUEST_DTO_2)))
                .andExpect(status().isCreated())
                .andExpect(content().json(TASK_RESPONSE_DTO_2_JSON));
    }

    @Test
    void givenRequestWithId_whenGettingTaskById_thenReturnCorrectTaskById() throws Exception {
        mockGetTaskByIdAsDto(1, TASK_RESPONSE_DTO_1);
        mockGetTaskByIdAsDto(2, TASK_RESPONSE_DTO_2);

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(TASK_RESPONSE_DTO_1_JSON));
        verify(mockTaskService).getTaskByIdAsDto(1);

        mockMvc.perform(get("/tasks/2"))
                .andExpect(status().isOk())
                .andExpect(content().json(TASK_RESPONSE_DTO_2_JSON));
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
                        .content(toJson(updatedTask)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(updatedTask)));
    }

    @Test
    void givenRequestDto_whenCreatingTask_thenReturnResponseDto() throws Exception {
        TaskRequestDTO requestDTO = createTaskRequestDto("New Task", "Some Description");
        TaskResponseDTO responseDTO = createTaskResponseDto(1, "New Task", "Some Description");

        mockCreateTask(requestDTO, responseDTO);

        mockMvc.perform(post("/tasks")
                        .contentType("application/json")
                        .content(toJson(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(toJson(responseDTO)));
    }

    private void mockGetAllTasksAsDTOs() {
        when(mockTaskService.getAllTasksAsDTOs()).thenReturn(TASK_RESPONSE_DTO_LIST);
    }

    private void mockCreateTask(TaskRequestDTO requestDto, TaskResponseDTO responseDTO) {
        when(mockTaskService.createTask(requestDto)).thenReturn(responseDTO);
    }

    private void mockGetTaskByIdAsDto(int id, TaskResponseDTO responseDTO) {
        when(mockTaskService.getTaskByIdAsDto(id)).thenReturn(responseDTO);
    }
}
