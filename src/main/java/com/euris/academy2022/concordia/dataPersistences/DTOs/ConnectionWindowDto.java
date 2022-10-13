package com.euris.academy2022.concordia.dataPersistences.DTOs;


import com.euris.academy2022.concordia.dataPersistences.archetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.archetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.models.ConnectionWindow;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ConnectionWindowDto implements DtoArchetype {

    private int month;

    private String cron;

    private LocalDateTime dateCreation;

    private LocalDateTime dateUpdate;

    @Override
    public ConnectionWindow toModel() {
        return ConnectionWindow.builder()
                .cron(this.cron)
                .month(this.month)
                .dateCreation(this.dateCreation)
                .dateUpdate(this.dateUpdate)
                .build();
    }
}
