package com.euris.academy2022.concordia.businessLogics.impls;

import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.responses.ResponseDto;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskJpaRepository taskJpaRepository;

    public TaskServiceImpl(TaskJpaRepository taskJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public ResponseDto<Task> insert(Task task) {
        ResponseDto<Task> response = new ResponseDto<>();

        Integer taskCreated = taskJpaRepository.insert(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority().getLabel(),
                task.getStatus().getLabel(),
                task.getDeadLine());

        response.setHttpRequest(HttpRequestType.POST);

        if (taskCreated != 1) {
            response.setHttpResponse(HttpResponseType.NOT_CREATED);
            response.setCode(HttpResponseType.NOT_CREATED.getCode());
            response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.CREATED);
            response.setCode(HttpResponseType.CREATED.getCode());
            response.setDesc(HttpResponseType.CREATED.getDesc());
            response.setBody(task);

        }

        return response;
    }


    @Override
    public ResponseDto<Task> update(Task task) {
        ResponseDto<Task> response = new ResponseDto<>();
        Optional<Task> optionalTask = taskJpaRepository.findById(task.getId());

        if (optionalTask.isEmpty()) {
            response.setHttpRequest(HttpRequestType.GET);
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
                    task.getDeadLine());

            response.setHttpRequest(HttpRequestType.PUT);

            if (updatedTask != 1) {
                response.setHttpResponse(HttpResponseType.NOT_UPDATED);
                response.setCode(HttpResponseType.NOT_UPDATED.getCode());
                response.setDesc(HttpResponseType.NOT_UPDATED.getDesc());
            } else {
                response.setHttpResponse(HttpResponseType.UPDATED);
                response.setCode(HttpResponseType.UPDATED.getCode());
                response.setDesc(HttpResponseType.UPDATED.getDesc());
                response.setBody(task);
            }
        }
        return response;
    }

    @Override
    public ResponseDto<Task> deleteById(String id) {
        ResponseDto<Task> response = new ResponseDto<>();
        Optional<Task> optionalTask = taskJpaRepository.findById(id);

        if (optionalTask.isEmpty()) {
            response.setHttpRequest(HttpRequestType.GET);
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
        } else {
            Integer deletedTask = taskJpaRepository.removeById(id);

            response.setHttpRequest(HttpRequestType.DELETE);

            if (deletedTask != 1) {
                response.setHttpResponse(HttpResponseType.NOT_DELETED);
                response.setCode(HttpResponseType.NOT_DELETED.getCode());
                response.setDesc(HttpResponseType.NOT_DELETED.getDesc());
            } else {
                response.setHttpResponse(HttpResponseType.DELETED);
                response.setCode(HttpResponseType.DELETED.getCode());
                response.setDesc(HttpResponseType.DELETED.getDesc());
                response.setBody(optionalTask.get());
            }
        }
        return response;
    }

    @Override
    public ResponseDto<Task> getById(String id) {
        ResponseDto<Task> response = new ResponseDto<>();
        Optional<Task> optionalTask = taskJpaRepository.findById(id);

        response.setHttpRequest(HttpRequestType.GET);

        if (optionalTask.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(optionalTask.get());
        }

        return response;
    }

    @Override
    public ResponseDto<List<Task>> getAll() {
        ResponseDto<List<Task>> response = new ResponseDto<>();
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
            response.setBody(taskList);
        }

        return response;
    }

    @Override
    public ResponseDto<List<Task>> getByPriority(TaskPriority priority) {
        ResponseDto<List<Task>> response = new ResponseDto<>();
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
            response.setBody(taskList);
        }

        return response;
    }

    @Override
    public ResponseDto<List<Task>> getByStatus(TaskStatus status) {
        ResponseDto<List<Task>> response = new ResponseDto<>();
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
            response.setBody(taskList);
        }

        return response;
    }

    @Override
    public ResponseDto<List<Task>> getByTitle(String title) {
        ResponseDto<List<Task>> response = new ResponseDto<>();
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
            response.setBody(taskList);
        }

        return response;
    }

    @Override
    public ResponseDto<List<Task>> getByDeadLine(LocalDateTime deadLine) {
        ResponseDto<List<Task>> response = new ResponseDto<>();
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
            response.setBody(taskList);
        }

        return response;
    }
}
