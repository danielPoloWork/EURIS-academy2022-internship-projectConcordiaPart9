package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloCardDto {

    private String id;
    private String idBoard;
    private String idList;
    private String name;
    private String desc;
    private String idLabels;
    private String dateLastActivity;
}