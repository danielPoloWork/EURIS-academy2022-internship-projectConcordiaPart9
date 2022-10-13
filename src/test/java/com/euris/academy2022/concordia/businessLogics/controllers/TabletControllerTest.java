package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.TabletService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TabletController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
class TabletControllerTest {

    @Autowired
    private MockMvc client;
    @MockBean
    private TabletService tabletService;
    private String uuidMember;
    private final String REQUEST_MAPPING = "/api/tablet";
    private ResponseDto<List<TaskDto>> listResponse;

    @BeforeEach
    void init() {
        uuidMember = "uuidMember";
        listResponse = new ResponseDto<>();
    }

    @Test
    void getMemberTasksTest() throws Exception {
        Mockito
                .when(tabletService.getMemberTasks(Mockito.anyString()))
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING + "/" + uuidMember)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(tabletService, Mockito.times(1)).getMemberTasks(Mockito.anyString());
    }

    @Test
    void getHighPriorityTasksTest() throws Exception {
        Mockito
                .when(tabletService.getMemberTasksByPriority(Mockito.anyString(), Mockito.any(TaskPriority.class)))
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING + "/highPriority/" + uuidMember)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(tabletService, Mockito.times(1)).getMemberTasksByPriority(Mockito.anyString(), Mockito.any(TaskPriority.class));
    }

    @Test
    void getMediumPriorityTasksTest() throws Exception {
        Mockito
                .when(tabletService.getMemberTasksByPriority(Mockito.anyString(), Mockito.any(TaskPriority.class)))
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING + "/mediumPriority/" + uuidMember)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(tabletService, Mockito.times(1)).getMemberTasksByPriority(Mockito.anyString(), Mockito.any(TaskPriority.class));
    }

    @Test
    void getLowPriorityTasksTest() throws Exception {
        Mockito
                .when(tabletService.getMemberTasksByPriority(Mockito.anyString(), Mockito.any(TaskPriority.class)))
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING + "/lowPriority/" + uuidMember)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(tabletService, Mockito.times(1)).getMemberTasksByPriority(Mockito.anyString(), Mockito.any(TaskPriority.class));
    }

    @Test
    void getExpiringPriorityTasksTest() throws Exception {
        Mockito
                .when(tabletService.getMemberTasksByPriority(Mockito.anyString(), Mockito.any(TaskPriority.class)))
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING + "/expiringPriority/" + uuidMember)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(tabletService, Mockito.times(1)).getMemberTasksByPriority(Mockito.anyString(), Mockito.any(TaskPriority.class));
    }

    @Test
    void getToDoTasksTest() throws Exception {
        Mockito
                .when(tabletService.getMemberTasksByStatus(Mockito.anyString(), Mockito.any(TaskStatus.class)))
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING + "/toDoStatus/" + uuidMember)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(tabletService, Mockito.times(1)).getMemberTasksByStatus(Mockito.anyString(), Mockito.any(TaskStatus.class));
    }

    @Test
    void getInProgressTasksTest() throws Exception {
        Mockito
                .when(tabletService.getMemberTasksByStatus(Mockito.anyString(), Mockito.any(TaskStatus.class)))
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING + "/inProgressStatus/" + uuidMember)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(tabletService, Mockito.times(1)).getMemberTasksByStatus(Mockito.anyString(), Mockito.any(TaskStatus.class));
    }

    @Test
    void getCompletedTasksTest() throws Exception {
        Mockito
                .when(tabletService.getMemberTasksByStatus(Mockito.anyString(), Mockito.any(TaskStatus.class)))
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING + "/completedStatus/" + uuidMember)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(tabletService, Mockito.times(1)).getMemberTasksByStatus(Mockito.anyString(), Mockito.any(TaskStatus.class));
    }
}