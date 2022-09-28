package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.users;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;

import java.time.LocalDateTime;

public class CommentPostRequest implements DtoArchetype {


    String text;

    LocalDateTime lastUpdate;

    @Override
    public Comment toModel() {
        return Comment.builder()
                .text(this.text)
                .lastUpdate(this.lastUpdate)
                .build();
    }
}
