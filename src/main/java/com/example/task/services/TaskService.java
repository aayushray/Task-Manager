package com.example.task.services;

import com.example.task.domain.entities.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    public List<Task> listTasks(UUID taskListID);
    public Task createTask(Task task, UUID taskListID);
    public Optional<Task> getTask(UUID taskListID, UUID taskId);
    public Task updateTask(UUID taskListID, UUID taskID, Task taskRecord);
}
