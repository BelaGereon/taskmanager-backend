package com.example.taskmanager.dto;

public class TaskRequestDTO {
    private final String title;
    private final String description;

    public TaskRequestDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
