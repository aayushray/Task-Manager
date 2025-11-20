package com.example.task.mappers;

import com.example.task.domain.dto.TaskListDto;
import com.example.task.domain.entities.TaskList;

public interface TaskListMapper{
    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
