package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.tasks.TaskPostRequest;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.responses.ResponseDto;
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
        return taskService.getById(id);
    }

    @GetMapping("/title={title}")
    public ResponseDto<List<TaskDto>> getByTitle(@PathVariable String title) {
        return taskService.getByTitle(title);
    }

    @GetMapping("/priority={priority}")
    public ResponseDto<List<TaskDto>> getByPriority(@PathVariable String priority) {
        TaskPriority taskPriority = TaskPriority.valueOf(priority);
        return taskService.getByPriority(taskPriority);
    }

    @GetMapping("/status={status}")
    public ResponseDto<List<TaskDto>> getByStatus(@PathVariable String status) {
        TaskStatus taskStatus = TaskStatus.valueOf(status);
        return taskService.getByStatus(taskStatus);
    }

    @GetMapping("/deadLine={deadLine}")
    public ResponseDto<List<TaskDto>> getByDeadLine(@PathVariable String deadLine) {
        //LocalDate deadLineParsed = LocalDate.parse(deadLine);
        //DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime deadLineParsed = LocalDateTime.parse(deadLine);
        return taskService.getByDeadLine(deadLineParsed);
    }
}
