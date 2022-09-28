package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentDto implements DtoArchetype {


    private String uuid;

    private String idTrelloComment;

    private String text;

    private LocalDateTime lastUpdate;

    private Member member;
    private Task task;



    @Override
    public Comment toModel() {


        return Comment.builder()
                .uuid(this.uuid)
                .text(this.text)
                .member(this.member)
                .task(this.task)
                .lastUpdate(this.lastUpdate)
                .idTrelloComment(this.idTrelloComment)
                .build();

    }
}
