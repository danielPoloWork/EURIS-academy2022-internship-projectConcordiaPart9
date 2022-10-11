package com.euris.academy2022.concordia.dataPersistences.models;

import com.euris.academy2022.concordia.dataPersistences.archetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.DTOs.AssigneeDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;

import java.time.LocalDateTime;

import static com.euris.academy2022.concordia.utils.constants.AssigneeConstant.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = TABLE_NAME)
public class Assignee implements ModelArchetype {

    @Id
    @Column(name = COLUMN_UUID)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_UUID_MEMBER)
    @JsonBackReference(value = FK_MEMBER)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_ID_TASK)
    @JsonBackReference(value = FK_TASK)
    private Task task;

    @Column(name = COLUMN_DATE_CREATION)
    LocalDateTime dateCreation;

    @Override
    public AssigneeDto toDto() {
        return AssigneeDto.builder()
                .uuid(this.uuid)
                .memberDto(this.member.toDto())
                .taskDto(this.task.toDto())
                .build();
    }
}