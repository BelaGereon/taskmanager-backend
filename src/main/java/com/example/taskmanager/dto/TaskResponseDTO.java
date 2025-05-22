package com.example.taskmanager.dto;

public record TaskResponseDTO(int id, String title, String description) implements TaskDTO {
}
