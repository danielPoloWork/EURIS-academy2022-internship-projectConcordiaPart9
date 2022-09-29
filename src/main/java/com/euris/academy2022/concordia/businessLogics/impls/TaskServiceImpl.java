package com.euris.academy2022.concordia.businessLogics.impls;

import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskJpaRepository taskJpaRepository;

    public TaskServiceImpl(TaskJpaRepository taskJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public Optional<Task> getById(String id) {
        return taskJpaRepository.findById(id);
    }

    @Override
    public List<Task> getAll() {
        return taskJpaRepository.findAll();
    }

    @Override
    public List<Task> getByPriority(TaskPriority priority) {
        return taskJpaRepository.findByPriority(priority.getLabel());
    }

    @Override
    public List<Task> getByStatus(TaskStatus status) {
        return taskJpaRepository.findByStatus(status.getLabel());
    }

    @Override
    public List<Task> getByTitle(String title) {
        return taskJpaRepository.findByTitle(title);
    }

    @Override
    public List<Task> getByDeadLine(LocalDateTime deadLine) {
        return taskJpaRepository.findByDeadLine(deadLine);
    }

    @Override
    public Optional<Task> insert(Task task) {
        return taskJpaRepository.insert(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority().getLabel(),
                task.getStatus().getLabel(),
                task.getDeadLine());
    }

    @Override
    public Optional<Task> update(Task task) {
        Optional<Task> taskOptional = getById(task.getId());
        return taskOptional.isEmpty() ? Optional.empty() : Optional.of(taskJpaRepository.save(task));
    }

    @Override
    public Boolean delete(Task task) {
        taskJpaRepository.delete(task);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteById(String id) {
        taskJpaRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteAll() {
        taskJpaRepository.deleteAll();
        return Boolean.TRUE;
    }
}
