package com.euris.academy2022.concordia.dataPersistences.DTOs;

import com.euris.academy2022.concordia.dataPersistences.archetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.models.Comment;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentDto implements DtoArchetype {

    private String uuid;
    private String idTrelloComment;
    private TaskDto taskDto;
    private MemberDto memberDto;
    private String text;
    private LocalDateTime dateCreation;
    private LocalDateTime dateUpdate;

    @Override
    public Comment toModel() {
        return Comment.builder()
                .uuid(this.uuid)
                .idTrelloComment(this.idTrelloComment)
                .task(this.taskDto.toModel())
                .member(this.memberDto.toModel())
                .text(this.text)
                .dateCreation(this.dateCreation)
                .dateUpdate(this.dateUpdate)
                .build();
    }
}
