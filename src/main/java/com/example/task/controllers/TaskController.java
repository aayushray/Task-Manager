package com.example.task.controllers;

import com.example.task.domain.dto.TaskDto;
import com.example.task.domain.entities.Task;
import com.example.task.mappers.TaskMapper;
import com.example.task.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
public class TaskController {
    private final TaskMapper taskMapper;
    private final TaskService taskService;

    public TaskController(TaskMapper taskMapper, TaskService taskService) {
        this.taskMapper = taskMapper;
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDto> listsTasks(@PathVariable("task_list_id") UUID taskListID){
        List<Task> servicedTasks = taskService.listTasks(taskListID);
        return servicedTasks.stream().
                map(taskMapper::toDto).
                toList();
    }

    @PostMapping
    public TaskDto createTasks(
            @PathVariable("task_list_id") UUID taskListId,
            @RequestBody TaskDto taskDto
    ){
        Task task = taskService.createTask(taskMapper.fromDto(taskDto), taskListId);
        return taskMapper.toDto(task);
    }

    @GetMapping(path = "/{task_id}")
    public Optional<TaskDto> getTask(
            @PathVariable("task_id") UUID taskId,
            @PathVariable("task_list_id") UUID taskListId
    ){
        return taskService.getTask(taskListId, taskId).map(taskMapper::toDto);
    }

    @PutMapping(path = "/{task_id}")
    public TaskDto updateTask(
            @PathVariable("task_id") UUID taskId,
            @PathVariable("task_list_id") UUID taskListId,
            @RequestBody TaskDto taskDto
    ){
        Task updatedRecord = taskService.updateTask(taskListId, taskId, taskMapper.fromDto(taskDto));
        return taskMapper.toDto(updatedRecord);
    }

}
