package com.example.task.services.impl;

import com.example.task.domain.entities.Task;
import com.example.task.domain.entities.TaskList;
import com.example.task.domain.entities.TaskPriority;
import com.example.task.domain.entities.TaskStatus;
import com.example.task.repositories.TaskListRepository;
import com.example.task.repositories.TaskRepository;
import com.example.task.services.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListID) {
        return taskRepository.findByTaskListId(taskListID);
    }

    @Override
    public Task createTask(Task task, UUID taskListID) {
        if(null == task.getTitle() || task.getTitle().isBlank()){
            throw new IllegalArgumentException("Task cannot have an empty title!");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepository.findById(taskListID).orElseThrow(() -> new IllegalArgumentException("Invalid Task List ID!!"));

        Task newTask = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        taskRepository.save(newTask);
        return newTask;
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Override
    public Task updateTask(UUID taskListID, UUID taskID, Task taskRecord) {
        if(null == taskRecord.getId()) {
            throw new IllegalArgumentException("Could Not find the Task");
        }
        if(!Objects.equals(taskID, taskRecord.getId())){
            throw new IllegalArgumentException("Not Allowed to edit this task.");
        }

        Task existingRecord = taskRepository.findByTaskListIdAndId(taskListID, taskID).orElseThrow(() -> new IllegalArgumentException("No records found!!"));

        existingRecord.setTitle(taskRecord.getTitle());
        existingRecord.setDescription((taskRecord.getDescription()));
        existingRecord.setDueDate(taskRecord.getDueDate());
        existingRecord.setPriority(taskRecord.getPriority());
        existingRecord.setUpdated(LocalDateTime.now());

        taskRepository.save(existingRecord);

        return existingRecord;
    }
}
