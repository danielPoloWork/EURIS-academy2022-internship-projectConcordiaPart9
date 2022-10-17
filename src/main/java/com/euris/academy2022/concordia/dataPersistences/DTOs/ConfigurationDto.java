package com.euris.academy2022.concordia.dataPersistences.DTOs;

import com.euris.academy2022.concordia.dataPersistences.archetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.archetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.models.Configuration;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ConfigurationDto implements DtoArchetype {

    private String label;

    private String value;

    private LocalDateTime dateCreation;

    private LocalDateTime dateUpdate;

    @Override
    public Configuration toModel() {
        return Configuration.builder()
                .label(this.label)
                .value(this.value)
                .dateCreation(this.dateCreation)
                .dateUpdate(this.dateUpdate)
                .build();
    }
}
