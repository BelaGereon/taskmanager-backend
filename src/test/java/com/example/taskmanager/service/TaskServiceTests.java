package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.utility.testdata.TaskFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.taskmanager.utility.testdata.TaskFactory.createTaskObject;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTests {

    private final TaskService taskService = new TaskService();

    @Test
    void shouldReturnAllTasks() {
        Task task1 = taskService.createTask(createTaskObject("Title 1", "Description 1"));
        Task task2 = taskService.createTask(createTaskObject("Title 2", "Irrelevant Description"));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(2, tasks.size());
        assertEquals(List.of(task1, task2), tasks);
    }

    @Test
    void shouldNotAllowModificationOfReturnedTaskList() {
        taskService.createTask(createTaskObject("Test Title", "Sample Description"));
        List<Task> tasks = taskService.getAllTasks();

        assertThrows(UnsupportedOperationException.class, () -> tasks.add(createTaskObject("Some Title", "Some Description")));
        assertThrows(UnsupportedOperationException.class, () -> tasks.remove(0));
    }

    @Test
    void shouldReturnCreatedTask() {
        Task createdTask = taskService.createTask(createTaskObject("Test Title", "Test Description"));

        assertEquals(new Task(1, "Test Title", "Test Description"), createdTask);
    }

    @Test
    void shouldReturnCorrectTask() {
        Task createdTask1 = taskService.createTask(createTaskObject("Title 1", "Description 1"));
        Task createdTask2 = taskService.createTask(createTaskObject("Title 2", "Description 2"));

        Task expectedTask1 = createTaskObject(1,"Title 1", "Description 1");
        Task expectedTask2 = createTaskObject(2,"Title 2", "Description 2");

        assertEquals(expectedTask1, createdTask1);
        assertEquals(expectedTask2, createdTask2);
    }

    @Test
    void shouldReturnTaskById() {
        taskService.createTask(createTaskObject("Some Title", "Some Description"));
        Task expectedTask = taskService.createTask(createTaskObject("Expected Title", "Expected Description"));
        taskService.createTask(createTaskObject("Irrelevant Title", "Irrelevant Description"));

        assertEquals(expectedTask, taskService.getTaskById(2));
    }

    @Test
    void shouldReturnErrorMessageWhenNoTaskIsFound() {
        taskService.createTask(createTaskObject("Title", "Description"));

        assertThrows(IllegalArgumentException.class, () -> taskService.getTaskById(2));
    }

    @Test
    void shouldDeleteTask() {
        Task deletedTask = taskService.createTask(createTaskObject("Deleted Task Title", "Deleted Task Description"));

        List<Task> tasksBeforeDeletion = taskService.getAllTasks();

        assertEquals(1, tasksBeforeDeletion.size());
        assertTrue(tasksBeforeDeletion.contains(deletedTask));

        taskService.deleteTask(deletedTask);

        List<Task> tasksAfterDeletion = taskService.getAllTasks();

        assertEquals(0, tasksAfterDeletion.size());
        assertFalse(tasksAfterDeletion.contains(deletedTask));
    }

    @Test
    void shouldDeleteCorrectTask() {
        Task someTask = taskService.createTask(createTaskObject("Some Task Title", "Some Task Description"));
        Task deletedTask = taskService.createTask(createTaskObject("Deleted Task Title", "Deleted Task Description"));
        Task anotherTask = taskService.createTask(createTaskObject("Another Task Title", "Another Task Description"));

        List<Task> tasksBeforeDeletion = taskService.getAllTasks();

        assertEquals(3, tasksBeforeDeletion.size());
        assertTrue(tasksBeforeDeletion.containsAll(List.of(someTask, deletedTask, anotherTask)));

        taskService.deleteTask(deletedTask);
        List<Task> tasksAfterDeletion = taskService.getAllTasks();

        assertEquals(2, tasksAfterDeletion.size());
        assertTrue(tasksAfterDeletion.containsAll(List.of(someTask, anotherTask)));
        assertFalse(tasksAfterDeletion.contains(deletedTask));
    }

    @Test
    void shouldThrowExceptionWhenDeletingUnknownTask() {
        Task unknownTask = createTaskObject("Unkown Title", "Unknown Description");

        assertThrows(IllegalArgumentException.class, () -> taskService.deleteTask(unknownTask));
    }

    @Test
    void shouldResetTheIdCounter() {
        AtomicInteger idCounter = taskService.getIdCounterForTest();

        assertEquals(0, idCounter.get());
        assertTrue(taskService.getAllTasks().isEmpty());

        Task task = taskService.createTask(createTaskObject("Irrelevant", "Values"));
        assertEquals(1, idCounter.get());
        assertTrue(taskService.getAllTasks().contains(task));

        taskService.resetCounterAndTasksList();

        assertEquals(0, idCounter.get());
        assertTrue(taskService.getAllTasks().isEmpty());
    }

    @Test
    void shouldUpdateTask() {
        Task taskToBeUpdated = taskService.createTask(createTaskObject(1, "Old Title", "Old Description"));

        taskService.updateTask(1, createTaskObject("New Title", "Old Description"));

        assertEquals(1, taskToBeUpdated.getId());
        assertEquals("New Title", taskToBeUpdated.getTitle());
        assertEquals("Old Description", taskToBeUpdated.getDescription());
    }

    @Test
    void shouldUpdateTheCorrectTask() {
        Task irrelevantTask =
                taskService.createTask(createTaskObject(1,"Irrelevant Task", "Original Description"));
        Task taskToBeUpdated =
                taskService.createTask(createTaskObject(2, "Correct Task", "Old Description"));

        assertEquals(2, taskService.getAllTasks().size());

        taskService.updateTask(2, createTaskObject("Correct Task", "New Description"));

        assertEquals("New Description", taskToBeUpdated.getDescription());
        assertEquals("Original Description", irrelevantTask.getDescription());
        assertEquals(2, taskService.getAllTasks().size());
    }
}
