package com.euris.academy2022.concordia.dataPersistences.models;

import com.euris.academy2022.concordia.dataPersistences.archetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.utils.constants.models.AssigneeConstant;
import com.euris.academy2022.concordia.utils.constants.models.CommentConstant;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.euris.academy2022.concordia.utils.constants.models.TaskConstant.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = TABLE_NAME)
public class Task implements ModelArchetype {

    @Id
    @Column(name = COLUMN_ID)
    private String id;

    @Column(name = COLUMN_TITLE)
    private String title;

    @Column(name = COLUMN_DESCRIPTION)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_PRIORITY)
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_STATUS)
    private TaskStatus status;

    @Column(name = COLUMN_DEADLINE)
    private LocalDateTime deadLine;

    @Column(name = COLUMN_DATE_CREATION)
    LocalDateTime dateCreation;

    @Column(name = COLUMN_DATE_UPDATE)
    LocalDateTime dateUpdate;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = JOIN_MAPPED_BY)
    @JsonManagedReference(value = AssigneeConstant.FK_TASK)
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Assignee> assignees;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = JOIN_MAPPED_BY)
    @JsonManagedReference(value = CommentConstant.FK_TASK)
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Comment> comments;

    @Override
    public TaskDto toDto() {
        return TaskDto.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .priority(this.priority)
                .status(this.status)
                .deadLine(this.deadLine)
                .dateCreation(this.dateCreation)
                .dateUpdate(this.dateUpdate)
                .build();
    }
}