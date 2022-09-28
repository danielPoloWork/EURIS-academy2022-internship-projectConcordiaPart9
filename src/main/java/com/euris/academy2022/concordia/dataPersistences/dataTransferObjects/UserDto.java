package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects;

import com.euris.academy2022.concordia.dataPersistences.dataArchetypes.DtoArchetype;
import com.euris.academy2022.concordia.dataPersistences.dataModels.User;
import com.euris.academy2022.concordia.utils.enums.UserRole;

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
public class UserDto implements DtoArchetype {

  private String uuid;
  private UserRole role;
  private String username;
  private String password;

  @Override
  public User toModel() {
    return User.builder()
        .uuid(this.uuid)
        .role(this.role)
        .username(this.username)
        .password(this.password)
        .build();
  }
}