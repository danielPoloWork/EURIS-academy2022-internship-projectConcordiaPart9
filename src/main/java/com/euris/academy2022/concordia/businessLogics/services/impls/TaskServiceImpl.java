package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.TimeUtil;

import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskJpaRepository taskJpaRepository;

    public TaskServiceImpl(TaskJpaRepository taskJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public ResponseDto<TaskDto> insert(Task task) {
        ResponseDto<TaskDto> response = new ResponseDto<>();

        Integer taskCreated = taskJpaRepository.insert(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority().getLabel(),
                task.getStatus().getLabel(),
                task.getDeadLine(),
                LocalDateTime.now(),
                LocalDateTime.now());

        response.setHttpRequest(HttpRequestType.POST);

        if (taskCreated != 1) {
            response.setHttpResponse(HttpResponseType.NOT_CREATED);
            response.setCode(HttpResponseType.NOT_CREATED.getCode());
            response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.CREATED);
            response.setCode(HttpResponseType.CREATED.getCode());
            response.setDesc(HttpResponseType.CREATED.getDesc());
            response.setBody(task.toDto());

        }

        return response;
    }

    @Override
    public ResponseDto<TaskDto> insertFromTrello(Task task) {
        ResponseDto<TaskDto> response = new ResponseDto<>();

        Integer taskCreated = taskJpaRepository.insert(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority().getLabel(),
                task.getStatus().getLabel(),
                task.getDeadLine(),
                task.getDateCreation(),
                task.getDateUpdate());

        response.setHttpRequest(HttpRequestType.POST);

        if (taskCreated != 1) {
            response.setHttpResponse(HttpResponseType.NOT_CREATED);
            response.setCode(HttpResponseType.NOT_CREATED.getCode());
            response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.CREATED);
            response.setCode(HttpResponseType.CREATED.getCode());
            response.setDesc(HttpResponseType.CREATED.getDesc());
            response.setBody(task.toDto());

        }

        return response;
    }


    @Override
    public ResponseDto<TaskDto> update(Task task) {
        ResponseDto<TaskDto> response = new ResponseDto<>();
        Optional<Task> optionalTask = taskJpaRepository.findById(task.getId());

        response.setHttpRequest(HttpRequestType.PUT);

        if (optionalTask.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        } else {
            Integer updatedTask = taskJpaRepository.update(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getPriority().getLabel(),
                    task.getStatus().getLabel(),
                    task.getDeadLine(),
                    LocalDateTime.now());

            if (updatedTask != 1) {
                response.setHttpResponse(HttpResponseType.NOT_UPDATED);
                response.setCode(HttpResponseType.NOT_UPDATED.getCode());
                response.setDesc(HttpResponseType.NOT_UPDATED.getDesc());

            } else {
                response.setHttpResponse(HttpResponseType.UPDATED);
                response.setCode(HttpResponseType.UPDATED.getCode());
                response.setDesc(HttpResponseType.UPDATED.getDesc());
                response.setBody(task.toDto());
            }
        }
        return response;
    }

    @Override
    public ResponseDto<TaskDto> updateFromTrello(Task task) {
        ResponseDto<TaskDto> response = new ResponseDto<>();
        Optional<Task> optionalTask = taskJpaRepository.findById(task.getId());

        response.setHttpRequest(HttpRequestType.PUT);

        if (optionalTask.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        } else {
            Integer updatedTask = taskJpaRepository.update(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getPriority().getLabel(),
                    task.getStatus().getLabel(),
                    task.getDeadLine(),
                    task.getDateUpdate());

            if (updatedTask != 1) {
                response.setHttpResponse(HttpResponseType.NOT_UPDATED);
                response.setCode(HttpResponseType.NOT_UPDATED.getCode());
                response.setDesc(HttpResponseType.NOT_UPDATED.getDesc());

            } else {
                response.setHttpResponse(HttpResponseType.UPDATED);
                response.setCode(HttpResponseType.UPDATED.getCode());
                response.setDesc(HttpResponseType.UPDATED.getDesc());
                response.setBody(task.toDto());
            }
        }
        return response;
    }

    @Override
    public ResponseDto<TaskDto> deleteById(String id) {
        ResponseDto<TaskDto> response = new ResponseDto<>();
        Optional<Task> optionalTask = taskJpaRepository.findById(id);

        response.setHttpRequest(HttpRequestType.DELETE);

        if (optionalTask.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
        } else {
            Integer deletedTask = taskJpaRepository.removeById(id);

            if (deletedTask != 1) {
                response.setHttpResponse(HttpResponseType.NOT_DELETED);
                response.setCode(HttpResponseType.NOT_DELETED.getCode());
                response.setDesc(HttpResponseType.NOT_DELETED.getDesc());
            } else {
                response.setHttpResponse(HttpResponseType.DELETED);
                response.setCode(HttpResponseType.DELETED.getCode());
                response.setDesc(HttpResponseType.DELETED.getDesc());
                response.setBody(optionalTask.get().toDto());
            }
        }
        return response;
    }

    @Override
    public ResponseDto<TaskDto> getByIdTrelloTask(String id) {
        ResponseDto<TaskDto> response = new ResponseDto<>();
        Optional<Task> optionalTask = taskJpaRepository.findByIdTrelloTask(id);

        response.setHttpRequest(HttpRequestType.GET);

        if (optionalTask.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(optionalTask.get().toDto());
        }

        return response;
    }

    @Override
    public ResponseDto<List<TaskDto>> getAll() {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();
        List<Task> taskList = taskJpaRepository.findAll();

        response.setHttpRequest(HttpRequestType.GET);

        if (taskList.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(taskList.stream().map(Task::toDto).collect(Collectors.toList()));
        }

        return response;
    }

    @Override
    public ResponseDto<List<TaskDto>> getByPriority(TaskPriority priority) {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();
        List<Task> taskList = taskJpaRepository.findByPriority(priority.getLabel());

        response.setHttpRequest(HttpRequestType.GET);

        if (taskList.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(taskList.stream().map(Task::toDto).collect(Collectors.toList()));
        }

        return response;
    }

    @Override
    public ResponseDto<List<TaskDto>> getByStatus(TaskStatus status) {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();
        List<Task> taskList = taskJpaRepository.findByStatus(status.getLabel());

        response.setHttpRequest(HttpRequestType.GET);

        if (taskList.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(taskList.stream().map(Task::toDto).collect(Collectors.toList()));
        }

        return response;
    }

    @Override
    public ResponseDto<List<TaskDto>> getByTitle(String title) {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();
        List<Task> taskList = taskJpaRepository.findByTitle(title);

        response.setHttpRequest(HttpRequestType.GET);

        if (taskList.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(taskList.stream().map(Task::toDto).collect(Collectors.toList()));
        }

        return response;
    }

    @Override
    public ResponseDto<List<TaskDto>> getByDeadLine(LocalDateTime deadLine) {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();
        List<Task> taskList = taskJpaRepository.findByDeadLine(deadLine);

        response.setHttpRequest(HttpRequestType.GET);

        if (taskList.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(taskList.stream().map(Task::toDto).collect(Collectors.toList()));
        }

        return response;
    }

    @Override
    public List<Task> findAllTasksByMemberUuid(String uuidMember) {
        return taskJpaRepository.findAllTasksByMemberUuid(uuidMember);
    }

    private void updateTaskPriorityToExpiring(Task task) {
        task.setPriority(TaskPriority.EXPIRING);
        taskJpaRepository.save(task);
    }

    @Override
    public void updateExpiringTasks() {
        List<Task> expiringTasks = taskJpaRepository.findAll()
                .stream()
                .filter(task -> task.getDeadLine() != null)
                .filter(task -> TimeUtil.isExpiring(task.getDeadLine()))
                .toList();

        expiringTasks.forEach(this::updateTaskPriorityToExpiring);
    }
}
