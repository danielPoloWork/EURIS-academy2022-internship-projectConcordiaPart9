package com.euris.academy2022.concordia.dataPersistences.DTOs.requests.assignees;

import com.euris.academy2022.concordia.dataPersistences.archetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.models.Assignee;
import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AssigneeDeleteRequest implements DtoArchetype {

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
