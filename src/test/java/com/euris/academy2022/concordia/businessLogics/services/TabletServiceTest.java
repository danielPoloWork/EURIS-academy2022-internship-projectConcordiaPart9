package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.ConcordiaApplication;
import com.euris.academy2022.concordia.businessLogics.services.impls.TabletServiceImpl;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application.test.properties")
class TabletServiceTest {

    @MockBean
    private TaskService taskService;
    private TabletService tabletService;

    private Task task;
    private Task taskHigh;
    private Task taskExp;
    private Task taskMedium;
    private Task taskLow;
    private Member member;

    private List<Task> taskList;

    private List<TaskDto> taskDtoList;

    @BeforeEach
    void init() {

        tabletService = new TabletServiceImpl(taskService);

        task = Task.builder()
                .id("idTask1")
                .priority(TaskPriority.HIGH)
                .status(TaskStatus.TO_DO)
                .build();

        taskHigh = Task.builder()
                .id("idTask2")
                .priority(TaskPriority.HIGH)
                .status(TaskStatus.TO_DO)
                .build();

        taskExp = Task.builder()
                .id("idTask3")
                .priority(TaskPriority.EXPIRING)
                .status(TaskStatus.TO_DO)
                .build();

        taskMedium = Task.builder()
                .id("idTask4")
                .priority(TaskPriority.MEDIUM)
                .status(TaskStatus.TO_DO)
                .build();

        taskLow = Task.builder()
                .id("idTask5")
                .priority(TaskPriority.LOW)
                .status(TaskStatus.TO_DO)
                .build();

        member = Member.builder()
                .idTrelloMember("idMember")
                .uuid("uuidMember")
                .build();

        taskList = new ArrayList<>();
        taskList.add(task);
        taskDtoList = taskList.stream().map(Task::toDto).toList();
    }

    @Test
    void getMemberTasksTest_NOT_FOUND() {

        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(taskService.findAllTasksByMemberUuid(Mockito.anyString()))
                .thenReturn(new ArrayList<>());

        ResponseDto<List<TaskDto>> response = tabletService.getMemberTasks(member.getUuid());

        Mockito.verify(taskService, Mockito.times(1)).findAllTasksByMemberUuid(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void getMemberTasksTest_FOUND() {

        taskList.clear();

        taskList.add(taskLow);
        taskList.add(taskHigh);
        taskList.add(taskExp);
        taskList.add(taskMedium);
        taskList.add(task);

        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());

        Mockito
                .when(taskService.findAllTasksByMemberUuid(Mockito.anyString()))
                .thenReturn(taskList);

        ResponseDto<List<TaskDto>> response = tabletService.getMemberTasks(member.getUuid());

        Mockito.verify(taskService, Mockito.times(1)).findAllTasksByMemberUuid(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());

        Assertions.assertEquals(task.getId(), response.getBody().get(0).getId());
        Assertions.assertEquals(taskHigh.getId(), response.getBody().get(1).getId());
        Assertions.assertEquals(taskExp.getId(), response.getBody().get(2).getId());
        Assertions.assertEquals(taskMedium.getId(), response.getBody().get(3).getId());
        Assertions.assertEquals(taskLow.getId(), response.getBody().get(4).getId());

        Assertions.assertEquals(task.getPriority(), response.getBody().get(0).getPriority());
        Assertions.assertEquals(taskHigh.getPriority(), response.getBody().get(1).getPriority());
        Assertions.assertEquals(taskExp.getPriority(), response.getBody().get(2).getPriority());
        Assertions.assertEquals(taskMedium.getPriority(), response.getBody().get(3).getPriority());
        Assertions.assertEquals(taskLow.getPriority(), response.getBody().get(4).getPriority());
    }

    @Test
    void getMemberTasksByPriorityTest_NOT_FOUND() {

        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(taskService.findAllTasksByMemberUuid(Mockito.anyString()))
                .thenReturn(new ArrayList<>());

        ResponseDto<List<TaskDto>> response = tabletService.getMemberTasksByPriority(member.getUuid(), task.getPriority());

        Mockito.verify(taskService, Mockito.times(1)).findAllTasksByMemberUuid(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void getMemberTasksByPriorityTest_FOUND() {

        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(taskDtoList);

        Mockito
                .when(taskService.findAllTasksByMemberUuid(Mockito.anyString()))
                .thenReturn(taskList);

        ResponseDto<List<TaskDto>> response = tabletService.getMemberTasksByPriority(member.getUuid(), task.getPriority());

        Mockito.verify(taskService, Mockito.times(1)).findAllTasksByMemberUuid(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().get(0).getId(), response.getBody().get(0).getId());
        Assertions.assertEquals(expectedResponse.getBody().get(0).getPriority(), response.getBody().get(0).getPriority());
    }

    @Test
    void getMemberTasksByStatusTest_NOT_FOUND() {

        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(taskService.findAllTasksByMemberUuid(Mockito.anyString()))
                .thenReturn(new ArrayList<>());

        ResponseDto<List<TaskDto>> response = tabletService.getMemberTasksByStatus(member.getUuid(), task.getStatus());

        Mockito.verify(taskService, Mockito.times(1)).findAllTasksByMemberUuid(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void getMemberTasksByStatusTest_FOUND() {

        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(taskDtoList);

        Mockito
                .when(taskService.findAllTasksByMemberUuid(Mockito.anyString()))
                .thenReturn(taskList);

        ResponseDto<List<TaskDto>> response = tabletService.getMemberTasksByPriority(member.getUuid(), task.getPriority());

        Mockito.verify(taskService, Mockito.times(1)).findAllTasksByMemberUuid(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().get(0).getId(), response.getBody().get(0).getId());
        Assertions.assertEquals(expectedResponse.getBody().get(0).getStatus(), response.getBody().get(0).getStatus());
    }

}