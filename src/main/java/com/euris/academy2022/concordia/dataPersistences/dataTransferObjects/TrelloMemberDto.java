package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloMemberDto {

    private String id;
    private String fullName;
    private String username;
}