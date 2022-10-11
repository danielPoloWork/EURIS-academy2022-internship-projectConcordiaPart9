package com.euris.academy2022.concordia.dataPersistences.DTOs.requests.comments;

import com.euris.academy2022.concordia.dataPersistences.archetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.models.Comment;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentPostRequest implements DtoArchetype {

    private String idTrelloComment;
    private String idTask;
//    private String idTrelloMember;
//    private String uuidMember;
    private String text;
    private LocalDateTime dateCreation;
    private LocalDateTime dateUpdate;

    @Override
    public Comment toModel() {
        Task task = Task.builder()
                .id(this.idTask).build();

//        Member member = Member.builder()
//                .uuid(this.uuidMember).build();

        return Comment.builder()
                .idTrelloComment(this.idTrelloComment)
                .task(task)
//                .idTrelloMember(this.idTrelloMember)
//                .member(member)
                .text(this.text)
                .dateCreation(this.dateCreation)
                .dateUpdate(this.dateUpdate)
                .build();
    }
}
