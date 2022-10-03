package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.comments;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentPutRequest implements DtoArchetype {

    private String uuid;

    private String text;

    private LocalDateTime lastUpdate;

    @Override
    public Comment toModel() {
        return Comment.builder()
                .uuid(uuid)
                .text(text)
                .lastUpdate(LocalDateTime.now())
                .build();
    }
}
