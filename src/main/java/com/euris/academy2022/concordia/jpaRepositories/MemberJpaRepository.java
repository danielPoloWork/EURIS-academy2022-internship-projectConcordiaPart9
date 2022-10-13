package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.dataPersistences.models.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, String> {
    String INSERT_INTO_MEMBER =
            "INSERT INTO Member (Member.uuid, Member.idTrelloMember, Member.username, Member.password, Member.role, Member.firstName, Member.lastName, Member.dateCreation, Member.dateUpdate) "
                    + "VALUES (UUID(), :idTrelloMember, :username, :password, :role, :firstName, :lastName, :dateCreation, :dateUpdate)";

    String UPDATE_MEMBER =
            "UPDATE Member "
                    + "SET Member.password = :password, Member.role = :role, Member.firstName = :firstName, Member.lastName = :lastName, Member.dateUpdate = :dateUpdate "
                    + "WHERE Member.uuid = :uuid";

    String DELETE_MEMBER =
            "DELETE FROM Member "
                    + "WHERE Member.uuid = :uuid";

    String SELECT_ALL_MEMBERS_BY_TASK_ID =
            "SELECT Member.uuid, Member.idTrelloMember, Member.username, Member.password, Member.role, Member.firstName, Member.lastName, Member.dateCreation, Member.dateUpdate "
                    + "FROM Task "
                    + "INNER JOIN Assignee ON Task.id = Assignee.idTask "
                    + "INNER JOIN Member ON Member.uuid = Assignee.uuidMember "
                    + "WHERE Task.id = :id";

    String SELECT_ALL_MEMBERS_BY_ROLE =
            "SELECT Member.uuid, Member.idTrelloMember, Member.username, Member.password, Member.role, Member.firstName, Member.lastName, Member.dateCreation, Member.dateUpdate "
                    + "FROM Member "
                    + "WHERE Member.role = :role";

    @Modifying
    @Query(value = INSERT_INTO_MEMBER, nativeQuery = true)
    @Transactional
    Integer insert(
            @Param("idTrelloMember") String idTrelloMember,
            @Param("username") String username,
            @Param("password") String password,
            @Param("role") String role,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("dateCreation") LocalDateTime dateCreation,
            @Param("dateUpdate") LocalDateTime dateUpdate);

    @Modifying
    @Query(value = UPDATE_MEMBER, nativeQuery = true)
    @Transactional
    Integer update(
            @Param("uuid") String uuid,
            @Param("password") String password,
            @Param("role") String role,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("dateUpdate") LocalDateTime dateUpdate);

    @Modifying
    @Query(value = DELETE_MEMBER, nativeQuery = true)
    @Transactional
    Integer removeByUuid(
            @Param("uuid") String uuid);

    @Query(value = SELECT_ALL_MEMBERS_BY_TASK_ID, nativeQuery = true)
    List<Member> findAllMembersByTaskId(@Param("id") String id);

    @Query(value = SELECT_ALL_MEMBERS_BY_ROLE, nativeQuery = true)
    List<Member> findByRole(@Param("role") String role);

    List<Member> findAll();

    Optional<Member> findByUuid(String uuid);

    Optional<Member> findByIdTrelloMember(String idTrelloMember);

    Optional<Member> findByUsername(String username);

    List<Member> findByFirstName(String firstName);

    List<Member> findByLastName(String lastName);
}