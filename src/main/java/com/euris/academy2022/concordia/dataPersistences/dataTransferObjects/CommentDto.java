package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;
import lombok.*;

import javax.persistence.Column;
import java.lang.reflect.Member;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentDto implements DtoArchetype {


    String id;

    String text;

    LocalDateTime lastUpdate;


    @Override
    public ModelArchetype toModel() {

        return null;
    }
}
