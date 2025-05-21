package com.example.taskmanager.dto;

public class TaskResponseDTO {
    private final int id;
    private final String title;
    private final String description;

    public TaskResponseDTO(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
