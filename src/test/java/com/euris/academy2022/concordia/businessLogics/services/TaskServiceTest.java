package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.businessLogics.impls.TaskServiceImpl;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application.test.properties")
class TaskServiceTest {

    private TaskService taskService;

    @Mock
    private TaskJpaRepository taskJpaRepository;

    @BeforeEach
    void init() {
        taskService = new TaskServiceImpl(taskJpaRepository);
    }

    @Test
    void getById_testIsPresent() {

        String taskId = "TaskId";

        Task task = Task.builder().id(taskId).build();

        Mockito
                .when(taskJpaRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(task));

        Optional<Task> response = taskService.getById(taskId);

        response.ifPresent(value -> Assertions.assertEquals(taskId, value.getId()));

    }

    @Test
    void getById_testIsNotPresent() {

        Mockito
                .when(taskJpaRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Assertions.assertEquals(Optional.empty(), taskService.getById(Mockito.anyString()));
    }

    @Test
    void getAll() {

        List<Task> tasks = new ArrayList<>();
        Task task1 = Task.builder().id("taskId1").build();
        Task task2 = Task.builder().id("taskId2").build();
        tasks.add(task1);
        tasks.add(task2);

        Mockito.when(taskJpaRepository.findAll()).thenReturn(tasks);

        List<Task> responseList = taskService.getAll();

        Assertions.assertEquals("taskId1", responseList.get(0).getId());
        Assertions.assertEquals("taskId2", responseList.get(1).getId());
    }

    @Test
    void getByPriority() {
    }

    @Test
    void getByStatus() {
    }

    @Test
    void getByTitle() {
    }

    @Test
    void getByDeadLine() {
    }

    @Test
    void insert() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void deleteAll() {
    }
}