package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataModels.User;

import java.util.List;
import java.util.Optional;

public interface UserDetailsManagerService {
  Optional<User> insert(User user);
  Optional<User> update(User user);
  Optional<String> deleteByUsername(String username);
  List<User> getUserListByUserList(List<User> userList);
}
