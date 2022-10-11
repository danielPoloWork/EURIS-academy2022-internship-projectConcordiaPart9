package com.euris.academy2022.concordia.dataPersistences.DTOs.requests.tasks;

import com.euris.academy2022.concordia.dataPersistences.archetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TaskPostRequest implements DtoArchetype {

    private String id;
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDateTime deadLine;
    private LocalDateTime dateCreation;
    private LocalDateTime dateUpdate;

    @Override
    public Task toModel() {
        return Task.builder()
                .id(id)
                .title(title)
                .description(description)
                .priority(priority)
                .status(status)
                .deadLine(deadLine)
                .dateCreation(this.dateCreation)
                .dateUpdate(this.dateUpdate)
                .build();
    }
}
