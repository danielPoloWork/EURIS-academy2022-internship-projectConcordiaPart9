package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.members;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MemberPutRequest implements DtoArchetype {

    private String id;
    private String name;
    private String surname;
    private MemberRole role;


    @Override
    public Member toModel() {
        return Member.builder()
                .id(this.id)
                .name(this.name)
                .surname(this.surname)
                .role(this.role)
                .build();
    }
}

