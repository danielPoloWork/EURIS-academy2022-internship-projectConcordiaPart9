package com.euris.academy2022.concordia.dataPersistences.DTOs.requests.assignees;

import com.euris.academy2022.concordia.dataPersistences.archetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.models.Assignee;
import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AssigneePostRequest implements DtoArchetype {

    private String uuidMember;
    private String idTask;
    private LocalDateTime dateCreation;
    @Override
    public Assignee toModel() {

            MemberDto memberDto = new MemberDto();
            memberDto.setUuid(this.uuidMember);

            TaskDto taskDto = new TaskDto();
            taskDto.setId(this.idTask);

        return Assignee.builder()
                .member(memberDto.toModel())
                .task(taskDto.toModel())
                .dateCreation(this.dateCreation)
                .build();
    }
}