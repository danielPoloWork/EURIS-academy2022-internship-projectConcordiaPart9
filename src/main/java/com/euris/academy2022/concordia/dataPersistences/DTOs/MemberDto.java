package com.euris.academy2022.concordia.dataPersistences.DTOs;

import com.euris.academy2022.concordia.dataPersistences.archetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.utils.enums.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MemberDto implements DtoArchetype {

    private String uuid;
    private String idTrelloMember;
    private String username;
    private MemberRole role;
    private String firstName;
    private String lastName;
    private LocalDateTime dateCreation;
    private LocalDateTime dateUpdate;

    @Override
    public Member toModel() {
        return Member.builder()
                .uuid(this.uuid)
                .idTrelloMember(this.idTrelloMember)
                .username(this.username)
                .role(this.role)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .dateCreation(this.dateCreation)
                .dateUpdate(this.dateUpdate)
                .build();
    }
}