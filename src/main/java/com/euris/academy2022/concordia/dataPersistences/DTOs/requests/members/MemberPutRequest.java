package com.euris.academy2022.concordia.dataPersistences.DTOs.requests.members;

import com.euris.academy2022.concordia.dataPersistences.archetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MemberPutRequest implements DtoArchetype {

    private String uuid;
    private String password;
    private MemberRole role;
    private String firstName;
    private String lastName;
    private LocalDateTime dateCreation;
    private LocalDateTime dateUpdate;

    @Override
    public Member toModel() {
        return Member.builder()
                .uuid(this.uuid)
                .password(this.password)
                .role(this.role)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .dateUpdate(this.dateUpdate)
                .build();
    }
}