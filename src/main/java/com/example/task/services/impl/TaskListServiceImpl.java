package com.example.task.services.impl;

import com.example.task.domain.entities.TaskList;
import com.example.task.repositories.TaskListRepository;
import com.example.task.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository){
        this.taskListRepository = taskListRepository;
    }
    @Override
    public List<TaskList> listTaskList() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(null != taskList.getId()){
            throw new IllegalArgumentException("Task List already has an ID!");
        }
        if(null == taskList.getTitle() || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Task List title must be present");
        }

        LocalDateTime timeNow = LocalDateTime.now();
        return taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                timeNow,
                timeNow
        ));
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id);
    }

    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        if(null == taskListId){
            throw new IllegalArgumentException("Task List must have an ID");
        }

        if(!Objects.equals(taskListId, taskList.getId())){
            throw new IllegalArgumentException("Attempting to change task list ID, this is not permitted!");
        }

        TaskList existingRecord = taskListRepository.findById(taskListId).orElseThrow(() ->
                new IllegalArgumentException("Task List not found!"));


        existingRecord.setTitle(taskList.getTitle());
        existingRecord.setDescription(taskList.getDescription());
        existingRecord.setUpdated(LocalDateTime.now());

        return taskListRepository.save(existingRecord);
    }

    @Override
    public void deleteTaskList(UUID taskListId) {
        if(null == taskListId){
            throw new IllegalArgumentException("Task List ID cannot be empty.");
        }

        taskListRepository.deleteById(taskListId);
    }
}
