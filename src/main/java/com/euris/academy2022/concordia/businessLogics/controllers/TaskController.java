package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.tasks.TaskPostRequest;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Optional<Task> insert(@RequestBody TaskPostRequest taskDto) {
        return taskService.insert(taskDto.toModel());
    }

    @PutMapping
    public Optional<Task> update(@RequestBody TaskPostRequest taskDto) {
        return taskService.update(taskDto.toModel());
    }

    @DeleteMapping("/{id}")
    public Boolean deleteById(@PathVariable String id) {
        return taskService.deleteById(id);
    }

    @DeleteMapping
    public Boolean deleteAll() {
        return taskService.deleteAll();
    }

    @GetMapping
    public List<Task> getAll() {
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Task> getById(@PathVariable String id) {
        return taskService.getById(id);
    }

    @GetMapping("/priority={priority}")
    public List<Task> getByRole(@PathVariable TaskPriority priority) {
        return taskService.getByPriority(priority);
    }

    @GetMapping("/status={status}")
    public List<Task> getByStatus(@PathVariable TaskStatus status) {
        return taskService.getByStatus(status);
    }

    @GetMapping("/title={title}")
    public List<Task> getByTitle(@PathVariable String title) {
        return taskService.getByTitle(title);
    }

    @GetMapping("/deadLine={deadLine}")
    public List<Task> getByDeadLine(@PathVariable LocalDateTime deadLine) {
        return taskService.getByDeadLine(deadLine);
    }
}
