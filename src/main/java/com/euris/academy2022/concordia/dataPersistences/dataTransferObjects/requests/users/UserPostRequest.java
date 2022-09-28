package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.users;

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
public class UserPostRequest implements DtoArchetype {

  // FIELDS ----------------------------------------------------------------------------------------
  private UserRole role;
  private String username;
  private String password;

  // METHODS ---------------------------------------------------------------------------------------
  @Override
  public User toModel() {
    return User.builder()
        .role(this.role)
        .username(this.username)
        .password(this.password)
        .build();
  }
}
