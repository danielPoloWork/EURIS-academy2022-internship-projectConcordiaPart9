package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.configurations.TestSecurityCfg;
import com.euris.academy2022.concordia.businessLogics.services.ConfigurationService;
import com.euris.academy2022.concordia.businessLogics.services.ConnectionWindowService;
import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.configurations.SecurityCfg;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ConfigurationDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ConnectionWindowDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.Configuration;
import com.euris.academy2022.concordia.dataPersistences.models.ConnectionWindow;
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

import static com.euris.academy2022.concordia.utils.constants.SecurityConstant.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private ResponseDto<ConnectionWindowDto> modelResponse;
    private ConnectionWindow connectionWindow;


    @BeforeEach
    void init() {

        objectMapper = new ObjectMapper();

        connectionWindow = ConnectionWindow.builder()
                .month(2)
                .cron("cron")
                .build();

        modelResponse = new ResponseDto<>();

    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void insertTest_AUTORIZED() throws Exception {

        Mockito
                .when(connectionWindowService.insert(Mockito.any(ConnectionWindow.class)))
                .thenReturn(modelResponse);

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(connectionWindow)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(connectionWindowService, Mockito.times(1)).insert(Mockito.any(ConnectionWindow.class));

    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void insertTest_FORBIDDEN() throws Exception {

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(connectionWindow)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void updateTest_AUTORIZED() throws Exception {

        Mockito
                .when(connectionWindowService.update(Mockito.any(ConnectionWindow.class)))
                .thenReturn(modelResponse);

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(connectionWindow)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(connectionWindowService, Mockito.times(1)).update(Mockito.any(ConnectionWindow.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void updateTest_FORBIDDEN() throws Exception {

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(connectionWindow)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void getByMonthTest_AUTORIZED() throws Exception {

        Mockito
                .when(connectionWindowService.getByMonth(Mockito.anyInt()))
                .thenReturn(modelResponse);

        client
                .perform(get(REQUEST_MAPPING + "/" + connectionWindow.getMonth())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(connectionWindowService, Mockito.times(1)).getByMonth(Mockito.anyInt());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getByMonthTest_FORBIDDEN() throws Exception {

        client
                .perform(get(REQUEST_MAPPING + "/" + connectionWindow.getMonth())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }
}
