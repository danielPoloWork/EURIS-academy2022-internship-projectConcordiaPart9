package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.responses.ResponseDto;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    ResponseDto<Task> insert(Task task);
    ResponseDto<Task> update(Task task);
    ResponseDto<Task> deleteById(String id);
    ResponseDto<Task> getById(String id);
    ResponseDto<List<Task>> getAll();
    ResponseDto<List<Task>> getByPriority(TaskPriority priority);
    ResponseDto<List<Task>> getByStatus(TaskStatus status);
    ResponseDto<List<Task>> getByTitle(String title);
    ResponseDto<List<Task>> getByDeadLine(LocalDate deadLine);

}
