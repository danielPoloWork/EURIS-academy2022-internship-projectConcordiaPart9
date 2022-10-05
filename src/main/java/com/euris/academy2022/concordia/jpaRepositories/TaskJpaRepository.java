package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskJpaRepository extends JpaRepository<Task, String> {

    String INSERT_INTO_TASK =
            "INSERT INTO Task (Task.id, Task.title, Task.description, Task.priority, Task.status, Task.deadLine) "
                    + "VALUES (:id, :title, :description, :priority, :status, :deadLine)";

    String UPDATE_TASK =
            "UPDATE Task "
                    + "SET Task.title = :title, Task.description = :description, Task.priority = :priority, Task.status = :status, Task.deadLine = :deadLine "
                    + "WHERE Task.id = :id";

    String DELETE_AUTHORIZATION =
            "DELETE FROM Task "
                    + "WHERE Task.id = :id";

    String SELECT_ALL_TASK_BY_MEMBER_UUID =
            "SELECT Task.id, Task.title, Task.description, Task.priority, Task.status, Task.deadLine "
                    + "FROM Member "
                    + "INNER JOIN Assignee ON Member.uuid = Assignee.uuidMember "
                    + "INNER JOIN Task ON Task.id = Assignee.idTask "
                    + "WHERE Member.uuid = :uuid";

    String SELECT_TASK_BY_PRIORITY =
            "SELECT Task.id, Task.title, Task.description, Task.priority, Task.status, Task.deadLine "
                    + "FROM Task "
                    + "WHERE Task.priority = :priority";

    String SELECT_TASK_BY_STATUS =
            "SELECT Task.id, Task.title, Task.description, Task.priority, Task.status, Task.deadLine "
                    + "FROM Task "
                    + "WHERE Task.status = :status";

    @Modifying
    @Query(value = INSERT_INTO_TASK, nativeQuery = true)
    @Transactional
    Integer insert(
            @Param("id") String id,
            @Param("title") String title,
            @Param("description") String description,
            @Param("priority") String priority,
            @Param("status") String status,
            @Param("deadLine") LocalDateTime deadLine);

    @Modifying
    @Query(value = UPDATE_TASK, nativeQuery = true)
    @Transactional
    Integer update(
            @Param("id") String id,
            @Param("title") String title,
            @Param("description") String description,
            @Param("priority") String priority,
            @Param("status") String status,
            @Param("deadLine") LocalDateTime deadLine);

    @Modifying
    @Query(value = DELETE_AUTHORIZATION, nativeQuery = true)
    @Transactional
    Integer removeById(
            @Param("id") String id);

    @Query(value = SELECT_ALL_TASK_BY_MEMBER_UUID, nativeQuery = true)
    List<Task> findAllTasksByMemberUuid(@Param("uuid") String uuid);

    @Query(value = SELECT_TASK_BY_PRIORITY, nativeQuery = true)
    List<Task> findByPriority(@Param("priority") String priority);

    @Query(value = SELECT_TASK_BY_STATUS, nativeQuery = true)
    List<Task> findByStatus(@Param("status") String status);

    List<Task> findByTitle(String title);
    List<Task> findByDeadLine(LocalDateTime deadLine);
}
