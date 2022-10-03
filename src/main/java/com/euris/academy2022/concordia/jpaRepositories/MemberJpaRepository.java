package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public interface MemberJpaRepository extends JpaRepository<Member, String> {
    String INSERT_INTO_MEMBER =
            "INSERT INTO Member (Member.uuid, Member.idTrelloMember, Member.username, Member.password, Member.role, Member.name, Member.surname) "
                    + "VALUES (UUID(), :idTrelloMember, :username, :password, :role, :name, :surname)";

    String UPDATE_MEMBER =
            "UPDATE Member "
                    + "SET Member.idTrelloMember = :idTrelloMember, Member.username = :username, Member.password = :password, Member.role = :role, Member.name = :name, Member.surname = :surname "
                    + "WHERE Member.uuid = :uuid";

    String DELETE_MEMBER =
            "DELETE FROM Member "
                    + "WHERE Member.uuid = :uuid";

    @Modifying
    @Query(value = INSERT_INTO_MEMBER, nativeQuery = true)
    @Transactional
    Integer insert(
            @Param("idTrelloMember") String idTrelloMember,
            @Param("username") String username,
            @Param("password") String password,
            @Param("role") String role,
            @Param("name") String name,
            @Param("surname") String surname);

    @Modifying
    @Query(value = UPDATE_MEMBER, nativeQuery = true)
    @Transactional
    Integer update(
            @Param("uuid") String uuid,
            @Param("idTrelloMember") String idTrelloMember,
            @Param("username") String username,
            @Param("password") String password,
            @Param("role") String role,
            @Param("name") String name,
            @Param("surname") String surname);

    @Modifying
    @Query(value = DELETE_MEMBER, nativeQuery = true)
    @Transactional
    Integer removeByUuid(
            @Param("uuid") String uuid);
    List<Member> findAll();
    Optional<Member> findByUuid(String uuid);
    Optional<Member> findByIdTrelloMember(String idTrelloMember);
    Optional<Member> findByUsername(String username);
    List<Member> findByRole(String role);
    List<Member> findByName(String name);
    List<Member> findBySurname(String surname);
}