package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.configurations.TestSecurityCfg;
import com.euris.academy2022.concordia.businessLogics.services.ConnectionWindowService;
import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.configurations.SecurityCfg;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.connectionWindow.ConnectionWindowPostRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.connectionWindow.ConnectionWindowPutRequest;
import com.euris.academy2022.concordia.dataPersistences.models.ConnectionWindow;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static com.euris.academy2022.concordia.utils.constants.SecurityConstant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(value = {
        TestSecurityCfg.class,
        SecurityCfg.class
})
@ExtendWith(SpringExtension.class)
@WebMvcTest(ConnectionWindowController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
public class ConnectionWindowControllerTest {

    @Autowired
    private MockMvc client;
    @Autowired
    private UserDetailsManager beanUdmAdmin;

    @MockBean
    private ConnectionWindowService connectionWindowService;
    @MockBean
    private MemberService memberService;

    private final String REQUEST_MAPPING = "/api/connectionWindow";
    private ObjectMapper objectMapper;
    private ConnectionWindow connectionWindow;
    private ConnectionWindowPostRequest postRequest;
    private ConnectionWindowPutRequest putRequest;


    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        connectionWindow = ConnectionWindow.builder().cron("cron").month(1).build();
        postRequest = ConnectionWindowPostRequest.builder().cron("cron").build();
        putRequest = ConnectionWindowPutRequest.builder().cron("cron").build();
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void insertTest_AUTHORIZED() throws Exception {

        when(connectionWindowService.insert(any(ConnectionWindow.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(connectionWindowService, times(1)).insert(any(ConnectionWindow.class));

    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void insertTest_FORBIDDEN() throws Exception {

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void updateTest_AUTHORIZED() throws Exception {

        when(connectionWindowService.update(any(ConnectionWindow.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(connectionWindowService, times(1)).update(any(ConnectionWindow.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void updateTest_FORBIDDEN() throws Exception {

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void getByMonthTest_AUTHORIZED() throws Exception {

        when(connectionWindowService.getByMonth(anyInt()))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/" + connectionWindow.getMonth()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(connectionWindowService, times(1)).getByMonth(anyInt());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getByMonthTest_FORBIDDEN() throws Exception {

        client
                .perform(get(REQUEST_MAPPING + "/" + connectionWindow.getMonth()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }
}
