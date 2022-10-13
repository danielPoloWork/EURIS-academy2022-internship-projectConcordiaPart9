package com.euris.academy2022.concordia.businessLogics.impls;

import com.euris.academy2022.concordia.businessLogics.services.UserDetailsManagerService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsManagerImplService implements UserDetailsManagerService {

  private final UserDetailsManager userDetailsManager;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserDetailsManagerImplService(UserDetailsManager userDetailsManager, BCryptPasswordEncoder passwordEncoder) {
    this.userDetailsManager = userDetailsManager;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Optional<User> insert(User user) {
    Optional<User> userFound = Optional.ofNullable(user);
    Optional<User> response = Optional.empty();

    if (userFound.isPresent()) {
        if (!userDetailsManager.userExists(userFound.get().getUsername())) {
          userDetailsManager.createUser(
              org.springframework.security.core.userdetails.User
              .withUsername(userFound.get().getUsername())
              .password(passwordEncoder.encode(userFound.get().getPassword()))
              .roles(userFound.get().getRole().toString())
              .build());
        }
      response = userFound;
    }

    return response;
  }

  @Override
  public Optional<User> update(User user) {
    Optional<User> userFound = Optional.ofNullable(user);
    Optional<User> response = Optional.empty();

    if (userFound.isPresent()) {
      if (userDetailsManager.userExists(userFound.get().getUsername())) {
        userDetailsManager.updateUser(
            org.springframework.security.core.userdetails.User
            .withUsername(userFound.get().getUsername())
            .password(passwordEncoder.encode(userFound.get().getPassword()))
            .roles(userFound.get().getRole().toString())
            .build());
      }
      response = userFound;
    }
    return response;
  }

  @Override
  public Optional<String> deleteByUsername(String username) {
    Optional<String> response = Optional.empty();

    if (!username.isEmpty()) {
      if (userDetailsManager.userExists(username)) {
        userDetailsManager.deleteUser(username);
      }
      response = Optional.of(username);
    }
    return response;
  }

  @Override
  public List<User> getUserListByUserList(List<User> userList) {
    List<User> response = new ArrayList<>();

    if (!userList.isEmpty()) {
      userList.forEach(user -> {
        if (!userDetailsManager.userExists(user.getUsername())) {
          userDetailsManager.createUser(
              org.springframework.security.core.userdetails.User
                  .withUsername(user.getUsername())
                  .password(passwordEncoder.encode(user.getPassword()))
                  .roles(user.getRole().toString())
                  .build());
        }
      });
      response = userList;
    }
    return response;
  }
}