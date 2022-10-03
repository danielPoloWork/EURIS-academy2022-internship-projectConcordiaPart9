package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.comments;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentPostRequest implements DtoArchetype {


    private String text;

    private LocalDateTime lastUpdate;

    private String idTask;

    private String uuidMember;

    @Override
    public Comment toModel() {

        Member member = Member.builder()
                .uuid(uuidMember).build();

        Task task = Task.builder()
                .id(idTask).build();


        return Comment.builder()
                .text(text)
                .member(member)
                .task(task)
                .lastUpdate(LocalDateTime.now())
                .build();
    }
}
