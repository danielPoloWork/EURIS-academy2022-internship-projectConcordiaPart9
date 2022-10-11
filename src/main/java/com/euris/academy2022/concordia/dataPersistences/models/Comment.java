package com.euris.academy2022.concordia.dataPersistences.models;

import com.euris.academy2022.concordia.dataPersistences.archetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.DTOs.CommentDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.euris.academy2022.concordia.utils.constants.CommentConstant.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = TABLE_NAME)
public class Comment implements ModelArchetype {

    @Id
    @Column(name = COLUMN_UUID)
    String uuid;

    @JoinColumn(name = COLUMN_ID_TRELLO_COMMENT)
    String idTrelloComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_ID_TASK)
    @JsonBackReference(value = FK_TASK)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_UUID_MEMBER)
    @JsonBackReference(value = FK_MEMBER)
    private Member member;

    @Column(name = COLUMN_TEXT)
    String text;

    @Column(name = COLUMN_DATE_CREATION)
    LocalDateTime dateCreation;

    @Column(name = COLUMN_DATE_UPDATE)
    LocalDateTime dateUpdate;

    @Override
    public CommentDto toDto() {
        return CommentDto.builder()
                .uuid(this.uuid)
                .taskDto(this.task.toDto())
                .memberDto(this.member.toDto())
                .dateCreation(this.dateCreation)
                .dateUpdate(this.dateUpdate)
                .text(this.text)
                .build();
    }
}