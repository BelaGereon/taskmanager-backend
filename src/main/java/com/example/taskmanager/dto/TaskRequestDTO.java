package com.example.taskmanager.dto;

import lombok.Getter;

@Getter
public class TaskRequestDTO {
    private final String title;
    private final String description;

    public TaskRequestDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }

}
