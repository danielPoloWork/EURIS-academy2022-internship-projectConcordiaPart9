package com.euris.academy2022.concordia.dataPersistences.DTOs.requests.connectionWindow;


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
public class ConnectionWindowPutRequest implements DtoArchetype {

    private int month;
    private String cron;
    private LocalDateTime dateUpdate;

    @Override
    public ConnectionWindow toModel() {
        return ConnectionWindow.builder()
                .month(this.month)
                .cron(this.cron)
                .dateUpdate(this.dateUpdate)
                .build();
    }
}
