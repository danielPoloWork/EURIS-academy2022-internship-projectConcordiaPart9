package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskService {

    ResponseDto<TaskDto> insert(Task task);
    ResponseDto<TaskDto> insertFromTrello(Task task);
    ResponseDto<TaskDto> update(Task task);
    ResponseDto<TaskDto> updateFromTrello(Task task);
    ResponseDto<TaskDto> lockTask(String id);
    ResponseDto<TaskDto> deleteById(String id);
    ResponseDto<TaskDto> getByIdTrelloTask(String id);
    ResponseDto<List<TaskDto>> getAll();
    ResponseDto<List<TaskDto>> getByPriority(TaskPriority priority);
    ResponseDto<List<TaskDto>> getByStatus(TaskStatus status);
    ResponseDto<List<TaskDto>> getByTitle(String title);
    ResponseDto<List<TaskDto>> getByDeadLine(LocalDateTime deadLine);
    List<Task> findAllTasksByMemberUuid(String uuidMember);
    Optional<String> findIdByUuidComment(String uuidComment);
    List<Task> updateExpiringTasks();
}