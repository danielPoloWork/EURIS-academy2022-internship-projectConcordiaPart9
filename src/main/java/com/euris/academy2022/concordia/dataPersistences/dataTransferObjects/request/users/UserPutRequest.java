package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.request.users;

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
public class UserPutRequest implements DtoArchetype {

  private String uuid;
  private UserRole role;
  private String password;

  @Override
  public User toModel() {
    return User.builder()
        .uuid(this.uuid)
        .role(this.role)
        .password(this.password)
        .build();
  }
}
