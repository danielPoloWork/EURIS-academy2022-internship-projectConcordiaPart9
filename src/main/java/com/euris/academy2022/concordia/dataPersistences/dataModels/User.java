package com.euris.academy2022.concordia.dataPersistences.dataModels;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.ModelArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.UserDto;
import com.euris.academy2022.concordia.utils.enums.UserRole;

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
@Table(name = "User")
public class User implements ModelArchetype {

  private static final String KEY_PK = "pkUser";

  private static final String COLUMN_UUID = "uuid";
  private static final String COLUMN_ROLE = "role";
  private static final String COLUMN_USERNAME = "username";
  private static final String COLUMN_PASSWORD = "password";

  @Id
  @Column(name = COLUMN_UUID)
  private String uuid;

  @Enumerated(EnumType.STRING)
  @Column(name = COLUMN_ROLE)
  private UserRole role;

  @Column(name = COLUMN_USERNAME)
  private String username;

  @Column(name = COLUMN_PASSWORD)
  private String password;

  @Override
  public UserDto toDto() {
    return UserDto.builder()
        .uuid(this.uuid)
        .role(this.role)
        .username(this.username)
        .password(this.password)
        .build();
  }
}