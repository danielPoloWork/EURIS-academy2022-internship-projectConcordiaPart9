package com.euris.academy2022.concordia.dataPersistences.dataModels;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.MemberDto;
import com.euris.academy2022.concordia.utils.enums.MemberRole;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private static final String MAPPED_BY = "member";
    private static final String FK_ASSIGNEE_MEMBER = "fkAssigneeMember";
    private static final String FK_COMMENT_MEMBER = "fkCommentMember";

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = MAPPED_BY)
    @JsonManagedReference(value = FK_ASSIGNEE_MEMBER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Assignee> assignees;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = MAPPED_BY)
    @JsonManagedReference(value = FK_COMMENT_MEMBER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Comment> comments;

    @Override
    public MemberDto toDto() {
        return MemberDto.builder()
                .uuid(this.uuid)
                .idTrelloMember(this.idTrelloMember)
                .username(this.username)
                .role(this.role)
                .name(this.name)
                .surname(this.surname)
                .build();
    }
}