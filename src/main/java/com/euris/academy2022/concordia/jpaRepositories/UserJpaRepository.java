package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.dataPersistences.dataModels.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, String> {

  String INSERT_INTO_USER =
      "INSERT INTO User (User.uuid, User.role, User.username, User.password) "
          + "VALUES (UUID(), :role, :username, :password)";

  String UPDATE_USER =
      "UPDATE User "
          + "SET User.role = :role, User.username = :username, User.password = :password "
          + "WHERE User.uuid = :uuid";

  @Modifying
  @Query(value = INSERT_INTO_USER, nativeQuery = true)
  @Transactional
  Optional<User> insert(
      @Param("role") String role,
      @Param("username") String username,
      @Param("password") String password);

  @Modifying
  @Query(value = UPDATE_USER, nativeQuery = true)
  @Transactional
  Optional<User> update(
      @Param("uuid") String uuid,
      @Param("role") String role,
      @Param("username") String username,
      @Param("password") String password);
  String deleteByUuid(String uuid);
  Optional<User> findByUuid(String uuid);
  Optional<User> findByUsername(String username);
  List<User> findByRole(String role);
}