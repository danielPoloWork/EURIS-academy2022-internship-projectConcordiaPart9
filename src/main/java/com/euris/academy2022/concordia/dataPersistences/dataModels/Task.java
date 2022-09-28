package com.euris.academy2022.concordia.dataPersistences.dataModels;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.UserDto;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import com.euris.academy2022.concordia.utils.enums.UserRole;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Task")
public class Task implements ModelArchetype {

    private static final String KEY_PK = "pkTask";

    private static final String COLUMN_ID = "uuid";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRIORITY = "priority";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_DEADLINE = "deadline";

    @Id
    @Column(name = COLUMN_ID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_TITLE)
    private String title;

    @Column(name = COLUMN_DESCRIPTION)
    private String description;

    @Column(name = COLUMN_PRIORITY)
    private TaskPriority priority;

    @Column(name = COLUMN_STATUS)
    private TaskStatus status;

    @Column(name = COLUMN_DEADLINE)
    private LocalDateTime deadLine;

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
