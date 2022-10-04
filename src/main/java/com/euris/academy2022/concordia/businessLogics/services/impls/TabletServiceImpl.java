package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.TabletService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TaskDto;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.time.TimeUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TabletServiceImpl implements TabletService {
    private final TaskJpaRepository taskJpaRepository;

    public TabletServiceImpl(TaskJpaRepository taskJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
    }

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
        List<Task> memberTasks = taskJpaRepository.findAllTasksByMemberUuid(uuidMember);


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

        ResponseDto<List<TaskDto>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);
        List<Task> memberTasks = taskJpaRepository.findAllTasksByMemberUuid(uuidMember);

        if (!memberTasks.isEmpty()) {
            List<Task> expiringTasks = memberTasks
                    .stream()
                    .filter(task -> TimeUtils.isExpiring(task.getDeadLine()))
                    .toList();

            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(expiringTasks.stream().map(Task::toDto).collect(Collectors.toList()));

        } else {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        }

        return response;
    }

}
