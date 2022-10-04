package com.euris.academy2022.concordia.dataPersistences.dataModels;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.AssigneeDto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Assignee")
public class Assignee implements ModelArchetype {

    private static final String KEY_PK = "pkAssignee";

    private static final String COLUMN_UUID = "uuid";
    private static final String COLUMN_UUID_MEMBER = "uuidMember";
    private static final String COLUMN_ID_TASK = "idTask";

    private static final String FK_ASSIGNEE_MEMBER = "fkAssigneeMember";
    private static final String FK_ASSIGNEE_TASK = "fkAssigneeTask";

    @Id
    @Column(name = COLUMN_UUID)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_UUID_MEMBER)
    @JsonBackReference(value = FK_ASSIGNEE_MEMBER)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_ID_TASK)
    @JsonBackReference(value = FK_ASSIGNEE_TASK)
    private Task task;

    @Override
    public AssigneeDto toDto() {
        return AssigneeDto.builder()
                .uuid(this.uuid)
                .memberDto(this.member.toDto())
                .taskDto(this.task.toDto())
                .build();
    }
}