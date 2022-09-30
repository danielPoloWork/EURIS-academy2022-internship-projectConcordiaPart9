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
                    + "SET Task.title = :title, Task.description = :description, Task.priority = :priority, Task.status = :status, Task.deadLine = :deadLine"
                    + "WHERE Task.id = :id";

    String DELETE_AUTHORIZATION =
            "DELETE FROM Task "
                    + "WHERE Task.id = :id";

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

    List<Task> findByPriority(String priority);
    List<Task> findByStatus(String status);
    List<Task> findByTitle(String title);
    List<Task> findByDeadLine(LocalDateTime deadLine);
}
