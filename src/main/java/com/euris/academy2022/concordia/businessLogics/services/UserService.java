package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataModels.User;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.responses.ResponseDto;
import com.euris.academy2022.concordia.utils.enums.UserRole;

import java.util.List;

public interface UserService {

  ResponseDto<User> insert(User user);
  ResponseDto<User> update(User user);
  ResponseDto<User> removeByUuid(String uuid);
  ResponseDto<User> getByUuid(String uuid);
  ResponseDto<List<User>> getAll();
  ResponseDto<List<User>> getByRole(UserRole role);
  ResponseDto<User> getByUsername(String username);
}