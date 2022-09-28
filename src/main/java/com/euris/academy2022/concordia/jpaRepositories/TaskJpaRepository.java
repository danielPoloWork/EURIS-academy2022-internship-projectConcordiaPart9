package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface TaskJpaRepository extends JpaRepository<Task, String> {

    String INSERT_INTO_TASK =
            "INSERT INTO Task (Task.id, Task.title, Task.description, Task.priority, Task.status, Task.deadLine) "
                    + "VALUES (:id, :title, :description, :priority, :status, :deadLine)";

    @Modifying
    @Query(value = INSERT_INTO_TASK, nativeQuery = true)
    @Transactional
    Integer create(
            @Param("id") String id,
            @Param("title") String title,
            @Param("description") String description,
            @Param("priority") String priority,
            @Param("status") String status,
            @Param("deadLine") LocalDateTime deadLine);
}
