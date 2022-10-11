package com.euris.academy2022.concordia.dataPersistences.DTOs;

import com.euris.academy2022.concordia.dataPersistences.archetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.models.Assignee;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AssigneeDto implements DtoArchetype {

    private String uuid;
    private MemberDto memberDto;
    private TaskDto taskDto;
    private LocalDateTime dateCreation;

    @Override
    public Assignee toModel() {
        return Assignee.builder()
                .uuid(this.uuid)
                .member(this.memberDto.toModel())
                .task(this.taskDto.toModel())
                .dateCreation(this.dateCreation)
                .build();
    }
}