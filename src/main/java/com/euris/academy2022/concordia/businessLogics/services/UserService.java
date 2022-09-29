package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataModels.User;
import com.euris.academy2022.concordia.utils.enums.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService {

  Optional<User> insert(User user);
  Optional<User> update(User user);
  Boolean deleteByUuid(String uuid);
  Optional<User> getByUuid(String uuid);
  List<User> getAll();
  List<User> getByRole(UserRole role);
  Optional<User> getByUsername(String username);
}