package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.AssigneeService;
import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.models.Assignee;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.dataPersistences.DTOs.AssigneeDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.jpaRepositories.AssigneeJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssigneeServiceImpl implements AssigneeService {

    private final AssigneeJpaRepository assigneeJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final TaskJpaRepository taskJpaRepository;
    private final TaskService taskService;

    public AssigneeServiceImpl(AssigneeJpaRepository assigneeJpaRepository, MemberJpaRepository memberJpaRepository, TaskJpaRepository taskJpaRepository, TaskService taskService) {
        this.assigneeJpaRepository = assigneeJpaRepository;
        this.memberJpaRepository = memberJpaRepository;
        this.taskJpaRepository = taskJpaRepository;
        this.taskService = taskService;
    }


    @Override
    public ResponseDto<AssigneeDto> insert(Assignee assignee) {
        ResponseDto<AssigneeDto> response = new ResponseDto<>();
        Optional<Member> memberFound = memberJpaRepository.findByUuid(assignee.getMember().getUuid());
        Optional<Task> taskFound = taskJpaRepository.findById(assignee.getTask().getId());

        response.setHttpRequest(HttpRequestType.POST);

        if (memberFound.isEmpty() || taskFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            Optional<Assignee> assigneeFound = assigneeJpaRepository.findByUuidMemberAndIdTask(assignee.getMember().getUuid(), assignee.getTask().getId());

            if (assigneeFound.isPresent()) {
                response.setHttpResponse(HttpResponseType.FOUND);
                response.setCode(HttpResponseType.FOUND.getCode());
                response.setDesc(HttpResponseType.FOUND.getDesc());
            } else {
                Integer assigneeCreated = assigneeJpaRepository.insert(
                        memberFound.get().getUuid(),
                        taskFound.get().getId(),
                        LocalDateTime.now());

                if (assigneeCreated != 1) {
                    response.setHttpResponse(HttpResponseType.NOT_CREATED);
                    response.setCode(HttpResponseType.NOT_CREATED.getCode());
                    response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
                } else {
                    if (taskFound.get().getStatus().equals(TaskStatus.TO_DO)) {
                        taskFound.get().setStatus(TaskStatus.IN_PROGRESS);
                        taskService.update(taskFound.get());
                    }
                    response.setHttpResponse(HttpResponseType.CREATED);
                    response.setCode(HttpResponseType.CREATED.getCode());
                    response.setDesc(HttpResponseType.CREATED.getDesc());

                    AssigneeDto assigneeDtoResponse = AssigneeDto.builder()
                            .memberDto(memberFound.get().toDto())
                            .taskDto(taskFound.get().toDto())
                            .build();

                    response.setBody(assigneeDtoResponse);
                }
            }

        }
        return response;
    }

    @Override
    public ResponseDto<AssigneeDto> removeByUuidMemberAndIdTask(String uuidMember, String idTask) {
        ResponseDto<AssigneeDto> response = new ResponseDto<>();

        Optional<Assignee> assigneeFound = assigneeJpaRepository.findByUuidMemberAndIdTask(uuidMember, idTask);

        response.setHttpRequest(HttpRequestType.DELETE);

        if (assigneeFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            Integer assigneeDeleted = assigneeJpaRepository.removeByUuidMemberAndIdTask(uuidMember, idTask);

            if (assigneeDeleted != 1) {
                response.setHttpResponse(HttpResponseType.NOT_DELETED);
                response.setCode(HttpResponseType.NOT_DELETED.getCode());
                response.setDesc(HttpResponseType.NOT_DELETED.getDesc());
            } else {
                response.setHttpResponse(HttpResponseType.DELETED);
                response.setCode(HttpResponseType.DELETED.getCode());
                response.setDesc(HttpResponseType.DELETED.getDesc());
                response.setBody(assigneeFound.get().toDto());
            }
        }
        return response;
    }

    @Override
    public ResponseDto<List<AssigneeDto>> getAll() {
        ResponseDto<List<AssigneeDto>> response = new ResponseDto<>();

        List<Assignee> assigneeListFound = assigneeJpaRepository.findAll();

        response.setHttpRequest(HttpRequestType.GET);

        if (assigneeListFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(assigneeListFound.stream()
                    .map(Assignee::toDto)
                    .collect(Collectors.toList()));
        }

        return response;
    }

    @Override
    public ResponseDto<AssigneeDto> getByUuidMemberAndIdTask(String uuidMember, String idTask) {
        ResponseDto<AssigneeDto> response = new ResponseDto<>();

        Optional<Assignee> assigneeFound = assigneeJpaRepository.findByUuidMemberAndIdTask(uuidMember, idTask);

        response.setHttpRequest(HttpRequestType.GET);

        if (assigneeFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(assigneeFound.get().toDto());
        }
        return response;
    }

    @Override
    public ResponseDto<List<TaskDto>> getAllTaskNotAssignedToThisUuidMember(String uuidMember) {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        List<Task> allTaskFound = assigneeJpaRepository.findAllTaskNotAssignedToThisUuidMember(uuidMember);

        response.setHttpRequest(HttpRequestType.GET);

        if (allTaskFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(allTaskFound.stream()
                    .map(Task::toDto)
                    .collect(Collectors.toList()));
        }
        return response;
    }
}