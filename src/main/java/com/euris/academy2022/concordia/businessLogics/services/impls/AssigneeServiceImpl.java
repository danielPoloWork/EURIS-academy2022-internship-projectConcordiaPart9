package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.AssigneeService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Assignee;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.AssigneeDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import com.euris.academy2022.concordia.jpaRepositories.AssigneeJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssigneeServiceImpl implements AssigneeService {

    private final AssigneeJpaRepository assigneeJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final TaskJpaRepository taskJpaRepository;

    public AssigneeServiceImpl(AssigneeJpaRepository assigneeJpaRepository, MemberJpaRepository memberJpaRepository, TaskJpaRepository taskJpaRepository) {
        this.assigneeJpaRepository = assigneeJpaRepository;
        this.memberJpaRepository = memberJpaRepository;
        this.taskJpaRepository = taskJpaRepository;
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
            Integer assigneeCreated = assigneeJpaRepository.insert(
                    memberFound.get().getUuid(),
                    taskFound.get().getId());

            if (assigneeCreated != 1) {
                response.setHttpResponse(HttpResponseType.NOT_CREATED);
                response.setCode(HttpResponseType.NOT_CREATED.getCode());
                response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
            } else {
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
        return response;
    }

    @Override
    public ResponseDto<AssigneeDto> removeByUuidMemberAndIdTask(String uuidMember, String idTask) {
        return null;
    }

    @Override
    public ResponseDto<List<AssigneeDto>> getAll() {
        return null;
    }

    @Override
    public ResponseDto<List<AssigneeDto>> getByUuidMember(String uuidMember) {
        return null;
    }

    @Override
    public ResponseDto<List<AssigneeDto>> getByIdTask(String idTask) {
        return null;
    }
}
