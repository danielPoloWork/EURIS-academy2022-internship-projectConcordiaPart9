package com.euris.academy2022.concordia.dataPersistences.models;


import com.euris.academy2022.concordia.dataPersistences.DTOs.ConnectionWindowDto;
import com.euris.academy2022.concordia.dataPersistences.archetypes.ModelArchetype;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static com.euris.academy2022.concordia.utils.constants.ConnectionWindowConstant.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = TABLE_NAME)
public class ConnectionWindow implements ModelArchetype {

    @Id
    @Column(name = COLUMN_MONTH)
    private int month;

    @Column(name = COLUMN_CRON)
    private String cron;

    @Column(name = COLUMN_DATE_CREATION)
    LocalDateTime dateCreation;

    @Column(name = COLUMN_DATE_UPDATE)
    LocalDateTime dateUpdate;

    @Override
    public ConnectionWindowDto toDto() {
        return ConnectionWindowDto.builder()
                .cron(this.cron)
                .month(this.month)
                .dateCreation(this.dateCreation)
                .dateUpdate(this.dateUpdate)
                .build();
    }
}
