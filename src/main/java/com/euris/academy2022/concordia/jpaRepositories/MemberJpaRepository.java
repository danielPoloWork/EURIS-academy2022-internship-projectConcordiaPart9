package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface MemberJpaRepository extends JpaRepository<Member, String> {


    String INSERT_INTO_MEMBER =
            "INSERT INTO Member (Member.id, Member.name, Member.surname, Member.role) "
                    + "VALUES (:id, :name, :surname, :role)";

    String UPDATE_MEMBER =
            "UPDATE Member "
                    + "SET Member.name = :name, Member.surname = :surname, Member.role = :role "
                    + "WHERE Member.id = :id";

    String DELETE_MEMBER =
            "DELETE FROM Member "
                    + "WHERE Member.id = :id";

    @Modifying
    @Query(value = INSERT_INTO_MEMBER, nativeQuery = true)
    @Transactional
    Integer insert(
            @Param("id") String id,
            @Param("name") String name,
            @Param("surname") String surname,
            @Param("role") String role
    );

    @Modifying
    @Query(value = UPDATE_MEMBER, nativeQuery = true)
    @Transactional
    Integer update(
            @Param("id") String id,
            @Param("name") String name,
            @Param("surname") String surname,
            @Param("role") String role
    );

    @Modifying
    @Query(value = DELETE_MEMBER, nativeQuery = true)
    @Transactional
    Integer removeById(
            @Param("id") String id
    );

    List<Member> findByName(String name);
    List<Member> findBySurname(String surname);
    List<Member> findByRole(String role);
}
