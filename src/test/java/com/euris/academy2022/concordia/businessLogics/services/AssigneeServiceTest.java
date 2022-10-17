package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.businessLogics.services.impls.AssigneeServiceImpl;
import com.euris.academy2022.concordia.dataPersistences.DTOs.AssigneeDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.Assignee;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.jpaRepositories.AssigneeJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.enums.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application.test.properties")
class AssigneeServiceTest {

    @MockBean
    private AssigneeJpaRepository assigneeJpaRepository;
    @MockBean
    private MemberJpaRepository memberJpaRepository;
    @MockBean
    private TaskJpaRepository taskJpaRepository;
    @MockBean
    private TaskService taskService;
    private AssigneeService assigneeService;

    private Task task;
    private Member member;
    private Assignee assignee;
    private List<Assignee> assigneeList;
    private List<AssigneeDto> assigneeDtoList;

    @BeforeEach
    void init() {
        assigneeService = new AssigneeServiceImpl(assigneeJpaRepository, memberJpaRepository, taskJpaRepository, taskService);
        task = Task.builder()
                .id("idTask")
                .status(TaskStatus.TO_DO)
                .build();

        member = Member.builder()
                .idTrelloMember("idMember")
                .uuid("uuidMember")
                .build();

        assignee = Assignee.builder()
                .member(member)
                .task(task)
                .build();

        assigneeList = new ArrayList<>();
        assigneeList.add(assignee);
        assigneeDtoList = assigneeList.stream()
                .map(Assignee::toDto)
                .collect(Collectors.toList());
    }

    @Test
    void insertTest_FOUND_CREATED(){
        ResponseDto<AssigneeDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.POST);
        expectedResponse.setHttpResponse(HttpResponseType.CREATED);
        expectedResponse.setCode(HttpResponseType.CREATED.getCode());
        expectedResponse.setDesc(HttpResponseType.CREATED.getDesc());
        expectedResponse.setBody(assignee.toDto());

        Mockito
                .when(memberJpaRepository.findByUuid(Mockito.anyString()))
                .thenReturn(Optional.of(member));

        Mockito
                .when(taskJpaRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(task));

        Mockito
                .when(assigneeJpaRepository.insert(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(1);

        ResponseDto<AssigneeDto> response = assigneeService.insert(assignee);

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findByUuid(Mockito.anyString());
        Mockito.verify(taskJpaRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(assigneeJpaRepository, Mockito.times(1)).insert(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(LocalDateTime.class));

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getMemberDto().getIdTrelloMember(), response.getBody().getMemberDto().getIdTrelloMember());
        Assertions.assertEquals(expectedResponse.getBody().getMemberDto().getUuid(), response.getBody().getMemberDto().getUuid());
        Assertions.assertEquals(expectedResponse.getBody().getTaskDto().getId(), response.getBody().getTaskDto().getId());
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, response.getBody().getTaskDto().getStatus());

    }

    @Test
    void insertTest_FOUND_NOT_CREATED(){
        ResponseDto<AssigneeDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.POST);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_CREATED);
        expectedResponse.setCode(HttpResponseType.NOT_CREATED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_CREATED.getDesc());

        Mockito
                .when(memberJpaRepository.findByUuid(Mockito.anyString()))
                .thenReturn(Optional.of(member));

        Mockito
                .when(taskJpaRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(task));

        Mockito
                .when(assigneeJpaRepository.insert(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(0);

        ResponseDto<AssigneeDto> response = assigneeService.insert(assignee);

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findByUuid(Mockito.anyString());
        Mockito.verify(taskJpaRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(assigneeJpaRepository, Mockito.times(1)).insert(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(LocalDateTime.class));

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());

    }

    @Test
    void insertTest_NOT_FOUND(){
        ResponseDto<AssigneeDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.POST);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberJpaRepository.findByUuid(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Mockito
                .when(taskJpaRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(task));

        ResponseDto<AssigneeDto> response = assigneeService.insert(assignee);

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findByUuid(Mockito.anyString());
        Mockito.verify(taskJpaRepository, Mockito.times(1)).findById(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void removeByUuidMemberAndIdTaskTest_FOUND_DELETED() {
        ResponseDto<AssigneeDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.DELETE);
        expectedResponse.setHttpResponse(HttpResponseType.DELETED);
        expectedResponse.setCode(HttpResponseType.DELETED.getCode());
        expectedResponse.setDesc(HttpResponseType.DELETED.getDesc());
        expectedResponse.setBody(assignee.toDto());

        Mockito
                .when(assigneeJpaRepository.findByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(assignee));

        Mockito
                .when(assigneeJpaRepository.removeByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(1);

        ResponseDto<AssigneeDto> response = assigneeService.removeByUuidMemberAndIdTask(member.getUuid(), task.getId());

        Mockito.verify(assigneeJpaRepository, Mockito.times(1)).findByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(assigneeJpaRepository, Mockito.times(1)).removeByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getMemberDto().getIdTrelloMember(), response.getBody().getMemberDto().getIdTrelloMember());
        Assertions.assertEquals(expectedResponse.getBody().getMemberDto().getUuid(), response.getBody().getMemberDto().getUuid());
        Assertions.assertEquals(expectedResponse.getBody().getTaskDto().getId(), response.getBody().getTaskDto().getId());
    }

    @Test
    void removeByUuidMemberAndIdTaskTest_FOUND_NOT_DELETED() {
        ResponseDto<AssigneeDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.DELETE);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_DELETED);
        expectedResponse.setCode(HttpResponseType.NOT_DELETED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_DELETED.getDesc());

        Mockito
                .when(assigneeJpaRepository.findByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(assignee));

        Mockito
                .when(assigneeJpaRepository.removeByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(0);

        ResponseDto<AssigneeDto> response = assigneeService.removeByUuidMemberAndIdTask(member.getUuid(), task.getId());

        Mockito.verify(assigneeJpaRepository, Mockito.times(1)).findByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(assigneeJpaRepository, Mockito.times(1)).removeByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void removeByUuidMemberAndIdTaskTest_NOT_FOUND() {
        ResponseDto<AssigneeDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.DELETE);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(assigneeJpaRepository.findByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<AssigneeDto> response = assigneeService.removeByUuidMemberAndIdTask(member.getUuid(), task.getId());

        Mockito.verify(assigneeJpaRepository, Mockito.times(1)).findByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void getAllTest_FOUND() {
        ResponseDto<List<AssigneeDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(assigneeDtoList);

        Mockito
                .when(assigneeJpaRepository.findAll())
                .thenReturn(assigneeList);

        ResponseDto<List<AssigneeDto>> response = assigneeService.getAll();

        Mockito.verify(assigneeJpaRepository, Mockito.times(1)).findAll();

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().get(0).getMemberDto().getIdTrelloMember(), response.getBody().get(0).getMemberDto().getIdTrelloMember());
        Assertions.assertEquals(expectedResponse.getBody().get(0).getMemberDto().getUuid(), response.getBody().get(0).getMemberDto().getUuid());
        Assertions.assertEquals(expectedResponse.getBody().get(0).getTaskDto().getId(), response.getBody().get(0).getTaskDto().getId());
    }

    @Test
    void getAllTest_NOT_FOUND() {
        ResponseDto<List<AssigneeDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(assigneeJpaRepository.findAll())
                .thenReturn(new ArrayList<>());

        ResponseDto<List<AssigneeDto>> response = assigneeService.getAll();

        Mockito.verify(assigneeJpaRepository, Mockito.times(1)).findAll();

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void getByUuidMemberAndIdTaskTest_FOUND() {
        ResponseDto<AssigneeDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(assignee.toDto());

        Mockito
                .when(assigneeJpaRepository.findByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(assignee));

        ResponseDto<AssigneeDto> response = assigneeService.getByUuidMemberAndIdTask(member.getUuid(), task.getId());

        Mockito.verify(assigneeJpaRepository, Mockito.times(1)).findByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getMemberDto().getIdTrelloMember(), response.getBody().getMemberDto().getIdTrelloMember());
        Assertions.assertEquals(expectedResponse.getBody().getMemberDto().getUuid(), response.getBody().getMemberDto().getUuid());
        Assertions.assertEquals(expectedResponse.getBody().getTaskDto().getId(), response.getBody().getTaskDto().getId());
    }

    @Test
    void getByUuidMemberAndIdTaskTest_NOT_FOUND() {
        ResponseDto<AssigneeDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(assigneeJpaRepository.findByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<AssigneeDto> response = assigneeService.getByUuidMemberAndIdTask(member.getUuid(), task.getId());

        Mockito.verify(assigneeJpaRepository, Mockito.times(1)).findByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

}