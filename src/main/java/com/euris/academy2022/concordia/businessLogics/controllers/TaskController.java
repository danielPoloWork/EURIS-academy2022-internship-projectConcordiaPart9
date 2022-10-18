package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.tasks.TaskPostRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.web.bind.annotation.*;

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
    public ResponseDto<TaskDto> insert(@RequestBody TaskPostRequest taskDto) {
        return taskService.insert(taskDto.toModel());
    }

    @PutMapping
    public ResponseDto<TaskDto> update(@RequestBody TaskPostRequest taskDto) {
        return taskService.update(taskDto.toModel());
    }

    @PutMapping("/{id}")
    public ResponseDto<TaskDto> lockTask(@PathVariable String id) {
        return taskService.lockTask(id);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<TaskDto> deleteById(@PathVariable String id) {
        return taskService.deleteById(id);
    }

    @GetMapping
    public ResponseDto<List<TaskDto>> getAll() {
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseDto<TaskDto> getById(@PathVariable String id) {
        return taskService.getByIdTrelloTask(id);
    }

    @GetMapping("/title={title}")
    public ResponseDto<List<TaskDto>> getByTitle(@PathVariable String title) {
        return taskService.getByTitle(title);
    }

    @GetMapping("/priority={priority}")
    public ResponseDto<List<TaskDto>> getByPriority(@PathVariable TaskPriority priority) {
        return taskService.getByPriority(priority);
    }

    @GetMapping("/status={status}")
    public ResponseDto<List<TaskDto>> getByStatus(@PathVariable TaskStatus status) {
        return taskService.getByStatus(status);
    }

    @GetMapping("/deadLine={deadLine}")
    public ResponseDto<List<TaskDto>> getByDeadLine(@PathVariable String deadLine) {
        LocalDateTime deadLineParsed = LocalDateTime.parse(deadLine);
        return taskService.getByDeadLine(deadLineParsed);
    }
}
