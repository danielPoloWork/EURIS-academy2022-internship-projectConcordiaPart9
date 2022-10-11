package com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloCommentDto {

    private String id;
    private String idCard;
    private String idMemberCreator;
    private String text;
    private String type;
    private String date;
}