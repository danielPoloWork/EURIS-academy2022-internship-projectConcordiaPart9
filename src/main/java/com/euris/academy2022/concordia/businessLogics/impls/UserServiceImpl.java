package com.euris.academy2022.concordia.businessLogics.impls;

import com.euris.academy2022.concordia.businessLogics.services.UserService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.User;
import com.euris.academy2022.concordia.jpaRepositories.UserJpaRepository;
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
  public Optional<User> insert(User user) {
    return userJpaRepository.insert(
          user.getRole().getLabel(),
          user.getUsername(),
          user.getPassword());
  }

  @Override
  public Optional<User> update(User user) {
    Optional<User> userFound = getByUuid(user.getUuid());

    return userFound.isEmpty() ? Optional.empty() : Optional.of(userJpaRepository.save(user));
  }

  @Override
  public Boolean deleteByUuid(String uuid) {
    return userJpaRepository.deleteByUuid(uuid);
  }

  @Override
  public Optional<User> getByUuid(String uuid) {
   return userJpaRepository.findByUuid(uuid);
  }

  @Override
  public List<User> getAll() {
    return userJpaRepository.findAll();
  }

  @Override
  public List<User> getByRole(UserRole role) {
    return userJpaRepository.findByRole(role.getLabel());
  }

  @Override
  public Optional<User> getByUsername(String username) {
    return userJpaRepository.findByUsername(username);
  }
}
