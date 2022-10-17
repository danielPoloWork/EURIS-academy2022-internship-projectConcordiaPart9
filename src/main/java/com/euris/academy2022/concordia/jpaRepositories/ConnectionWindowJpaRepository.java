package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.dataPersistences.models.ConnectionWindow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConnectionWindowJpaRepository extends JpaRepository<ConnectionWindow, Integer> {

    String INSERT_INTO_CONNECTION_WINDOW =
            "INSERT INTO ConnectionWindow (ConnectionWindow.month, ConnectionWindow.cron, ConnectionWindow.dateCreation, ConnectionWindow.dateUpdate) "
                    + "VALUES (:month, :cron, :dateCreation, :dateUpdate)";

    String UPDATE_CONNECTION_WINDOW =
            "UPDATE ConnectionWindow "
                    + "SET ConnectionWindow.cron = :cron, ConnectionWindow.dateUpdate = :dateUpdate "
                    + "WHERE ConnectionWindow.month = :month";

    String SELECT_BY_MONTH =
            "SELECT ConnectionWindow.month, ConnectionWindow.cron, ConnectionWindow.dateCreation, ConnectionWindow.dateUpdate "
                    + "FROM ConnectionWindow "
                    + "WHERE ConnectionWindow.month = :month";


    @Modifying
    @Query(value = INSERT_INTO_CONNECTION_WINDOW, nativeQuery = true)
    @Transactional
    Integer insert(
            @Param("month") int month,
            @Param("cron") String cron,
            @Param("dateCreation") LocalDateTime dateCreation,
            @Param("dateUpdate") LocalDateTime dateUpdate);


    @Modifying
    @Query(value = UPDATE_CONNECTION_WINDOW, nativeQuery = true)
    @Transactional
    Integer update(
            @Param("month") int month,
            @Param("cron") String cron,
            @Param("dateUpdate") LocalDateTime dateUpdate);



    @Query(value = SELECT_BY_MONTH, nativeQuery = true)
    Optional<ConnectionWindow> findByMonth(
            @Param("month") int month);


}
