package com.example.task.domain.dto;

import com.example.task.domain.entities.TaskPriority;
import com.example.task.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}
