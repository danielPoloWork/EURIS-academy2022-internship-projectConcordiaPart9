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

    private static final String COLUMN_UUID = "uuid";
    private static final String COLUMN_IDTRELLOCOMMENT = "idTrelloComment";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_LASTUPDATE = "lastUpdate";

    private static final String COLUMN_UUID_MEMBER = "uuidMember";
    private static final String COLUMN_ID_TASK = "idTask";

    private static final String FK_COMMENT_MEMBER = "fkCommentMember";
    private static final String FK_COMMENT_TASK = "fkCommentTask";

    @Id
    @Column(name = COLUMN_UUID)
    String uuid;

    @Column(name = COLUMN_IDTRELLOCOMMENT)
    String idTrelloComment;

    @Column(name = COLUMN_TEXT)
    String text;

    @Column(name = COLUMN_LASTUPDATE)
    LocalDateTime lastUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_UUID_MEMBER)
    @JsonBackReference(value = FK_COMMENT_MEMBER)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_ID_TASK)
    @JsonBackReference(value = FK_COMMENT_TASK)
    private Task task;

    @Override
    public CommentDto toDto() {
        return CommentDto.builder()
                .uuid(this.uuid)
                .memberDto(this.member.toDto())
                .taskDto(this.task.toDto())
                .idTrelloComment(this.idTrelloComment)
                .lastUpdate(this.lastUpdate)
                .text(this.text)
                .build();
    }
}