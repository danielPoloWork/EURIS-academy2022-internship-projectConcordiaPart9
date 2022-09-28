package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.users;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentPutRequest implements DtoArchetype {

    String id;

    String text;

    LocalDateTime lastUpdate;

    @Override
    public Comment toModel() {
        return Comment.builder()
                .id(this.id)
                .text(this.text)
                .lastUpdate(this.lastUpdate)
                .build();
    }
}
