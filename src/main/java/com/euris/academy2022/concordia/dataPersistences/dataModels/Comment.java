package com.euris.academy2022.concordia.dataPersistences.dataModels;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.CommentDto;
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

    private static final String COLUMN_IDTRELLOCOMMENT = "idTrelloComment";

    private static final String COLUMN_UUID = "uuid";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_LASTUPDATE = "lastUpdate";

    @Id
    @Column(name = COLUMN_UUID)
    String uuid;

    @Column(name = COLUMN_TEXT)
    String text;
    @Column(name = COLUMN_LASTUPDATE)
    LocalDateTime lastUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTask")
    @JsonBackReference(value = "fkCommentTask")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuidMember")
    @JsonBackReference(value = "fkCommentMember")
    private Member member;

    @Column(name = COLUMN_IDTRELLOCOMMENT)
    String idTrelloComment;

    @Override
    public CommentDto toDto() {

        return CommentDto.builder()
                .uuid(this.uuid)
                .member(this.member)
                .task(this.task)
                .idTrelloComment(this.idTrelloComment)
                .lastUpdate(this.lastUpdate)
                .text(this.text)
                .build();
    }
}
