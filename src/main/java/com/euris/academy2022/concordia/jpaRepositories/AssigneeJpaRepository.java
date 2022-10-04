package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Assignee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface AssigneeJpaRepository extends JpaRepository<Assignee, String> {

    String INSERT_INTO_ASSIGNEE =
            "INSERT INTO Assignee (Assignee.uuid, Assignee.uuidMember, Assignee.idTask) "
                    + "VALUES (UUID(), :uuidMember, :idTask)";

    String DELETE_ASSIGNEE_BY_ID_TASK_AND_UUID_MEMBER =
            "DELETE FROM Assignee "
                    +"WHERE Assignee.uuidMember = :uuidMember AND Assignee.idTask = :idTask ";

    @Modifying
    @Query(value = INSERT_INTO_ASSIGNEE, nativeQuery = true)
    @Transactional
    Integer insert(
            @Param("uuidMember") String uuidMember,
            @Param("idTask") String idTask);

    @Modifying
    @Query(value = DELETE_ASSIGNEE_BY_ID_TASK_AND_UUID_MEMBER, nativeQuery = true)
    @Transactional
    Integer removeByUuidMemberAndIdTask(
            @Param("uuidMember") String uuidMember, @Param("idTask") String idTask);

    List<Assignee> findAll();
//    List<Assignee> findByUuidMember(String uuidMember);
//    List<Assignee> findByIdTask(String idTask);
}