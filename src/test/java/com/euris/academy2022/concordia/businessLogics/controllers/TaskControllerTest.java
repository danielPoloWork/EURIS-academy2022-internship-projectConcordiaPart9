package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.configurations.TestSecurityCfg;
import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.configurations.SecurityCfg;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;

import static com.euris.academy2022.concordia.utils.constants.SecurityConstant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(value = {
        TestSecurityCfg.class,
        SecurityCfg.class
})
@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
public class TaskControllerTest {

    @Autowired
    private MockMvc client;
    @Autowired
    private UserDetailsManager beanUdmAdmin;
    @Autowired
    private UserDetailsManager beanUdmBasicMember;

    @MockBean
    private TaskService taskService;
    @MockBean
    private MemberService memberService;

    private ObjectMapper objectMapper;
    private final String REQUEST_MAPPING = "/api/task";


    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void insertTest_AUTHORIZED() throws Exception {

        when(taskService.insert(any(Task.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Task())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskService, times(1)).insert(any(Task.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void insertTest_FORBIDDEN() throws Exception {

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Task())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void updateTest() throws Exception {
        when(taskService.update(any(Task.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Task())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskService, times(1)).update(any(Task.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void deleteByIdTest_AUTHORIZED() throws Exception {
        when(taskService.deleteById(anyString()))
                .thenReturn(new ResponseDto<>());

        client
                .perform(delete(REQUEST_MAPPING + "/idTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Task())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskService, times(1)).deleteById(Mockito.anyString());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void deleteByIdTest_FORBIDDEN() throws Exception {

        client
                .perform(delete(REQUEST_MAPPING + "/idTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Task())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getAllTest() throws Exception {
        when(taskService.getAll())
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskService, times(1)).getAll();
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getByIdTest() throws Exception {
        when(taskService.getByIdTrelloTask(anyString()))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/idTask")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskService, times(1)).getByIdTrelloTask(anyString());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getByTitle() throws Exception {
        when(taskService.getByTitle(anyString()))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/title=title")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskService, times(1)).getByTitle(anyString());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getByPriority() throws Exception {
        when(taskService.getByPriority(any(TaskPriority.class)))
                .thenReturn(new ResponseDto<>());

        TaskPriority priority = TaskPriority.HIGH;

        client
                .perform(get(REQUEST_MAPPING + "/priority=" + priority)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskService, times(1)).getByPriority(any(TaskPriority.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getByStatus() throws Exception {
        when(taskService.getByStatus(any(TaskStatus.class)))
                .thenReturn(new ResponseDto<>());

        TaskStatus status = TaskStatus.TO_DO;

        client
                .perform(get(REQUEST_MAPPING + "/status=" + status)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskService, times(1)).getByStatus(any(TaskStatus.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getByDeadLine() throws Exception {

        when(taskService.getByDeadLine(any(LocalDateTime.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/deadLine=" + LocalDateTime.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskService, times(1)).getByDeadLine(any(LocalDateTime.class));
    }
}
