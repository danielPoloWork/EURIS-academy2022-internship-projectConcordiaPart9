package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.tasks.TaskPostRequest;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.responses.ResponseDto;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseDto<Task> insert(@RequestBody TaskPostRequest taskDto) {
        return taskService.insert(taskDto.toModel());
    }

    @PutMapping
    public ResponseDto<Task> update(@RequestBody TaskPostRequest taskDto) {
        return taskService.update(taskDto.toModel());
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Task> deleteById(@PathVariable String id) {
        return taskService.deleteById(id);
    }

    @GetMapping
    public ResponseDto<List<Task>> getAll() {
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseDto<Task> getById(@PathVariable String id) {
        return taskService.getById(id);
    }

    @GetMapping("/title={title}")
    public ResponseDto<List<Task>> getByTitle(@PathVariable String title) {
        return taskService.getByTitle(title);
    }

    @GetMapping("/priority={priority}")
    public ResponseDto<List<Task>> getByPriority(@PathVariable TaskPriority priority) {
        return taskService.getByPriority(priority);
    }

    @GetMapping("/status={status}")
    public ResponseDto<List<Task>> getByStatus(@PathVariable TaskStatus status) {
        return taskService.getByStatus(status);
    }

    @GetMapping("/deadLine={deadLine}")
    public ResponseDto<List<Task>> getByDeadLine(@PathVariable String deadLine) {
        LocalDate deadLineParsed = LocalDate.parse(deadLine);
        return taskService.getByDeadLine(deadLineParsed);
    }
}
