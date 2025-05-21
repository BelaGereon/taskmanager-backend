package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequestDTO;
import com.example.taskmanager.dto.TaskResponseDTO;
import com.example.taskmanager.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@Service
public class TaskService {

    private final AtomicInteger idCounter = new AtomicInteger(0);
    private final List<Task> tasks = new ArrayList<>();

    public List<Task> getAllTasks() {
        return List.copyOf(tasks);
    }

    public List<TaskResponseDTO> getAllTasksAsDTOs() {
        return tasks.stream()
                .map(convertTaskToResponseDto())
                .toList();
    }

    private static Function<Task, TaskResponseDTO> convertTaskToResponseDto() {
        return task -> new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription());
    }

    public Task createTask(Task task) {
        int taskId = idCounter.incrementAndGet();
        Task createdTask = new Task(taskId, task.getTitle(), task.getDescription());
        tasks.add(createdTask);

        return createdTask;
    }

    public Task getTaskById(int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Task with the getId " + id + " does not exist."));
    }

    public void deleteTask(Task task) {
        tasks.remove(getTaskById(task.getId()));
    }

    public void resetCounterAndTasksList() {
        resetIdCounter();
        resetTasksList();
    }

    public AtomicInteger getIdCounterForTest() {
        return idCounter;
    }

    private void resetTasksList() {
        tasks.clear();
    }

    private void resetIdCounter() {
        idCounter.set(0);
    }

    public Task updateTask(int id, Task updatedTask) {
        Task existingTask = getTaskById(id);

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());

        return existingTask;
    }

    public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
        int taskId = idCounter.incrementAndGet();
        Task createdTask = new Task(taskId, requestDTO.getTitle(), requestDTO.getDescription());
        tasks.add(createdTask);

        return new TaskResponseDTO(createdTask.getId(), createdTask.getTitle(), createdTask.getDescription());
    }

    public TaskResponseDTO getTaskByIdAsDto(int id) {
        Task task = getTaskById(id);
        return new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription());
    }
}
