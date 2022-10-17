package com.euris.academy2022.concordia.dataPersistences.DTOs.requests.configurations;


import com.euris.academy2022.concordia.dataPersistences.archetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.archetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.models.Configuration;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ConfigurationPutRequest implements DtoArchetype {

    private String label;
    private String value;
    private LocalDateTime dateUpdate;


    @Override
    public Configuration toModel() {
        return Configuration.builder()
                .label(this.label)
                .value(this.value)
                .dateUpdate(this.dateUpdate)
                .build();
    }
}
