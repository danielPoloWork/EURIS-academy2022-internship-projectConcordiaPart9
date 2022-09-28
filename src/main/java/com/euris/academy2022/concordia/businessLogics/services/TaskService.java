package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TaskDto;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> getById(String id);
    List<Task> getAll();
    List<Task> getByPriority(TaskPriority priority);
    List<Task> getByStatus(TaskStatus status);
    List<Task> getByTitle(String title);
    List<Task> getByDeadLine(LocalDateTime deadLine);
    Optional<TaskDto> insert(Task task);
    Optional<TaskDto> update(Task task);
    Boolean delete(Task task);
    Boolean deleteById(String id);
    Boolean deleteAll();
}
