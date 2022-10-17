package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.dataPersistences.models.Comment;
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
public interface CommentJpaRepository extends JpaRepository<Comment, String> {

    String INSERT_INTO_COMMENT =
            "INSERT INTO Comment (Comment.uuid, Comment.idTask, Comment.uuidMember, Comment.text, Comment.dateCreation, Comment.dateUpdate) " +
            "VALUES (UUID(), :idTask, :uuidMember, :text, :dateCreation, :dateUpdate)";

    String INSERT_INTO_COMMENT_FROM_TRELLO =
            "INSERT INTO Comment (Comment.uuid, Comment.idTrelloComment, Comment.idTask, Comment.uuidMember, Comment.text, Comment.dateCreation, Comment.dateUpdate) " +
                    "VALUES (UUID(), :idTrelloComment, :idTask, :uuidMember, :text, :dateCreation, :dateUpdate)";

    String UPDATE_COMMENT_BY_UUID =
        "UPDATE Comment " +
        "SET Comment.text = :text, Comment.dateUpdate = :dateUpdate " +
        "WHERE Comment.uuid = :uuid";

    String UPDATE_COMMENT_BY_ID_TRELLO_COMMENT =
            "UPDATE Comment " +
            "SET Comment.idTrelloComment = :idTrelloComment, Comment.text = :text, Comment.dateUpdate = :dateUpdate " +
            "WHERE Comment.uuid = :uuid";

    String DELETE_COMMENT_BY_UUID =
            "DELETE FROM Comment " +
            "WHERE Comment.uuid = :uuid";

    String DELETE_COMMENT_BY_ID_TRELLO_COMMENT =
            "DELETE FROM Comment " +
            "WHERE Comment.idTrelloComment = :idTrelloComment";

    String SELECT_COMMENT_BY_ID_TRELLO_COMMENT =
            "SELECT Comment.uuid, Comment.idTrelloComment, Comment.idTask, Comment.uuidMember, Comment.text, Comment.dateCreation, Comment.dateUpdate " +
            "FROM Comment " +
            "WHERE Comment.idTrelloComment = :idTrelloComment";

    String SELECT_ALL_WHERE_ID_TRELLO_COMMENT_IS_NOT_NULL =
            "SELECT Comment.uuid, Comment.idTrelloComment, Comment.idTask, Comment.uuidMember, Comment.text, Comment.dateCreation, Comment.dateUpdate " +
                    "FROM Comment " +
                    "WHERE Comment.idTrelloComment IS NOT NULL";

    @Modifying
    @Query(value = INSERT_INTO_COMMENT, nativeQuery = true)
    @Transactional
    Integer insert(
            @Param("idTask") String idTask,
            @Param("uuidMember") String uuidMember,
            @Param("text") String text,
            @Param("dateCreation") LocalDateTime dateCreation,
            @Param("dateUpdate") LocalDateTime dateUpdate);

    @Modifying
    @Query(value = INSERT_INTO_COMMENT_FROM_TRELLO, nativeQuery = true)
    @Transactional
    Integer insertFromTrello(
            @Param("idTrelloComment") String idTrelloComment,
            @Param("idTask") String idTask,
            @Param("uuidMember") String uuidMember,
            @Param("text") String text,
            @Param("dateCreation") LocalDateTime dateCreation,
            @Param("dateUpdate") LocalDateTime dateUpdate);

    @Modifying
    @Query(value = UPDATE_COMMENT_BY_UUID, nativeQuery = true)
    @Transactional
    Integer update(
            @Param("uuid") String uuid,
            @Param("text") String text,
            @Param("dateUpdate") LocalDateTime dateUpdate);

    @Modifying
    @Query(value = UPDATE_COMMENT_BY_ID_TRELLO_COMMENT, nativeQuery = true)
    @Transactional
    Integer updateFromTrello(
            @Param("uuid") String uuid,
            @Param("idTrelloComment") String idTrelloComment,
            @Param("text") String text,
            @Param("dateUpdate") LocalDateTime dateUpdate);

    @Modifying
    @Query(value = DELETE_COMMENT_BY_UUID, nativeQuery = true)
    @Transactional
    Integer removeByUuid(
            @Param("uuid") String uuid);

    @Modifying
    @Query(value = DELETE_COMMENT_BY_ID_TRELLO_COMMENT, nativeQuery = true)
    @Transactional
    Integer removeByIdTrelloComment(
            @Param("idTrelloComment") String idTrelloComment);

    @Query(value = SELECT_COMMENT_BY_ID_TRELLO_COMMENT, nativeQuery = true)
    Optional<Comment> findByIdTrelloComment(
            @Param("idTrelloComment") String idTrelloComment);

    @Query(value = SELECT_ALL_WHERE_ID_TRELLO_COMMENT_IS_NOT_NULL, nativeQuery = true)
    List<Comment> findAllWhereIdTrelloCommentIsNotNull();

    Optional<Comment> findByUuid(String uuid);
}
