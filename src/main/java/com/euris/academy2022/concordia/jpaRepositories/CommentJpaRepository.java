package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, String> {


    String INSERT_INTO_COMMENT =
            "INSERT INTO Comment (Comment.uuid, Comment.text, Comment.lastUpdate, Comment.idTask, Comment.idMember) "
                    + "VALUES (UUID(), :text, :now(), :idTask, :idMember)";


    @Modifying
    @Query(value = INSERT_INTO_COMMENT, nativeQuery = true)
    @Transactional
    Integer create(
            @Param("text") String text,
            @Param("idTask") String idTask,
            @Param("idMember") String idMember);
}
