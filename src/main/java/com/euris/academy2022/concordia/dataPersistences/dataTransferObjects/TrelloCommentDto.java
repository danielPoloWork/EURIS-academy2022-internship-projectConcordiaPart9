package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloCommentDto {

    private String id;
    private String idMemberCreator;
    private String text;
    private String type;
    private String date;
}