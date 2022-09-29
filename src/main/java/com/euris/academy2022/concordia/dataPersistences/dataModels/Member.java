package com.euris.academy2022.concordia.dataPersistences.dataModels;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.MemberDto;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Member")
public class Member implements ModelArchetype {


    private static final String KEY_PK = "pkMember";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURNAME = "surname";
    private static final String COLUMN_ROLE = "role";

    @Id
    @Column(name = COLUMN_ID)
    private String id;

    @Column(name = COLUMN_NAME)
    private String name;

    @Column(name = COLUMN_SURNAME)
    private String surname;

    @Column(name = COLUMN_ROLE)
    @Enumerated(EnumType.STRING)
    private MemberRole role;


    @Override
    public MemberDto toDto() {
        return MemberDto.builder()
                .id(this.id)
                .name(this.name)
                .surname(this.surname)
                .role(this.role)
                .build();
    }
}
