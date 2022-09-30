package com.euris.academy2022.concordia.businessLogics.impls;

import com.euris.academy2022.concordia.businessLogics.services.UserService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.User;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.responses.ResponseDto;
import com.euris.academy2022.concordia.jpaRepositories.UserJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.UserRole;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserJpaRepository userJpaRepository;

  public UserServiceImpl(UserJpaRepository userJpaRepository) {
    this.userJpaRepository = userJpaRepository;
  }

  @Override
  public ResponseDto<User> insert(User user) {
    ResponseDto<User> response = new ResponseDto<>();

    Integer userCreated = userJpaRepository.insert(
        user.getRole().getLabel(),
        user.getUsername(),
        user.getPassword());

    response.setHttpRequest(HttpRequestType.POST);

    if (userCreated != 1) {
      response.setHttpResponse(HttpResponseType.NOT_CREATED);
      response.setCode(HttpResponseType.NOT_CREATED.getCode());
      response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
    } else {
      response.setHttpResponse(HttpResponseType.CREATED);
      response.setCode(HttpResponseType.CREATED.getCode());
      response.setDesc(HttpResponseType.CREATED.getDesc());
      response.setBody(user);
    }
    return response;
  }

  @Override
  public ResponseDto<User> update(User user) {
    ResponseDto<User> response = new ResponseDto<>();
    Optional<User> userFound = userJpaRepository.findByUuid(user.getUuid());

    if (userFound.isEmpty()) {
      response.setHttpRequest(HttpRequestType.GET);
      response.setHttpResponse(HttpResponseType.NOT_FOUND);
      response.setCode(HttpResponseType.NOT_FOUND.getCode());
      response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    } else {

      Integer userUpdated = userJpaRepository.update(
          user.getUuid(),
          user.getRole().getLabel(),
          user.getUsername(),
          user.getPassword());

      response.setHttpRequest(HttpRequestType.PUT);

      if (userUpdated != 1) {
        response.setHttpResponse(HttpResponseType.NOT_UPDATED);
        response.setCode(HttpResponseType.NOT_UPDATED.getCode());
        response.setDesc(HttpResponseType.NOT_UPDATED.getDesc());
      } else {
        response.setHttpResponse(HttpResponseType.UPDATED);
        response.setCode(HttpResponseType.UPDATED.getCode());
        response.setDesc(HttpResponseType.UPDATED.getDesc());
        response.setBody(user);
      }
    }
    return response;
  }

  @Override
  public ResponseDto<User> removeByUuid(String uuid) {
    ResponseDto<User> response = new ResponseDto<>();
    Optional<User> userFound = userJpaRepository.findByUuid(uuid);

    if (userFound.isEmpty()) {
      response.setHttpRequest(HttpRequestType.GET);
      response.setHttpResponse(HttpResponseType.NOT_FOUND);
      response.setCode(HttpResponseType.NOT_FOUND.getCode());
    } else {
      Integer userDeleted = userJpaRepository.removeByUuid(uuid);

      response.setHttpRequest(HttpRequestType.DELETE);

      if (userDeleted != 1) {
        response.setHttpResponse(HttpResponseType.NOT_DELETED);
        response.setCode(HttpResponseType.NOT_DELETED.getCode());
        response.setDesc(HttpResponseType.NOT_DELETED.getDesc());
      } else {
        response.setHttpResponse(HttpResponseType.DELETED);
        response.setCode(HttpResponseType.DELETED.getCode());
        response.setDesc(HttpResponseType.DELETED.getDesc());
        response.setBody(userFound.get());
      }
    }

    return response;
  }

  @Override
  public ResponseDto<User> getByUuid(String uuid) {
    ResponseDto<User> response = new ResponseDto<>();
    Optional<User> userFound = userJpaRepository.findByUuid(uuid);

    response.setHttpRequest(HttpRequestType.GET);

    if (userFound.isEmpty()) {
      response.setHttpResponse(HttpResponseType.NOT_FOUND);
      response.setCode(HttpResponseType.NOT_FOUND.getCode());
      response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    } else {
      response.setHttpResponse(HttpResponseType.FOUND);
      response.setCode(HttpResponseType.FOUND.getCode());
      response.setDesc(HttpResponseType.FOUND.getDesc());
      response.setBody(userFound.get());
    }
    return response;
  }

  @Override
  public ResponseDto<List<User>> getAll() {
    ResponseDto<List<User>> response = new ResponseDto<>();
    List<User> userListFound = userJpaRepository.findAll();

    response.setHttpRequest(HttpRequestType.GET);

    if (userListFound.isEmpty()) {
      response.setHttpResponse(HttpResponseType.NOT_FOUND);
      response.setCode(HttpResponseType.NOT_FOUND.getCode());
      response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    } else {
      response.setHttpResponse(HttpResponseType.FOUND);
      response.setCode(HttpResponseType.FOUND.getCode());
      response.setDesc(HttpResponseType.FOUND.getDesc());
      response.setBody(userListFound);
    }

    return response;
  }

  @Override
  public ResponseDto<List<User>> getByRole(UserRole role) {
    ResponseDto<List<User>> response = new ResponseDto<>();
    List<User> userListFound = userJpaRepository.findByRole(role.getLabel());

    response.setHttpRequest(HttpRequestType.GET);

    if (userListFound.isEmpty()) {
      response.setHttpResponse(HttpResponseType.NOT_FOUND);
      response.setCode(HttpResponseType.NOT_FOUND.getCode());
      response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    } else {
      response.setHttpResponse(HttpResponseType.FOUND);
      response.setCode(HttpResponseType.FOUND.getCode());
      response.setDesc(HttpResponseType.FOUND.getDesc());
      response.setBody(userListFound);
    }

    return response;
  }

  @Override
  public ResponseDto<User> getByUsername(String username) {
    ResponseDto<User> response = new ResponseDto<>();
    Optional<User> userFound = userJpaRepository.findByUsername(username);

    response.setHttpRequest(HttpRequestType.GET);

    if (userFound.isEmpty()) {
      response.setHttpResponse(HttpResponseType.NOT_FOUND);
      response.setCode(HttpResponseType.NOT_FOUND.getCode());
      response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    } else {
      response.setHttpResponse(HttpResponseType.FOUND);
      response.setCode(HttpResponseType.FOUND.getCode());
      response.setDesc(HttpResponseType.FOUND.getDesc());
      response.setBody(userFound.get());
    }
    return response;
  }
}