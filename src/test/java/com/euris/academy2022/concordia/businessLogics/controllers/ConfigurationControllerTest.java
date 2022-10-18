package com.euris.academy2022.concordia.businessLogics.controllers;


import com.euris.academy2022.concordia.businessLogics.configurations.TestSecurityCfg;
import com.euris.academy2022.concordia.businessLogics.services.ConfigurationService;
import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.configurations.SecurityCfg;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.configurations.ConfigurationPostRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.configurations.ConfigurationPutRequest;
import com.euris.academy2022.concordia.dataPersistences.models.Configuration;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(value = {
        TestSecurityCfg.class,
        SecurityCfg.class
})
@ExtendWith(SpringExtension.class)
@WebMvcTest(ConfigurationController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
public class ConfigurationControllerTest {

    @Autowired
    private MockMvc client;
    @Autowired
    private UserDetailsManager beanUdmAdmin;
    @Autowired
    private UserDetailsManager beanUdmBasicMember;

    @MockBean
    private ConfigurationService configurationService;
    @MockBean
    private MemberService memberService;

    private final String REQUEST_MAPPING = "/api/configuration";
    private ObjectMapper objectMapper;
    private Configuration configuration;
    private ConfigurationPostRequest configurationPostRequest;
    private ConfigurationPutRequest configurationPutRequest;


    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        configuration = Configuration.builder().label("label0").build();
        configurationPostRequest = ConfigurationPostRequest.builder().label("label1").build();
        configurationPutRequest = ConfigurationPutRequest.builder().label("label2").build();
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void insertTest_AUTHORIZED() throws Exception {

        when(configurationService.insert(any(Configuration.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(configurationPostRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(configurationService, times(1)).insert(any(Configuration.class));

    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void insertTest_FORBIDDEN() throws Exception {

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(configurationPostRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void updateTest_AUTHORIZED() throws Exception {

        when(configurationService.update(any(Configuration.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(configurationPutRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(configurationService, times(1)).update(any(Configuration.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void updateTest_FORBIDDEN() throws Exception {

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(configurationPutRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void deleteByLabelTest_AUTHORIZED() throws Exception {

        when(configurationService.deleteByLabel(anyString()))
                .thenReturn(new ResponseDto<>());

        client
                .perform(delete(REQUEST_MAPPING + "/" + configuration.getLabel())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(configuration)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(configurationService, times(1)).deleteByLabel(anyString());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void deleteByLabelTest_FORBIDDEN() throws Exception {

        client
                .perform(delete(REQUEST_MAPPING + "/" + configuration.getLabel())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(configuration)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void getByLabelTest_AUTHORIZED() throws Exception {

        when(configurationService.getByLabel(anyString()))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/" + configuration.getLabel())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(configurationService, times(1)).getByLabel(anyString());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getByLabelTest_FORBIDDEN() throws Exception {

        client
                .perform(get(REQUEST_MAPPING + "/" + configuration.getLabel())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }
}
