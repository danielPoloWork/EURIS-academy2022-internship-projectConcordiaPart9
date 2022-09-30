package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.responses.ResponseDto;
import com.euris.academy2022.concordia.utils.enums.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
class TaskControllerTest {

    @Autowired
    private MockMvc client;

    @MockBean
    private TaskService taskService;

    private ObjectMapper objectMapper;

    private Task task;

    private List<TaskDto> taskList;

    private final String REQUEST_MAPPING = "/api/task/";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        task = Task.builder()
                .id("id1")
                .title("t1")
                .description("d1")
                .priority(TaskPriority.HIGH)
                .status(TaskStatus.TO_DO)
                .build();
        taskList = new ArrayList<>();
        taskList.add(task.toDto());
    }

    @Test
    @DisplayName("GIVEN id, title, description, priority, status WHEN insert THEN response should be CREATED")
    void insertTest_ShouldBeCreated() throws Exception {
        ResponseDto<TaskDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.POST);
        response.setHttpResponse(HttpResponseType.CREATED);
        response.setCode(HttpResponseType.CREATED.getCode());
        response.setDesc(HttpResponseType.CREATED.getDesc());
        response.setBody(task.toDto());

        Mockito
                .when(taskService.insert(Mockito.any(Task.class)))
                .thenReturn(response);

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.POST.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.CREATED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.CREATED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.CREATED.getDesc()))
                .andExpect(jsonPath("$.body.id").value(task.getId()))
                .andExpect(jsonPath("$.body.title").value(task.getTitle()))
                .andExpect(jsonPath("$.body.description").value(task.getDescription()))
                .andExpect(jsonPath("$.body.priority").value(task.getPriority().getLabel()))
                .andExpect(jsonPath("$.body.status").value(task.getStatus().getLabel()));
    }


    @Test
    @DisplayName("IF args are missing WHEN insert THEN response should be NOT_CREATED")
    void insertTest_ShouldBeNotCreated() throws Exception {
        ResponseDto<TaskDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.POST);
        response.setHttpResponse(HttpResponseType.NOT_CREATED);
        response.setCode(HttpResponseType.NOT_CREATED.getCode());
        response.setDesc(HttpResponseType.NOT_CREATED.getDesc());

        Mockito
                .when(taskService.insert(Mockito.any(Task.class)))
                .thenReturn(response);

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.POST.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_CREATED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_CREATED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_CREATED.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN id, title, description, priority, status WHEN update THEN response should be UPDATED")
    void updateTest_ShouldBeUpdated() throws Exception {

        ResponseDto<TaskDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.PUT);
        response.setHttpResponse(HttpResponseType.UPDATED);
        response.setCode(HttpResponseType.UPDATED.getCode());
        response.setDesc(HttpResponseType.UPDATED.getDesc());
        response.setBody(task.toDto());

        Mockito
                .when(taskService.update(Mockito.any(Task.class)))
                .thenReturn(response);

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.PUT.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.UPDATED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.UPDATED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.UPDATED.getDesc()))
                .andExpect(jsonPath("$.body.id").value(task.getId()))
                .andExpect(jsonPath("$.body.title").value(task.getTitle()))
                .andExpect(jsonPath("$.body.description").value(task.getDescription()))
                .andExpect(jsonPath("$.body.priority").value(task.getPriority().getLabel()))
                .andExpect(jsonPath("$.body.status").value(task.getStatus().getLabel()));
    }

    @Test
    @DisplayName("IF something goes wrong WHEN update THEN response should be NOT_UPDATED")
    void updateTest_ShouldBeNotUpdated() throws Exception {

        ResponseDto<TaskDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.PUT);
        response.setHttpResponse(HttpResponseType.NOT_UPDATED);
        response.setCode(HttpResponseType.NOT_UPDATED.getCode());
        response.setDesc(HttpResponseType.NOT_UPDATED.getDesc());

        Mockito
                .when(taskService.update(Mockito.any(Task.class)))
                .thenReturn(response);

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.PUT.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_UPDATED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_UPDATED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_UPDATED.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN a right id WHEN deleteById AND id is present THEN response should be DELETED")
    void deleteByUuidTest_ShouldBeDeleted() throws Exception {

        ResponseDto<TaskDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.DELETE);
        response.setHttpResponse(HttpResponseType.DELETED);
        response.setCode(HttpResponseType.DELETED.getCode());
        response.setDesc(HttpResponseType.DELETED.getDesc());
        response.setBody(task.toDto());

        Mockito
                .when(taskService.deleteById(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(delete(REQUEST_MAPPING + "/" + task.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.DELETE.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.DELETED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.DELETED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.DELETED.getDesc()))
                .andExpect(jsonPath("$.body.id").value(task.getId()))
                .andExpect(jsonPath("$.body.title").value(task.getTitle()))
                .andExpect(jsonPath("$.body.description").value(task.getDescription()))
                .andExpect(jsonPath("$.body.priority").value(task.getPriority().getLabel()))
                .andExpect(jsonPath("$.body.status").value(task.getStatus().getLabel()));
    }

    @Test
    @DisplayName("GIVEN a id WHEN deleteById BUT couldn't delete THEN response should be NOT_DELETED")
    void deleteByUuidTest_ShouldBeNotDeleted() throws Exception {

        ResponseDto<TaskDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.DELETE);
        response.setHttpResponse(HttpResponseType.NOT_DELETED);
        response.setCode(HttpResponseType.NOT_DELETED.getCode());
        response.setDesc(HttpResponseType.NOT_DELETED.getDesc());

        Mockito
                .when(taskService.deleteById(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(delete(REQUEST_MAPPING + "/" + task.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.DELETE.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_DELETED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_DELETED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_DELETED.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN a id WHEN deleteById BUT couldn't find it THEN response should be NOT_FOUND")
    void deleteByUuidTest_ShouldBeNotFound() throws Exception {

        ResponseDto<TaskDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.DELETE);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(taskService.deleteById(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(delete(REQUEST_MAPPING + "/" + task.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.DELETE.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN getAll() WHEN record are present THEN response should be FOUND")
    void getAllTest_ShouldBeFound() throws Exception {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(taskList);

        Mockito
                .when(taskService.getAll())
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].id").value(taskList.get(0).getId()))
                .andExpect(jsonPath("$.body[0].title").value(taskList.get(0).getTitle()))
                .andExpect(jsonPath("$.body[0].description").value(taskList.get(0).getDescription()))
                .andExpect(jsonPath("$.body[0].priority").value(taskList.get(0).getPriority().getLabel()))
                .andExpect(jsonPath("$.body[0].status").value(taskList.get(0).getStatus().getLabel()));
    }

    @Test
    @DisplayName("GIVEN getAll() WHEN table is empty THEN response should be FOUND")
    void getAllTest_ShouldBeNotFound() throws Exception {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(taskService.getAll())
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING))
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
    @DisplayName("GIVEN a right id WHEN getById() THEN response should be FOUND")
    void getByUuidTest_ShouldBeFound() throws Exception {

        ResponseDto<TaskDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(task.toDto());

        Mockito
                .when(taskService.getById(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/" + task.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body.id").value(task.getId()))
                .andExpect(jsonPath("$.body.title").value(task.getTitle()))
                .andExpect(jsonPath("$.body.description").value(task.getDescription()))
                .andExpect(jsonPath("$.body.priority").value(task.getPriority().getLabel()))
                .andExpect(jsonPath("$.body.status").value(task.getStatus().getLabel()));
    }

    @Test
    @DisplayName("GIVEN a wrong id WHEN getById() THEN response should be NOT_FOUND")
    void getByUuidTest_ShouldBeNotFound() throws Exception {
        ResponseDto<TaskDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(taskService.getById(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/anyString"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN a right title WHEN getByTitle() THEN response should be FOUND")
    void getByTitleTest_ShouldBeFound() throws Exception {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(taskList);

        Mockito
                .when(taskService.getByTitle(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/title=" + task.getTitle()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].id").value(taskList.get(0).getId()))
                .andExpect(jsonPath("$.body[0].title").value(taskList.get(0).getTitle()))
                .andExpect(jsonPath("$.body[0].description").value(taskList.get(0).getDescription()))
                .andExpect(jsonPath("$.body[0].priority").value(taskList.get(0).getPriority().getLabel()))
                .andExpect(jsonPath("$.body[0].status").value(taskList.get(0).getStatus().getLabel()));
    }

    @Test
    @DisplayName("GIVEN a wrong title WHEN getByTitle() THEN response should be NOT_FOUND")
    void getByRoleTest_ShouldBeNotFound() throws Exception {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(taskService.getByTitle(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/title=wrongTitle"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN a right priority WHEN getByPriority() THEN response should be FOUND")
    void getByPriorityTest_ShouldBeFound() throws Exception {

        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(taskList);

        Mockito
                .when(taskService.getByPriority(Mockito.any(TaskPriority.class)))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/priority=" + task.getPriority().getLabel()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].id").value(taskList.get(0).getId()))
                .andExpect(jsonPath("$.body[0].title").value(taskList.get(0).getTitle()))
                .andExpect(jsonPath("$.body[0].description").value(taskList.get(0).getDescription()))
                .andExpect(jsonPath("$.body[0].priority").value(taskList.get(0).getPriority().getLabel()))
                .andExpect(jsonPath("$.body[0].status").value(taskList.get(0).getStatus().getLabel()));
    }

    @Test
    @DisplayName("GIVEN a wrong priority WHEN getByPriority() THEN response should be NOT_FOUND")
    void getByPriorityTest_ShouldBeNotFound() throws Exception {
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(taskService.getByPriority(Mockito.any(TaskPriority.class)))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/priority=" + task.getPriority().getLabel()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN a right status WHEN getByStatus() THEN response should be FOUND")
    void getByStatusTest_ShouldBeFound() throws Exception{

        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(taskList);

        Mockito
                .when(taskService.getByStatus(Mockito.any(TaskStatus.class)))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/status=" + task.getStatus().getLabel()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].id").value(taskList.get(0).getId()))
                .andExpect(jsonPath("$.body[0].title").value(taskList.get(0).getTitle()))
                .andExpect(jsonPath("$.body[0].description").value(taskList.get(0).getDescription()))
                .andExpect(jsonPath("$.body[0].priority").value(taskList.get(0).getPriority().getLabel()))
                .andExpect(jsonPath("$.body[0].status").value(taskList.get(0).getStatus().getLabel()));
    }

    @Test
    @DisplayName("GIVEN a wrong status WHEN getByStatus() THEN response should be NOT_FOUND")
    void getByStatusTest_ShouldBeNotFound() throws Exception{
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(taskService.getByStatus(Mockito.any(TaskStatus.class)))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/status=" + task.getStatus().getLabel()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN a right deadLine WHEN getByDeadLine() THEN response should be FOUND")
    void getByDeadLine_ShouldBeFound() throws Exception{

        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(taskList);

        Mockito
                .when(taskService.getByDeadLine(Mockito.any(LocalDateTime.class)))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/deadLine=" + LocalDateTime.now()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].id").value(taskList.get(0).getId()))
                .andExpect(jsonPath("$.body[0].title").value(taskList.get(0).getTitle()))
                .andExpect(jsonPath("$.body[0].description").value(taskList.get(0).getDescription()))
                .andExpect(jsonPath("$.body[0].priority").value(taskList.get(0).getPriority().getLabel()))
                .andExpect(jsonPath("$.body[0].status").value(taskList.get(0).getStatus().getLabel()));
    }

    @Test
    @DisplayName("GIVEN a wrong deadLine WHEN getByDeadLine() THEN response should be NOT_FOUND")
    void getByDeadLine_ShouldBeNotFound() throws Exception{
        ResponseDto<List<TaskDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(taskService.getByDeadLine(Mockito.any(LocalDateTime.class)))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/deadLine=" + LocalDateTime.now()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }



}