package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloCardDto {

    private String id;
    private String idBoard;
    private String idList;
    private String name;
    private String desc;
    private String idLabel;
    private String due;
    private String dateLastActivity;
}