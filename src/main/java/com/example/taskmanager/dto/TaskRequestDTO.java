package com.example.taskmanager.dto;

public class TaskRequestDTO {
    private final String title;

    public TaskRequestDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
