package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.AssigneeService;
import com.euris.academy2022.concordia.dataPersistences.models.Assignee;
import com.euris.academy2022.concordia.dataPersistences.DTOs.AssigneeDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.assignees.AssigneePostRequest;
import com.euris.academy2022.concordia.utils.enums.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AssigneeController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
public class AssigneeControllerTest {

    @Autowired
    private MockMvc client;

    @MockBean
    private AssigneeService assigneeService;

    private ObjectMapper objectMapper;
    private AssigneeDto assignee;
    private AssigneePostRequest assigneePost;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        MemberDto member = MemberDto.builder()
                .uuid("1")
                .idTrelloMember("1")
                .username("username1")
                .role(MemberRole.A1)
                .name("name1")
                .surname("surname1")
                .build();

        TaskDto task = TaskDto.builder()
                .id("id1")
                .title("t1")
                .description("d1")
                .priority(TaskPriority.HIGH)
                .status(TaskStatus.TO_DO)
                .build();

        assignee = AssigneeDto.builder()
                .uuid("1")
                .memberDto(member)
                .taskDto(task)
                .build();

        assigneePost = AssigneePostRequest.builder()
                .uuidMember(member.getUuid())
                .idTask(task.getId())
                .build();
    }

    private final String REQUEST_MAPPING = "/api/assignee";

    @Test
    @DisplayName("GIVEN AssigneePostRequest WHEN insert() THEN response return POST, CREATED")
    void insertTest_ShouldBeCreated() throws Exception {
        ResponseDto<AssigneeDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.POST);
        response.setHttpResponse(HttpResponseType.CREATED);
        response.setCode(HttpResponseType.CREATED.getCode());
        response.setDesc(HttpResponseType.CREATED.getDesc());
        response.setBody(assignee);

        Mockito.when(assigneeService.insert(Mockito.any(Assignee.class)))
                .thenReturn(response);

        client.perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assigneePost)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.POST.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.CREATED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.CREATED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.CREATED.getDesc()))
                .andExpect(jsonPath("$.body.uuid").value(assignee.getUuid()));
    }

    @Test
    @DisplayName("IF args are missing WHEN insert() THEN response return POST, NOT_CREATED")
    void insertTest_ShouldBeNotCreated() throws Exception {
        ResponseDto<AssigneeDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.POST);
        response.setHttpResponse(HttpResponseType.NOT_CREATED);
        response.setCode(HttpResponseType.NOT_CREATED.getCode());
        response.setDesc(HttpResponseType.NOT_CREATED.getDesc());

        Mockito.when(assigneeService.insert(Mockito.any(Assignee.class)))
                .thenReturn(response);

        client.perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignee)))
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
    @DisplayName("GIVEN a right uuidMember AND idTAsk WHEN deleteByUuidMemberAndIdTask AND uuid is present THEN response should be DELETED")
    void deleteByUuidMemberAndIdTaskTest_ShouldBeDeleted() throws Exception {

        ResponseDto<AssigneeDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.DELETE);
        response.setHttpResponse(HttpResponseType.DELETED);
        response.setCode(HttpResponseType.DELETED.getCode());
        response.setDesc(HttpResponseType.DELETED.getDesc());
        response.setBody(assignee);

        Mockito
                .when(assigneeService.removeByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(delete(REQUEST_MAPPING
                        + "/uuidMember=" + assignee.getMemberDto().getUuid()
                        + "&idTask=" + assignee.getTaskDto().getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.DELETE.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.DELETED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.DELETED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.DELETED.getDesc()))
                .andExpect(jsonPath("$.body.uuid").value(assignee.getUuid()));
    }

    @Test
    @DisplayName("GIVEN a wrong uuidMember AND idTAsk WHEN deleteByUuidMemberAndIdTask BUT couldn't delete THEN response should be NOT_DELETED")
    void deleteByUuidMemberAndIdTaskTest_ShouldBeNotDeleted() throws Exception {

        ResponseDto<AssigneeDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.DELETE);
        response.setHttpResponse(HttpResponseType.NOT_DELETED);
        response.setCode(HttpResponseType.NOT_DELETED.getCode());
        response.setDesc(HttpResponseType.NOT_DELETED.getDesc());

        Mockito
                .when(assigneeService.removeByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(delete(REQUEST_MAPPING
                        + "/uuidMember=AnyString"
                        + "&idTask=AnyString"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.DELETE.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_DELETED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_DELETED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_DELETED.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }
}