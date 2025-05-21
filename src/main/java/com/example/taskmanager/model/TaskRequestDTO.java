package com.example.taskmanager.model;

public class TaskRequestDTO {
    private final String title;

    public TaskRequestDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
