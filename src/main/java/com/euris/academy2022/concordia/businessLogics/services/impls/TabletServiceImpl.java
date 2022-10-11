package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.TabletService;
import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TabletServiceImpl implements TabletService {
    @Autowired
    TaskService taskService;

    @Override
    public ResponseDto<List<TaskDto>> getMemberTasksByPriority(String uuidMember, TaskPriority priority) {

        ResponseDto<List<TaskDto>> response = getMemberTasks(uuidMember);

        if (!response.getBody().isEmpty()) {
            List<TaskDto> filteredBody = response.getBody()
                    .stream()
                    .filter(task -> task.getPriority().getLabel().equals(priority.getLabel()))
                    .toList();

            response.setBody(filteredBody);
        }

        return response;
    }

    @Override
    public ResponseDto<List<TaskDto>> getMemberTasks(String uuidMember) {

        ResponseDto<List<TaskDto>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);
        List<Task> memberTasks = taskService.findAllTasksByMemberUuid(uuidMember);

        if (!memberTasks.isEmpty()) {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(memberTasks.stream().map(Task::toDto).collect(Collectors.toList()));

        } else {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        }

        return response;
    }

    @Override
    public ResponseDto<List<TaskDto>> getExpiringTasks(String uuidMember) {

        taskService.updateExpiringTasks();

        ResponseDto<List<TaskDto>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        List<Task> expiringTasks = taskService.findAllTasksByMemberUuid(uuidMember)
                .stream()
                .filter(task -> task.getPriority().equals(TaskPriority.EXPIRING))
                .toList();

        if (expiringTasks.isEmpty()) {

            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        } else {

            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(expiringTasks.stream().map(Task::toDto).collect(Collectors.toList()));
        }

        return response;
    }

}