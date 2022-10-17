package com.euris.academy2022.concordia.dataPersistences.models;

import static com.euris.academy2022.concordia.utils.constants.ConfigurationConstant.*;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ConfigurationDto;
import com.euris.academy2022.concordia.dataPersistences.archetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.archetypes.ModelArchetype;
import com.euris.academy2022.concordia.utils.constants.ConfigurationConstant;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity
@Table(name = TABLE_NAME)
public class Configuration implements ModelArchetype {

    @Id
    @Column(name = COLUMN_LABEL)
    private String label;

    @Column(name = COLUMN_VALUE)
    private String value;

    @Column(name = COLUMN_DATE_CREATION)
    LocalDateTime dateCreation;

    @Column(name = COLUMN_DATE_UPDATE)
    LocalDateTime dateUpdate;


    @Override
    public ConfigurationDto toDto() {
        return ConfigurationDto.builder()
                .label(this.label)
                .value(this.value)
                .dateCreation(this.dateCreation)
                .dateUpdate(this.dateUpdate)
                .build();
    }
}
