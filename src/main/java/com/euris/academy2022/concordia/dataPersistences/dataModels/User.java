package com.danielpolo.game.dataPersistences.dataModels;

import com.danielpolo.game.dataPersistences.dataArchetypes.ModelArchetype;
import com.danielpolo.game.utils.enums.AuthRoleEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
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
@Table(name = "authorization")
public class User implements ModelArchetype {

  // CONSTANTS -------------------------------------------------------------------------------------
  /* Table columns */
  private static final String COLUMN_PK_UUID = "uuid";
  private static final String COLUMN_ROLE = "role";
  private static final String COLUMN_USERNAME = "username";
  private static final String COLUMN_PASSWORD = "password";

  // COLUMNS ---------------------------------------------------------------------------------------
  @Id
  @Column(name = COLUMN_PK_UUID)
  private String uuid;

  @Enumerated(EnumType.STRING)
  @Column(name = COLUMN_ROLE)
  private AuthRoleEnum role;

  @Column(name = COLUMN_USERNAME)
  private String username;

  @Column(name = COLUMN_PASSWORD)
  private String password;

  // METHODS ---------------------------------------------------------------------------------------
  @Override
  public com.danielpolo.game.dataPersistences.dataTransferObjects.defaults.UserDto toDto() {
    return com.danielpolo.game.dataPersistences.dataTransferObjects.defaults.UserDto.builder()
        .uuid(this.uuid)
        .role(this.role)
        .username(this.username)
        .password(this.password)
        .build();
  }
}