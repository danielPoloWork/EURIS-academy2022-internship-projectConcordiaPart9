package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.AssigneeService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.assignees.AssigneePostRequest;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssigneeServiceImpl implements AssigneeService {

    private final TaskJpaRepository taskJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    public AssigneeServiceImpl(TaskJpaRepository taskJpaRepository, MemberJpaRepository memberJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public ResponseDto<Integer> assignMemberToTask(AssigneePostRequest assigneePostRequest) {

        ResponseDto<Integer> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.POST);

        String uuidMember = assigneePostRequest.getUuidMember();
        String idTask = assigneePostRequest.getIdTask();

        Optional<Member> memberFound = memberJpaRepository.findByUuid(uuidMember);

        Optional<Task> taskFound = taskJpaRepository.findById(idTask);

        if (memberFound.isPresent() && taskFound.isPresent()) {
                Member member = memberFound.get();
                Task task = taskFound.get();

                List<Task> memberTasks = member.getTasks();

                boolean isAlreadyPresent = false;

                for (Task oldTask : memberTasks) {
                    if (oldTask.getId().equals(task.getId())) {
                        isAlreadyPresent = true;
                    }
                }

                if (isAlreadyPresent) {
                    response.setHttpResponse(HttpResponseType.NOT_CREATED);
                    response.setCode(HttpResponseType.NOT_CREATED.getCode());
                    response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
                    response.setBody(0);
                } else {
                    memberTasks.add(task);
                    response.setHttpResponse(HttpResponseType.CREATED);
                    response.setCode(HttpResponseType.CREATED.getCode());
                    response.setDesc(HttpResponseType.CREATED.getDesc());
                    response.setBody(1);
                }
        } else {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
            response.setBody(0);
        }
        return response;
    }
}
