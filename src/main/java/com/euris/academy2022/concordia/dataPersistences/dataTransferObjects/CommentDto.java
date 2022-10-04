package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
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

    private MemberDto memberDto;
    private TaskDto taskDto;



    @Override
    public Comment toModel() {


        return Comment.builder()
                .uuid(this.uuid)
                .text(this.text)
                .member(this.memberDto.toModel())
                .task(this.taskDto.toModel())
                .lastUpdate(this.lastUpdate)
                .idTrelloComment(this.idTrelloComment)
                .build();

    }
}
