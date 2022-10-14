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
public class CommentFromTrelloDto implements DtoArchetype {

    private String uuid;
    private String idTrelloComment;
    private String text;
    private LocalDateTime dateUpdate;

    @Override
    public Comment toModel() {
        return Comment.builder()
                .uuid(this.uuid)
                .idTrelloComment(this.idTrelloComment)
                .text(this.text)
                .dateUpdate(this.dateUpdate)
                .build();
    }
}
