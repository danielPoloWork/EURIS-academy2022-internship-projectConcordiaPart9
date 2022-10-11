package com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloMemberDto {

    private String id;
    private String fullName;
    private String username;
}