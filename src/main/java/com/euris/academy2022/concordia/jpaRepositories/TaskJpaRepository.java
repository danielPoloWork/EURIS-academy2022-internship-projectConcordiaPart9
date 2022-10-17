package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface TaskJpaRepository extends JpaRepository<Task, String> {

    String INSERT_INTO_TASK =
            "INSERT INTO Task (Task.id, Task.title, Task.description, Task.priority, Task.status, Task.deadLine, Task.dateCreation, Task.dateUpdate) "
                    + "VALUES (:id, :title, :description, :priority, :status, :deadLine, :dateCreation, :dateUpdate)";

    String UPDATE_TASK =
            "UPDATE Task "
                    + "SET Task.title = :title, Task.description = :description, Task.priority = :priority, Task.status = :status, Task.deadLine = :deadLine, Task.dateUpdate = :dateUpdate "
                    + "WHERE Task.id = :id";

    String DELETE_AUTHORIZATION =
            "DELETE FROM Task "
                    + "WHERE Task.id = :id";

    String SELECT_BY_ID_TRELLO_TASK =
            "SELECT Task.id, Task.title, Task.description, Task.priority, Task.status, Task.deadLine, Task.dateCreation, Task.dateUpdate " +
            "FROM Task " +
            "WHERE Task.id = :id";

    String SELECT_ALL_TASK_BY_MEMBER_UUID =
            "SELECT Task.id, Task.title, Task.description, Task.priority, Task.status, Task.deadLine, Task.dateCreation, Task.dateUpdate "
                    + "FROM Member "
                    + "INNER JOIN Assignee ON Member.uuid = Assignee.uuidMember "
                    + "INNER JOIN Task ON Task.id = Assignee.idTask "
                    + "WHERE Member.uuid = :uuid";

    String SELECT_TASK_BY_PRIORITY =
            "SELECT Task.id, Task.title, Task.description, Task.priority, Task.status, Task.deadLine, Task.dateCreation, Task.dateUpdate "
                    + "FROM Task "
                    + "WHERE Task.priority = :priority";

    String SELECT_TASK_BY_STATUS =
            "SELECT Task.id, Task.title, Task.description, Task.priority, Task.status, Task.deadLine, Task.dateCreation, Task.dateUpdate "
                    + "FROM Task "
                    + "WHERE Task.status = :status";

    String SELECT_TASK_ID_BY_COMMENT_UUID =
            "SELECT Task.id " +
            "FROM Task " +
            "INNER JOIN Comment ON Task.id = Comment.idTask " +
            "WHERE Comment.uuid = :uuid";

    @Modifying
    @Query(value = INSERT_INTO_TASK, nativeQuery = true)
    @Transactional
    Integer insert(
            @Param("id") String id,
            @Param("title") String title,
            @Param("description") String description,
            @Param("priority") String priority,
            @Param("status") String status,
            @Param("deadLine") LocalDateTime deadLine,
            @Param("dateCreation") LocalDateTime dateCreation,
            @Param("dateUpdate") LocalDateTime dateUpdate);

    @Modifying
    @Query(value = UPDATE_TASK, nativeQuery = true)
    @Transactional
    Integer update(
            @Param("id") String id,
            @Param("title") String title,
            @Param("description") String description,
            @Param("priority") String priority,
            @Param("status") String status,
            @Param("deadLine") LocalDateTime deadLine,
            @Param("dateUpdate") LocalDateTime dateUpdate);

    @Modifying
    @Query(value = DELETE_AUTHORIZATION, nativeQuery = true)
    @Transactional
    Integer removeById(
            @Param("id") String id);
    @Query(value = SELECT_BY_ID_TRELLO_TASK, nativeQuery = true)
    Optional<Task> findByIdTrelloTask(@Param("id") String id);

    @Query(value = SELECT_ALL_TASK_BY_MEMBER_UUID, nativeQuery = true)
    List<Task> findAllTasksByMemberUuid(@Param("uuid") String uuid);

    @Query(value = SELECT_TASK_BY_PRIORITY, nativeQuery = true)
    List<Task> findByPriority(@Param("priority") String priority);

    @Query(value = SELECT_TASK_BY_STATUS, nativeQuery = true)
    List<Task> findByStatus(@Param("status") String status);

    @Query(value = SELECT_TASK_ID_BY_COMMENT_UUID, nativeQuery = true)
    Optional<String> findIdByUuidComment(@Param("uuid") String uuidComment);

    List<Task> findByTitle(String title);
    List<Task> findByDeadLine(LocalDateTime deadLine);
    Long countByStatus(TaskStatus status);


}
