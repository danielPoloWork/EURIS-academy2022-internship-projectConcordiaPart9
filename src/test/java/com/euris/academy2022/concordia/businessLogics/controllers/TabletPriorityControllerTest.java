package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.TabletService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TaskDto;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TabletPriorityController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
class TabletPriorityControllerTest {

    @Autowired
    private MockMvc client;

    @MockBean
    TabletService tabletService;

    private Task task;
    private List<TaskDto> taskList;

    private final String REQUEST_MAPPING = "/api/tablet/priority";

    @BeforeEach
    void setUp() {
        task = Task.builder()
                .id("id1")
                .priority(TaskPriority.HIGH)
                .deadLine(LocalDateTime.now())
                .build();
        taskList = new ArrayList<>();
        taskList.add(task.toDto());
    }

    @Test
    void getMemberTasks() throws Exception {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(taskList);

        Mockito
                .when(tabletService.getMemberTasks(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/uuid"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].id").value(taskList.get(0).getId()))
                .andExpect(jsonPath("$.body[0].priority").value(taskList.get(0).getPriority().getLabel()));
    }

    @Test
    void getMemberTasks_notFound() throws Exception {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(tabletService.getMemberTasks(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/uuid"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    void getHighPriorityTasks() throws Exception {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(taskList);

        Mockito
                .when(tabletService.getMemberTasksByPriority(Mockito.anyString(), Mockito.any(TaskPriority.class)))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/high/uuid"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].id").value(taskList.get(0).getId()))
                .andExpect(jsonPath("$.body[0].priority").value(TaskPriority.HIGH.getLabel()));
    }

    @Test
    void getHighPriorityTasks_notFound() throws Exception {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(tabletService.getMemberTasksByPriority(Mockito.anyString(), Mockito.any(TaskPriority.class)))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/high/uuid"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    void getMediumPriorityTasks() throws Exception {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        taskList.get(0).setPriority(TaskPriority.MEDIUM);

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(taskList);

        Mockito
                .when(tabletService.getMemberTasksByPriority(Mockito.anyString(), Mockito.any(TaskPriority.class)))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/medium/uuid"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].id").value(taskList.get(0).getId()))
                .andExpect(jsonPath("$.body[0].priority").value(TaskPriority.MEDIUM.getLabel()));
    }

    @Test
    void getMediumPriorityTasks_notFound() throws Exception {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(tabletService.getMemberTasksByPriority(Mockito.anyString(), Mockito.any(TaskPriority.class)))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/medium/uuid"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    void getLowPriorityTasks() throws Exception {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        taskList.get(0).setPriority(TaskPriority.LOW);

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(taskList);

        Mockito
                .when(tabletService.getMemberTasksByPriority(Mockito.anyString(), Mockito.any(TaskPriority.class)))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/low/uuid"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].id").value(taskList.get(0).getId()))
                .andExpect(jsonPath("$.body[0].priority").value(TaskPriority.LOW.getLabel()));
    }

    @Test
    void getLowPriorityTasks_notFound() throws Exception {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(tabletService.getMemberTasksByPriority(Mockito.anyString(), Mockito.any(TaskPriority.class)))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/low/uuid"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    void getExpiringTasks() throws Exception {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(taskList);

        Mockito
                .when(tabletService.getExpiringTasks(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/expiring/uuid"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].id").value(taskList.get(0).getId()));
    }

    @Test
    void getExpiringTask_notFound() throws Exception {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(tabletService.getExpiringTasks(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/expiring/uuid"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }
}