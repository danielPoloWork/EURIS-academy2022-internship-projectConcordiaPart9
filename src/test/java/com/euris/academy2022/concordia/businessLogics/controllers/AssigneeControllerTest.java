package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.AssigneeService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.AssigneeDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.assignees.AssigneeDeleteRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.assignees.AssigneePostRequest;
import com.euris.academy2022.concordia.dataPersistences.models.Assignee;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AssigneeController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
public class AssigneeControllerTest {

    @Autowired
    private MockMvc client;
    @MockBean
    private AssigneeService assigneeService;
    private ObjectMapper objectMapper;
    private AssigneePostRequest assigneePostRequest;
    private AssigneeDeleteRequest assigneeDeleteRequest;
    private final String REQUEST_MAPPING = "/api/assignee";
    private ResponseDto<AssigneeDto> modelResponse;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        assigneePostRequest = AssigneePostRequest.builder()
                .idTask("idTask")
                .uuidMember("uuidMember")
                .build();
        assigneeDeleteRequest = AssigneeDeleteRequest.builder()
                .idTask("idTask")
                .uuidMember("uuidMember")
                .build();
        modelResponse = new ResponseDto<>();
    }

    @Test
    void insertTest() throws Exception {
        Mockito
                .when(assigneeService.insert(Mockito.any(Assignee.class)))
                .thenReturn(modelResponse);

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assigneePostRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(assigneeService, Mockito.times(1)).insert(Mockito.any(Assignee.class));
    }

    @Test
    void removeTest() throws Exception {
        Mockito
                .when(assigneeService.removeByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(modelResponse);

        client
                .perform(delete(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assigneeDeleteRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(assigneeService, Mockito.times(1)).removeByUuidMemberAndIdTask(Mockito.anyString(), Mockito.anyString());
    }
}
