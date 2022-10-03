package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.utils.enums.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MemberDto implements DtoArchetype {

    private String uuid;
    private String idTrelloMember;
    private String username;
    private String password;
    private MemberRole role;
    private String name;
    private String surname;

    @Override
    public Member toModel() {
        return Member.builder()
                .uuid(this.uuid)
                .idTrelloMember(this.idTrelloMember)
                .username(this.username)
                .password(this.password)
                .role(this.role)
                .name(this.name)
                .surname(this.surname)
                .build();
    }
}