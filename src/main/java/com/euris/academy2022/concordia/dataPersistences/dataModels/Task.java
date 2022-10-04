package com.euris.academy2022.concordia.dataPersistences.dataModels;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TaskDto;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Task")
public class Task implements ModelArchetype {

    private static final String KEY_PK = "pkTask";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRIORITY = "priority";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_DEADLINE = "deadline";

    private static final String MAPPED_BY = "task";
    private static final String FK_ASSIGNEE_TASK = "fkAssigneeTask";
    private static final String FK_COMMENT_TASK = "fkCommentTask";

    @Id
    @Column(name = COLUMN_ID)
    private String id;

    @Column(name = COLUMN_TITLE)
    private String title;

    @Column(name = COLUMN_DESCRIPTION)
    private String description;

    @Column(name = COLUMN_PRIORITY)
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(name = COLUMN_STATUS)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = COLUMN_DEADLINE)
    private LocalDateTime deadLine;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = MAPPED_BY)
    @JsonManagedReference(value = FK_ASSIGNEE_TASK)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Assignee> assignees;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = MAPPED_BY)
    @JsonManagedReference(value = FK_COMMENT_TASK)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Comment> comments;

    @Override
    public TaskDto toDto() {
        return TaskDto.builder()
                .id(id)
                .title(title)
                .description(description)
                .priority(priority)
                .status(status)
                .deadLine(deadLine)
                .build();
    }
}