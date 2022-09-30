package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.responses.ResponseDto;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    ResponseDto<TaskDto> insert(Task task);
    ResponseDto<TaskDto> update(Task task);
    ResponseDto<TaskDto> deleteById(String id);
    ResponseDto<TaskDto> getById(String id);
    ResponseDto<List<TaskDto>> getAll();
    ResponseDto<List<TaskDto>> getByPriority(TaskPriority priority);
    ResponseDto<List<TaskDto>> getByStatus(TaskStatus status);
    ResponseDto<List<TaskDto>> getByTitle(String title);
    ResponseDto<List<TaskDto>> getByDeadLine(LocalDateTime deadLine);

}
