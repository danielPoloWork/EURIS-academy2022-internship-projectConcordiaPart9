package com.euris.academy2022.concordia.dataPersistences.dataModels;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.MemberDto;
import com.euris.academy2022.concordia.utils.enums.MemberRole;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
@Entity
@Table(name = "Member")
public class Member implements ModelArchetype {

    private static final String KEY_PK = "pkMember";

    private static final String COLUMN_UUID = "uuid";
    private static final String COLUMN_ID_TRELLO_MEMBER = "idTrelloMember";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURNAME = "surname";

    private static final String JOIN_INTO = "Assignee";
    private static final String COLUMN_UUID_MEMBER = "idMember";
    private static final String COLUMN_ID_TASK = "idTask";

    @Id
    @Column(name = COLUMN_UUID)
    private String uuid;

    @Column(name = COLUMN_ID_TRELLO_MEMBER)
    private String idTrelloMember;

    @Column(name = COLUMN_USERNAME)
    private String username;

    @Column(name = COLUMN_PASSWORD)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_ROLE)
    private MemberRole role;

    @Column(name = COLUMN_NAME)
    private String name;

    @Column(name = COLUMN_SURNAME)
    private String surname;

    @ManyToMany
    @JoinTable(
            name = JOIN_INTO,
            joinColumns = @JoinColumn(name = COLUMN_UUID_MEMBER),
            inverseJoinColumns = @JoinColumn(name = COLUMN_ID_TASK))
    List<Task> tasks;

    @Override
    public MemberDto toDto() {
        return MemberDto.builder()
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