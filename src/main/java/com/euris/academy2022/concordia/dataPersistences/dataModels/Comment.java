package com.euris.academy2022.concordia.dataPersistences.dataModels;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.ModelArchetype;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Comment")
public class Comment implements ModelArchetype {

    @Id
    @Column
    String id;

    @Column
    String text;
    @Column
    LocalDateTime lastUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTask")
    @JsonBackReference(value = "fkCommentTask")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMember")
    @JsonBackReference(value = "fkCommentMember")
    private Member member;


    @Override
    public DtoArchetype toDto() {
        return null;
    }
}
