package com.example.taskmanager.dto;

import lombok.Getter;

@Getter
public class TaskResponseDTO {
    private final int id;
    private final String title;
    private final String description;

    public TaskResponseDTO(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

}
