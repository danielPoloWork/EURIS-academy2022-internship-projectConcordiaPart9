package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.ConcordiaApplication;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(classes = ConcordiaApplication.class)
class TaskJpaRepositoryTest {

    @Autowired
    private TaskJpaRepository taskJpaRepository;

    private Task task;

    @BeforeEach
    void init() {

        task = Task.builder()
                .id("idTask")
                .title("titleTask")
                .description("descriptionTask")
                .priority(TaskPriority.HIGH)
                .status(TaskStatus.TO_DO)
                .deadLine(LocalDateTime.now())
                .dateCreation(LocalDateTime.now())
                .dateUpdate(LocalDateTime.now())
                .build();
    }

    @Test
    void insertTest() {

        taskJpaRepository.insert(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority().getLabel(),
                task.getStatus().getLabel(),
                task.getDeadLine(),
                task.getDateCreation(),
                task.getDateUpdate()
                );

        Optional<Task> optionalTask = taskJpaRepository.findById(task.getId());

        Assertions.assertEquals(task.getId(), optionalTask.get().getId());
        Assertions.assertEquals(task.getTitle(), optionalTask.get().getTitle());
        Assertions.assertEquals(task.getDescription(), optionalTask.get().getDescription());
        Assertions.assertEquals(task.getPriority(), optionalTask.get().getPriority());
        Assertions.assertEquals(task.getStatus(), optionalTask.get().getStatus());
        Assertions.assertEquals(task.getDeadLine().truncatedTo(ChronoUnit.SECONDS), optionalTask.get().getDeadLine());
        Assertions.assertEquals(task.getDateCreation().truncatedTo(ChronoUnit.SECONDS), optionalTask.get().getDateCreation());
        Assertions.assertEquals(task.getDateUpdate().truncatedTo(ChronoUnit.SECONDS), optionalTask.get().getDateUpdate());

    }
    @Test
    void updateTest() {

        LocalDateTime updateDate = task.getDateUpdate().plusDays(5);

        taskJpaRepository.insert(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority().getLabel(),
                task.getStatus().getLabel(),
                task.getDeadLine(),
                task.getDateCreation(),
                task.getDateUpdate()
        );

        taskJpaRepository.update(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority().getLabel(),
                task.getStatus().getLabel(),
                task.getDeadLine(),
                updateDate
        );

        Optional<Task> optionalTask = taskJpaRepository.findById(task.getId());

        Assertions.assertEquals(task.getId(), optionalTask.get().getId());
        Assertions.assertEquals(updateDate.truncatedTo(ChronoUnit.SECONDS), optionalTask.get().getDateUpdate());

    }

}