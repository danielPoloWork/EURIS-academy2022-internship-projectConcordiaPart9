package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.assignees;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Assignee;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TaskDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AssigneePostRequest implements DtoArchetype {

    private String uuidMember;
    private String idTask;
    @Override
    public Assignee toModel() {

            MemberDto memberDto = new MemberDto();
            memberDto.setUuid(uuidMember);

            TaskDto taskDto = new TaskDto();
            taskDto.setId(idTask);

        return Assignee.builder()
                .member(memberDto.toModel())
                .task(taskDto.toModel())
                .build();
    }
}