package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.TabletService;
import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TabletServiceImpl implements TabletService {

    private TaskService taskService;

    public TabletServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ResponseDto<List<TaskDto>> getMemberTasksByPriority(String uuidMember, TaskPriority priority) {

        taskService.updateExpiringTasks();

        ResponseDto<List<TaskDto>> response = getMemberTasks(uuidMember);

        if (response.getBody() != null) {
            List<TaskDto> filteredBody = response.getBody()
                    .stream()
                    .filter(task -> task.getPriority().equals(priority))
                    .toList();

            response.setBody(filteredBody);
        }

        return response;
    }

    @Override
    public ResponseDto<List<TaskDto>> getMemberTasksByStatus(String uuidMember, TaskStatus status) {

        ResponseDto<List<TaskDto>> response = getMemberTasks(uuidMember);

        if (response.getBody() != null) {
            List<TaskDto> filteredBody = response.getBody()
                    .stream()
                    .filter(task -> task.getStatus().equals(status))
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

}