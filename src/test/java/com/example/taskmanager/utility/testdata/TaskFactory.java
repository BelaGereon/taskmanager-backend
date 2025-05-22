package com.example.taskmanager.utility.testdata;

import com.example.taskmanager.dto.TaskRequestDTO;
import com.example.taskmanager.dto.TaskResponseDTO;
import com.example.taskmanager.model.Task;

public class TaskFactory {

    public static Task createTaskObject(int id, String title, String description) {
        return new Task(id, title, description);
    }

    public static Task createTaskObject(String title, String description) {
        return new Task(0, title, description);
    }

    public static TaskRequestDTO createTaskRequestDto(String title, String description) {
        return new TaskRequestDTO(title, description);
    }

    public static TaskResponseDTO createTaskResponseDto(int id, String title, String description) {
        return new TaskResponseDTO(id, title, description);
    }
}
