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
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


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
    }

    @Test
    void insertTest_CREATED() {

        when(memberJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(member));

        when(taskJpaRepository.findById(anyString()))
                .thenReturn(Optional.of(task));

        when(assigneeJpaRepository.insert(
                anyString(),
                anyString(),
                any(LocalDateTime.class)))
                .thenReturn(1);

        ResponseDto<AssigneeDto> response = assigneeService.insert(assignee);

        verify(memberJpaRepository, times(1)).findByUuid(anyString());
        verify(taskJpaRepository, times(1)).findById(anyString());
        verify(assigneeJpaRepository, times(1)).insert(
                anyString(),
                anyString(),
                any(LocalDateTime.class));

        assertEquals(HttpRequestType.POST, response.getHttpRequest());
        assertEquals(HttpResponseType.CREATED, response.getHttpResponse());
        assertEquals(HttpResponseType.CREATED.getCode(), response.getCode());
        assertEquals(HttpResponseType.CREATED.getDesc(), response.getDesc());
        assertEquals(assignee.getMember().getIdTrelloMember(), response.getBody().getMemberDto().getIdTrelloMember());
        assertEquals(assignee.getMember().getUuid(), response.getBody().getMemberDto().getUuid());
        assertEquals(assignee.getTask().getId(), response.getBody().getTaskDto().getId());
        assertEquals(TaskStatus.IN_PROGRESS, response.getBody().getTaskDto().getStatus());

    }

    @Test
    void insertTest_NOT_CREATED() {

        when(memberJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(member));

        when(taskJpaRepository.findById(anyString()))
                .thenReturn(Optional.of(task));

        when(assigneeJpaRepository.insert(
                anyString(),
                anyString(),
                any(LocalDateTime.class)))
                .thenReturn(0);

        ResponseDto<AssigneeDto> response = assigneeService.insert(assignee);

        verify(memberJpaRepository, times(1)).findByUuid(anyString());
        verify(taskJpaRepository, times(1)).findById(anyString());
        verify(assigneeJpaRepository, times(1)).insert(
                anyString(),
                anyString(),
                any(LocalDateTime.class));

        assertEquals(HttpRequestType.POST, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_CREATED, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_CREATED.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_CREATED.getDesc(), response.getDesc());
        assertNull(response.getBody());

    }

    @Test
    void insertTest_NOT_FOUND() {

        when(memberJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.empty());

        when(taskJpaRepository.findById(anyString()))
                .thenReturn(Optional.of(task));

        ResponseDto<AssigneeDto> response = assigneeService.insert(assignee);

        verify(memberJpaRepository, times(1)).findByUuid(anyString());
        verify(taskJpaRepository, times(1)).findById(anyString());

        assertEquals(HttpRequestType.POST, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void removeByUuidMemberAndIdTaskTest_DELETED() {

        when(assigneeJpaRepository.findByUuidMemberAndIdTask(anyString(), anyString()))
                .thenReturn(Optional.of(assignee));

        when(assigneeJpaRepository.removeByUuidMemberAndIdTask(anyString(), anyString()))
                .thenReturn(1);

        ResponseDto<AssigneeDto> response = assigneeService.removeByUuidMemberAndIdTask(member.getUuid(), task.getId());

        verify(assigneeJpaRepository, times(1)).findByUuidMemberAndIdTask(anyString(), anyString());
        verify(assigneeJpaRepository, times(1)).removeByUuidMemberAndIdTask(anyString(), anyString());

        assertEquals(HttpRequestType.DELETE, response.getHttpRequest());
        assertEquals(HttpResponseType.DELETED, response.getHttpResponse());
        assertEquals(HttpResponseType.DELETED.getCode(), response.getCode());
        assertEquals(HttpResponseType.DELETED.getDesc(), response.getDesc());
        assertEquals(assignee.getMember().getIdTrelloMember(), response.getBody().getMemberDto().getIdTrelloMember());
        assertEquals(assignee.getMember().getUuid(), response.getBody().getMemberDto().getUuid());
        assertEquals(assignee.getTask().getId(), response.getBody().getTaskDto().getId());
    }

    @Test
    void removeByUuidMemberAndIdTaskTest_NOT_DELETED() {

        when(assigneeJpaRepository.findByUuidMemberAndIdTask(anyString(), anyString()))
                .thenReturn(Optional.of(assignee));

        when(assigneeJpaRepository.removeByUuidMemberAndIdTask(anyString(), anyString()))
                .thenReturn(0);

        ResponseDto<AssigneeDto> response = assigneeService.removeByUuidMemberAndIdTask(member.getUuid(), task.getId());

        verify(assigneeJpaRepository, times(1)).findByUuidMemberAndIdTask(anyString(), anyString());
        verify(assigneeJpaRepository, times(1)).removeByUuidMemberAndIdTask(anyString(), anyString());

        assertEquals(HttpRequestType.DELETE, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_DELETED, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_DELETED.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_DELETED.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void removeByUuidMemberAndIdTaskTest_NOT_FOUND() {

        when(assigneeJpaRepository.findByUuidMemberAndIdTask(anyString(), anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<AssigneeDto> response = assigneeService.removeByUuidMemberAndIdTask(member.getUuid(), task.getId());

        verify(assigneeJpaRepository, times(1)).findByUuidMemberAndIdTask(anyString(), anyString());

        assertEquals(HttpRequestType.DELETE, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getAllTest_FOUND() {

        when(assigneeJpaRepository.findAll())
                .thenReturn(assigneeList);

        ResponseDto<List<AssigneeDto>> response = assigneeService.getAll();

        verify(assigneeJpaRepository, times(1)).findAll();

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.FOUND.getDesc(), response.getDesc());
        assertEquals(assignee.getMember().getIdTrelloMember(), response.getBody().get(0).getMemberDto().getIdTrelloMember());
        assertEquals(assignee.getMember().getUuid(), response.getBody().get(0).getMemberDto().getUuid());
        assertEquals(assignee.getTask().getId(), response.getBody().get(0).getTaskDto().getId());
    }

    @Test
    void getAllTest_NOT_FOUND() {
        ResponseDto<List<AssigneeDto>> expectedResponse = new ResponseDto<>();

        when(assigneeJpaRepository.findAll())
                .thenReturn(new ArrayList<>());

        ResponseDto<List<AssigneeDto>> response = assigneeService.getAll();

        verify(assigneeJpaRepository, times(1)).findAll();

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getByUuidMemberAndIdTaskTest_FOUND() {

        when(assigneeJpaRepository.findByUuidMemberAndIdTask(anyString(), anyString()))
                .thenReturn(Optional.of(assignee));

        ResponseDto<AssigneeDto> response = assigneeService.getByUuidMemberAndIdTask(member.getUuid(), task.getId());

        verify(assigneeJpaRepository, times(1)).findByUuidMemberAndIdTask(anyString(), anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.FOUND.getDesc(), response.getDesc());
        assertEquals(assignee.getMember().getIdTrelloMember(), response.getBody().getMemberDto().getIdTrelloMember());
        assertEquals(assignee.getMember().getUuid(), response.getBody().getMemberDto().getUuid());
        assertEquals(assignee.getTask().getId(), response.getBody().getTaskDto().getId());
    }

    @Test
    void getByUuidMemberAndIdTaskTest_NOT_FOUND() {

        when(assigneeJpaRepository.findByUuidMemberAndIdTask(anyString(), anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<AssigneeDto> response = assigneeService.getByUuidMemberAndIdTask(member.getUuid(), task.getId());

        verify(assigneeJpaRepository, times(1)).findByUuidMemberAndIdTask(anyString(), anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

}