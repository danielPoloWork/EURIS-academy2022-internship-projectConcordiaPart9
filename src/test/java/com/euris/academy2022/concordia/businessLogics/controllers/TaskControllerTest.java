package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

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
//@Import(value = {SecurityConfTest.class, SecurityConf.class})
@TestPropertySource(locations = "classpath:application.test.properties")
class TaskControllerTest {

    @Autowired
    private MockMvc client;

    @MockBean
    private TaskService taskService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void insert_test() {

    }

    @Test
    void update_test() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void getAll_test() throws Exception {

        String id = "taskId";
        String title = "title";
        String description = "description";
        TaskStatus status = TaskStatus.TO_DO;
        TaskPriority priority = TaskPriority.HIGH;

        List<Task> tasks = new ArrayList<>();
        Task task = Task.builder()
                .id(id)
                .title(title)
                .description(description)
                .priority(priority)
                .status(status)
                .build();

        tasks.add(task);

        Mockito.when(taskService.getAll()).thenReturn(tasks);

        client.perform(get("/api/task"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(task.getId()))
                .andExpect(jsonPath("$[0].title").value(task.getTitle()))
                .andExpect(jsonPath("$[0].description").value(task.getDescription()))
                .andExpect(jsonPath("$[0].priority").value(task.getPriority().getLabel()))
                .andExpect(jsonPath("$[0].status").value(task.getStatus().getLabel()));

    }

    @Test
    void getById() {
    }

    @Test
    void getByRole() {
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
}