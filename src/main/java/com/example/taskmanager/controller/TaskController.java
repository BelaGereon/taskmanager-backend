package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskRequestDTO;
import com.example.taskmanager.dto.TaskResponseDTO;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/tasks/{id}")
    TaskResponseDTO getTaskById(@PathVariable int id) {
        return taskService.getTaskByIdAsDto(id);
    }

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    TaskResponseDTO createTask(@RequestBody TaskRequestDTO task) {
        return taskService.createTask(task);
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTask(@PathVariable int id) {
        Task taskToBeDeleted = taskService.getTaskById(id);
        taskService.deleteTask(taskToBeDeleted);
    }

    @PutMapping("/tasks/{id}")
    TaskResponseDTO updateTask(@PathVariable int id, @RequestBody TaskRequestDTO updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }
}
