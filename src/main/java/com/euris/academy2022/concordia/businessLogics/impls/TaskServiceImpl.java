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

        Optional<Task> taskCreated = taskJpaRepository.insert(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority().getLabel(),
                task.getStatus().getLabel(),
                task.getDeadLine());

        response.setHttpRequest(HttpRequestType.POST);

        if (taskCreated.isEmpty()) {
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
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        } else {

            try {
                taskJpaRepository.save(task);
                response.setHttpResponse(HttpResponseType.UPDATED);
                response.setCode(HttpResponseType.UPDATED.getCode());
                response.setDesc(HttpResponseType.UPDATED.getDesc());
                response.setBody(task);
            } catch (Exception e) {
                response.setHttpResponse(HttpResponseType.NOT_UPDATED);
                response.setCode(HttpResponseType.NOT_UPDATED.getCode());
                response.setDesc(HttpResponseType.NOT_UPDATED.getDesc());
                response.setBody(task);
            }
        }

        return response;
    }

    @Override
    public ResponseDto<String> deleteById(String id) {
        return null;
    }

    @Override
    public ResponseDto<Task> getById(String id) {
        return null;
    }

    @Override
    public ResponseDto<List<Task>> getAll() {
        ResponseDto<List<Task>> response = new ResponseDto<>();
        List<Task> tasks = taskJpaRepository.findAll();
        response.setHttpResponse(HttpResponseType.ACCEPTED);
        response.setCode(HttpResponseType.ACCEPTED.getCode());
        response.setDesc(HttpResponseType.ACCEPTED.getDesc());
        response.setBody(tasks);
        return response;
    }

    @Override
    public ResponseDto<List<Task>> getByPriority(TaskPriority priority) {
        return null;
    }

    @Override
    public ResponseDto<List<Task>> getByStatus(TaskStatus status) {
        return null;
    }

    @Override
    public ResponseDto<List<Task>> getByTitle(String title) {
        return null;
    }

    @Override
    public ResponseDto<List<Task>> getByDeadLine(LocalDateTime deadLine) {
        return null;
    }
}
