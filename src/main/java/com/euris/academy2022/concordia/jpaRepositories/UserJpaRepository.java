package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.dataPersistences.dataModels.User;
import java.util.Collection;
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

  String DELETE_USER =
      "DELETE FROM User "
          + "WHERE User.uuid = :uuid";

  String SELECT_BY_UUID =
      "SELECT User.uuid, User.role, User.username, User.password "
          + "FROM User "
          + "WHERE User.uuid LIKE :uuid";

  String SELECT_BY_ROLE =
      "SELECT User.uuid, User.role, User.username, User.password "
          + "FROM User "
          + "WHERE User.role LIKE :role";

  String SELECT_BY_USERNAME =
      "SELECT User.uuid, User.role, User.username, User.password "
          + "FROM User "
          + "WHERE User.username LIKE :username";

  @Modifying
  @Query(value = INSERT_INTO_USER, nativeQuery = true)
  @Transactional
  Integer create(
      @Param("role") String role,
      @Param("username") String username,
      @Param("password") String password);

  @Modifying
  @Query(value = UPDATE_USER, nativeQuery = true)
  @Transactional
  Integer update(
      @Param("uuid") String uuid,
      @Param("role") String role,
      @Param("username") String username,
      @Param("password") String password);

  @Modifying
  @Query(value = DELETE_USER, nativeQuery = true)
  @Transactional
  Integer deleteByUuid(
      @Param("uuid") String uuid);

  @Query(value = SELECT_BY_UUID, nativeQuery = true)
  Optional<User> retrieveByUuid(
      @Param("uuid") String uuid);

  @Query(value = SELECT_BY_ROLE, nativeQuery = true)
  Collection<User> retrieveByRole(
      @Param("role") String role);

  @Query(value = SELECT_BY_USERNAME, nativeQuery = true)
  Optional<User> retrieveByUsername(
      @Param("username") String username);
}

/* Method params must be the same dataType as DB columns dataType */