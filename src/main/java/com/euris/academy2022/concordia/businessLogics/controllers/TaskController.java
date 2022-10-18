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

import static com.euris.academy2022.concordia.utils.constants.controller.TaskControllerConstant.*;

@RestController
@RequestMapping(TASK_REQUEST_MAPPING)
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

    @DeleteMapping(DELETE_BY_ID)
    public ResponseDto<TaskDto> deleteById(@PathVariable String id) {
        return taskService.deleteById(id);
    }

    @GetMapping
    public ResponseDto<List<TaskDto>> getAll() {
        return taskService.getAll();
    }

    @GetMapping(GET_BY_ID)
    public ResponseDto<TaskDto> getById(@PathVariable String id) {
        return taskService.getByIdTrelloTask(id);
    }

    @GetMapping(GET_BY_TITLE)
    public ResponseDto<List<TaskDto>> getByTitle(@PathVariable String title) {
        return taskService.getByTitle(title);
    }

    @GetMapping(GET_BY_PRIORITY)
    public ResponseDto<List<TaskDto>> getByPriority(@PathVariable TaskPriority priority) {
        return taskService.getByPriority(priority);
    }

    @GetMapping(GET_BY_STATUS)
    public ResponseDto<List<TaskDto>> getByStatus(@PathVariable TaskStatus status) {
        return taskService.getByStatus(status);
    }

    @GetMapping(GET_BY_DEADLINE)
    public ResponseDto<List<TaskDto>> getByDeadLine(@PathVariable String deadLine) {
        LocalDateTime deadLineParsed = LocalDateTime.parse(deadLine);
        return taskService.getByDeadLine(deadLineParsed);
    }
}
