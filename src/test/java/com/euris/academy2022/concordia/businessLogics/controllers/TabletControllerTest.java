package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.configurations.TestSecurityCfg;
import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.businessLogics.services.TabletService;
import com.euris.academy2022.concordia.configurations.SecurityCfg;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
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

import static com.euris.academy2022.concordia.utils.constants.SecurityConstant.BEAN_BASIC_MEMBER;
import static com.euris.academy2022.concordia.utils.constants.SecurityConstant.BEAN_USERNAME_BASIC_MEMBER;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(value = {
        TestSecurityCfg.class,
        SecurityCfg.class
})
@ExtendWith(SpringExtension.class)
@WebMvcTest(TabletController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
class TabletControllerTest {

    @Autowired
    private MockMvc client;
    @Autowired
    private UserDetailsManager beanUdmBasicMember;

    @MockBean
    private TabletService tabletService;
    @MockBean
    private MemberService memberService;

    private final String REQUEST_MAPPING = "/api/tablet";
    private Member member;

    @BeforeEach
    void init() {
        member = Member.builder().uuid("uuidMember").build();
    }


    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getMemberTasksTest() throws Exception {
        when(tabletService.getMemberTasks(anyString()))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/" + member.getUuid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(tabletService, times(1)).getMemberTasks(anyString());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getHighPriorityTasksTest() throws Exception {
        when(tabletService.getMemberTasksByPriority(anyString(), any(TaskPriority.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/highPriority/" + member.getUuid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(tabletService, times(1)).getMemberTasksByPriority(anyString(), Mockito.any(TaskPriority.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getMediumPriorityTasksTest() throws Exception {
        when(tabletService.getMemberTasksByPriority(anyString(), any(TaskPriority.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/mediumPriority/" + member.getUuid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(tabletService, times(1)).getMemberTasksByPriority(anyString(), any(TaskPriority.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getLowPriorityTasksTest() throws Exception {
        when(tabletService.getMemberTasksByPriority(anyString(), any(TaskPriority.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/lowPriority/" + member.getUuid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(tabletService, times(1)).getMemberTasksByPriority(anyString(), any(TaskPriority.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getExpiringPriorityTasksTest() throws Exception {
        when(tabletService.getMemberTasksByPriority(anyString(), Mockito.any(TaskPriority.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/expiringPriority/" + member.getUuid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(tabletService, times(1)).getMemberTasksByPriority(anyString(), any(TaskPriority.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getToDoTasksTest() throws Exception {
        when(tabletService.getMemberTasksByStatus(anyString(), any(TaskStatus.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/toDoStatus/" + member.getUuid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(tabletService, times(1)).getMemberTasksByStatus(anyString(), any(TaskStatus.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getInProgressTasksTest() throws Exception {
        when(tabletService.getMemberTasksByStatus(anyString(), any(TaskStatus.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/inProgressStatus/" + member.getUuid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(tabletService, times(1)).getMemberTasksByStatus(anyString(), any(TaskStatus.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getCompletedTasksTest() throws Exception {
        when(tabletService.getMemberTasksByStatus(anyString(), any(TaskStatus.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/completedStatus/" + member.getUuid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(tabletService, times(1)).getMemberTasksByStatus(anyString(), any(TaskStatus.class));
    }
}