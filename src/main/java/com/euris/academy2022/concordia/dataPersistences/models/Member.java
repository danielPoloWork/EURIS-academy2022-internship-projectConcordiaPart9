package com.euris.academy2022.concordia.dataPersistences.models;

import com.euris.academy2022.concordia.dataPersistences.archetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.utils.constants.AssigneeConstant;
import com.euris.academy2022.concordia.utils.constants.CommentConstant;
import com.euris.academy2022.concordia.utils.enums.MemberRole;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.euris.academy2022.concordia.utils.constants.MemberConstant.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = TABLE_NAME)
public class Member implements ModelArchetype {

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

    @Column(name = COLUMN_FIRST_NAME)
    private String firstName;

    @Column(name = COLUMN_LAST_NAME)
    private String lastName;

    @Column(name = COLUMN_DATE_CREATION)
    LocalDateTime dateCreation;

    @Column(name = COLUMN_DATE_UPDATE)
    LocalDateTime dateUpdate;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = JOIN_MAPPED_BY)
    @JsonManagedReference(value = AssigneeConstant.FK_MEMBER)
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Assignee> assignees;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = JOIN_MAPPED_BY)
    @JsonManagedReference(value = CommentConstant.FK_MEMBER)
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Comment> comments;

    @Override
    public MemberDto toDto() {
        return MemberDto.builder()
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