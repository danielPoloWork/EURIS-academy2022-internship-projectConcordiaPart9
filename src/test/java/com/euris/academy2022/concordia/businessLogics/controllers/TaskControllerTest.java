package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
public class TaskControllerTest {

    @Autowired
    private MockMvc client;
    @MockBean
    private TaskService taskService;
    private ObjectMapper objectMapper;
    private Task task;
    private final String REQUEST_MAPPING = "/api/task";
    private ResponseDto<TaskDto> modelResponse;
    private ResponseDto<List<TaskDto>> listResponse;

    @BeforeEach
    void init() {

        objectMapper = new ObjectMapper();
        task = Task.builder()
                .id("idTask")
                .title("titleTask")
                .description("descriptionTask")
                .priority(TaskPriority.HIGH)
                .status(TaskStatus.TO_DO)
                .build();
        modelResponse = new ResponseDto<>();
        listResponse = new ResponseDto<>();

    }

    @Test
    void insertTest() throws Exception {
        Mockito
                .when(taskService.insert(Mockito.any(Task.class)))
                .thenReturn(modelResponse);

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(taskService, Mockito.times(1)).insert(Mockito.any(Task.class));
    }

    @Test
    void updateTest() throws Exception {
        Mockito
                .when(taskService.update(Mockito.any(Task.class)))
                .thenReturn(modelResponse);

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(taskService, Mockito.times(1)).update(Mockito.any(Task.class));
    }

    @Test
    void deleteByIdTest() throws Exception {
        Mockito
                .when(taskService.deleteById(Mockito.anyString()))
                .thenReturn(modelResponse);

        client
                .perform(delete(REQUEST_MAPPING + "/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(taskService, Mockito.times(1)).deleteById(Mockito.anyString());
    }

    @Test
    void getAllTest() throws Exception {
        Mockito
                .when(taskService.getAll())
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(taskService, Mockito.times(1)).getAll();
    }

    @Test
    void getByIdTest() throws Exception {
        Mockito
                .when(taskService.getByIdTrelloTask(Mockito.anyString()))
                .thenReturn(modelResponse);

        client
                .perform(get(REQUEST_MAPPING + "/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(taskService, Mockito.times(1)).getByIdTrelloTask(Mockito.anyString());
    }

    @Test
    void getByTitle() throws Exception {
        Mockito
                .when(taskService.getByTitle(Mockito.anyString()))
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING + "/title=" + task.getTitle())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(taskService, Mockito.times(1)).getByTitle(Mockito.anyString());
    }

    @Test
    void getByPriority() throws Exception {
        Mockito
                .when(taskService.getByPriority(Mockito.any(TaskPriority.class)))
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING + "/priority=" + task.getPriority())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(taskService, Mockito.times(1)).getByPriority(Mockito.any(TaskPriority.class));
    }

    @Test
    void getByStatus() throws Exception {
        Mockito
                .when(taskService.getByStatus(Mockito.any(TaskStatus.class)))
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING + "/status=" + task.getStatus())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(taskService, Mockito.times(1)).getByStatus(Mockito.any(TaskStatus.class));
    }

    @Test
    void getByDeadLine() throws Exception {

        task.setDeadLine(LocalDateTime.now());

        Mockito
                .when(taskService.getByDeadLine(Mockito.any(LocalDateTime.class)))
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING + "/deadLine=" + task.getDeadLine())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(taskService, Mockito.times(1)).getByDeadLine(Mockito.any(LocalDateTime.class));
    }
}
