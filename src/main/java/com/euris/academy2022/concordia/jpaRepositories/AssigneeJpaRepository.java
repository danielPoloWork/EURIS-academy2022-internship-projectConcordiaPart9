package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.models.Assignee;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
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
public interface AssigneeJpaRepository extends JpaRepository<Assignee, String> {

    String INSERT_INTO_ASSIGNEE =
            "INSERT INTO Assignee (Assignee.uuid, Assignee.uuidMember, Assignee.idTask, Assignee.dateCreation) "
                    + "VALUES (UUID(), :uuidMember, :idTask, :dateCreation)";

    String DELETE_ASSIGNEE_BY_ID_TASK_AND_UUID_MEMBER =
            "DELETE FROM Assignee "
                    +"WHERE Assignee.uuidMember = :uuidMember AND Assignee.idTask = :idTask ";

    String SELECT_ASSIGNEE_BY_ID_TASK_AND_UUID_MEMBER =
            "SELECT Assignee.uuid, Assignee.uuidMember, Assignee.idTask, Assignee.dateCreation "
                    + "FROM Assignee "
                    + "WHERE Assignee.uuidMember LIKE :uuidMember "
                    + "AND Assignee.idTask LIKE :idTask";

    String SELECT_ALL_TASK_NOT_ASSIGNED_TO_THIS_UUID_MEMBER =
            "SELECT Task.id, Task.title, Task.description, Task.priority, Task.status, Task.deadLine, Task.dateCreation, Task.dateUpdate " +
            "FROM concordia.Task " +
            "LEFT JOIN concordia.Assignee ON Task.id = Assignee.idTask " +
            "WHERE Assignee.uuidMember NOT LIKE :uuidMember " +
            "UNION " +
                "SELECT Task.id, Task.title, Task.description, Task.priority, Task.status, Task.deadLine, Task.dateCreation, Task.dateUpdate " +
                "FROM concordia.Task " +
                "LEFT JOIN concordia.Assignee ON Task.id = Assignee.idTask " +
                "WHERE Assignee.idTask IS NULL";

    @Modifying
    @Query(value = INSERT_INTO_ASSIGNEE, nativeQuery = true)
    @Transactional
    Integer insert(
            @Param("uuidMember") String uuidMember,
            @Param("idTask") String idTask,
            @Param("dateCreation")LocalDateTime dateCreation);

    @Modifying
    @Query(value = DELETE_ASSIGNEE_BY_ID_TASK_AND_UUID_MEMBER, nativeQuery = true)
    @Transactional
    Integer removeByUuidMemberAndIdTask(
            @Param("uuidMember") String uuidMember,
            @Param("idTask") String idTask);

    List<Assignee> findAll();
    @Query(value = SELECT_ASSIGNEE_BY_ID_TASK_AND_UUID_MEMBER, nativeQuery = true)
    Optional<Assignee> findByUuidMemberAndIdTask(
            @Param("uuidMember") String uuidMember,
            @Param("idTask") String idTask);

    @Query(value = SELECT_ALL_TASK_NOT_ASSIGNED_TO_THIS_UUID_MEMBER, nativeQuery = true)
    List<Task> findAllTaskNotAssignedToThisUuidMember(
            @Param("uuidMember") String uuidMember);
}