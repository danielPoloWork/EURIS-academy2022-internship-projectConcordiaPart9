package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Assignee;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AssigneeDto implements DtoArchetype {

    private String uuid;
    private MemberDto memberDto;
    private TaskDto taskDto;

    @Override
    public Assignee toModel() {
        return Assignee.builder()
                .uuid(this.uuid)
                .member(this.memberDto.toModel())
                .task(this.taskDto.toModel())
                .build();
    }
}