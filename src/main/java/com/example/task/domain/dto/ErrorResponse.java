package com.example.task.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
