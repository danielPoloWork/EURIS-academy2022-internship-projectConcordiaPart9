package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.dataPersistences.DTOs.ConfigurationDto;
import com.euris.academy2022.concordia.dataPersistences.models.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfigurationJpaRepository extends JpaRepository<Configuration, String> {

    String INSERT_INTO_CONFIGURATION =
            "INSERT INTO CONFIGURATION (CONFIGURATION.label, CONFIGURATION.value, CONFIGURATION.dateCreation, CONFIGURATION.dateUpdate) "
                    + "VALUES (:label, :value, :dateCreation, :dateUpdate)";

    String UPDATE_CONFIGURATION =
            "UPDATE CONFIGURATION "
                    + "SET CONFIGURATION.value = :value, CONFIGURATION.dateUpdate = :dateUpdate "
                    + "WHERE CONFIGURATION.label = :label";

    String DELETE_CONFIGURATION =
            "DELETE FROM CONFIGURATION "
                    + "WHERE CONFIGURATION.label = :label";

    String SELECT_BY_LABEL =
            "SELECT CONFIGURATION.label CONFIGURATION.value "
                    + "FROM CONFIGURATION"
                    + "WHERE CONFIGURATION.label = :label";


    @Modifying
    @Query(value = INSERT_INTO_CONFIGURATION, nativeQuery = true)
    @Transactional
    Integer insert(
            @Param("label") String label,
            @Param("value") String value,
            @Param("dateCreation") LocalDateTime dateCreation,
            @Param("dateUpdate") LocalDateTime dateUpdate);


    @Modifying
    @Query(value = UPDATE_CONFIGURATION, nativeQuery = true)
    @Transactional
    Integer update(
            @Param("label") String label,
            @Param("value") String value,
            @Param("dateUpdate") LocalDateTime dateUpdate);


    @Modifying
    @Query(value = DELETE_CONFIGURATION, nativeQuery = true)
    @Transactional
    Integer removeByLabel(
            @Param("label") String label);


    @Query(value = SELECT_BY_LABEL, nativeQuery = true)
    Optional<Configuration> findByLabel(
            @Param("label") String label);

}
