package com.euris.academy2022.concordia.jpaRepositories;

import com.euris.academy2022.concordia.ConcordiaApplication;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(classes = ConcordiaApplication.class)
class TaskJpaRepositoryTest {

    @Autowired
    private TaskJpaRepository taskJpaRepository;

    @Test
    void insert() {
        Task task = Task.builder()
                .id("idTask").build();

        taskJpaRepository.save(task);

        Optional<Task> optionalTask = taskJpaRepository.findById("idTask");

        Assertions.assertEquals("idTask", optionalTask.get().getId());

    }
}