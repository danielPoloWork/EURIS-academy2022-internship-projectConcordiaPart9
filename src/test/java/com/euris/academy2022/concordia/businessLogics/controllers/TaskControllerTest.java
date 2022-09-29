package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
class TaskControllerTest {

    @Autowired
    private MockMvc client;

    @MockBean
    private TaskService taskService;

    private final String requestMappingUrl = "/api/task/";

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void getAll_test() throws Exception {

        String id = "taskId";
        String title = "title";
        String description = "description";
        TaskStatus status = TaskStatus.TO_DO;
        TaskPriority priority = TaskPriority.HIGH;

        Task task = Task.builder()
                .id(id)
                .title(title)
                .description(description)
                .priority(priority)
                .status(status)
                .build();

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        Mockito.when(taskService.getAll()).thenReturn(tasks);

        client.perform(get(requestMappingUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(task.getId()))
                .andExpect(jsonPath("$[0].title").value(task.getTitle()))
                .andExpect(jsonPath("$[0].description").value(task.getDescription()))
                .andExpect(jsonPath("$[0].priority").value(task.getPriority().getLabel()))
                .andExpect(jsonPath("$[0].status").value(task.getStatus().getLabel()));

    }

    @Test
    void getAll_emptyListTest() throws Exception {
        List<Task> tasks = new ArrayList<>();
        Mockito.when(taskService.getAll()).thenReturn(tasks);

        client.perform(get(requestMappingUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getById_test() throws Exception {
        String id = "taskId";
        String title = "title";
        String description = "description";
        TaskStatus status = TaskStatus.TO_DO;
        TaskPriority priority = TaskPriority.HIGH;

        Task task = Task.builder()
                .id(id)
                .title(title)
                .description(description)
                .priority(priority)
                .status(status)
                .deadLine(LocalDateTime.now())
                .build();

        Mockito.when(taskService.getById(Mockito.anyString())).thenReturn(Optional.of(task));

        client.perform(get(requestMappingUrl + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.priority").value(task.getPriority().getLabel()))
                .andExpect(jsonPath("$.status").value(task.getStatus().getLabel()));
    }

    @Test
    void getById_404test() throws Exception {
        Mockito.when(taskService.getById(Mockito.anyString())).thenReturn(Optional.empty());

        client.perform(get(requestMappingUrl + "randomId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").doesNotExist());
    }

}