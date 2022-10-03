package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloCommentDto {

    private String id;
    private String idMemberCreator;
    private String text;
    private String type;
    private String date;
}