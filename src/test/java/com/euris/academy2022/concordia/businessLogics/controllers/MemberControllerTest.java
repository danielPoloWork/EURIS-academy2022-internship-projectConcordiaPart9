package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.configurations.TestSecurityCfg;
import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.UserDetailsManagerService;
import com.euris.academy2022.concordia.configurations.SecurityCfg;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.members.MemberPostRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.members.MemberPutRequest;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
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
@WebMvcTest(MemberController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
public class MemberControllerTest {

    @Autowired
    private MockMvc client;
    @Autowired
    private UserDetailsManager beanUdmAdmin;
    @Autowired
    private UserDetailsManager beanUdmBasicMember;

    @MockBean
    private MemberService memberService;
    @MockBean
    private UserDetailsManagerService userDetailsManagerService;

    private ObjectMapper objectMapper;
    private final String REQUEST_MAPPING = "/api/member";

    private Member member;
    private MemberPostRequest memberPostRequest;
    private MemberPutRequest memberPutRequest;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        member = Member.builder().uuid("uuidMember").username("username").idTrelloMember("idTrello").role(MemberRole.ADMIN).build();
        memberPostRequest = MemberPostRequest.builder().idTrelloMember("idTrello").build();
        memberPutRequest = MemberPutRequest.builder().uuid("uuidMember").build();
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void insertTest_AUTHORIZED() throws Exception {

        when(memberService.insert(any(Member.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberPostRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService, times(1)).insert(any(Member.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void insertTest_FORBIDDEN() throws Exception {

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberPostRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void updateTest_AUTHORIZED() throws Exception {

        when(memberService.update(any(Member.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberPutRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService, times(1)).update(any(Member.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void updateTest_FORBIDDEN() throws Exception {

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberPutRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void removeByUuidTest_AUTHORIZED() throws Exception {
        when(memberService.removeByUuid(anyString()))
                .thenReturn(new ResponseDto<>());

        client
                .perform(delete(REQUEST_MAPPING + "/" + member.getUuid()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService, times(1)).removeByUuid(anyString());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void removeByUuidTest_FORBIDDEN() throws Exception {

        client
                .perform(delete(REQUEST_MAPPING + "/" + member.getUuid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Member())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void getAllTest() throws Exception {
        when(memberService.getAllMemberDto())
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService, times(1)).getAllMemberDto();
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void getByUuidTest_AUTHORIZED() throws Exception {
        when(memberService.getMemberDtoByUuid(anyString()))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/" + member.getUuid()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService, times(1)).getMemberDtoByUuid(anyString());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getByUuidTest_FORBIDDEN() throws Exception {

        client
                .perform(get(REQUEST_MAPPING + "/" + member.getUuid()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void getByIdTrelloMemberTest_AUTHORIZED() throws Exception {
        when(memberService.getByIdTrelloMember(Mockito.anyString()))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/idTrelloMember=" + member.getIdTrelloMember()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService, times(1)).getByIdTrelloMember(anyString());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getByIdTrelloMemberTest_FORBIDDEN() throws Exception {

        client
                .perform(get(REQUEST_MAPPING + "/idTrelloMember=" + member.getIdTrelloMember()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void getUsernameTest_AUTHORIZED() throws Exception {
        when(memberService.getByUsername(Mockito.anyString()))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/username=" + member.getUsername()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService, times(1)).getByUsername(Mockito.anyString());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getUsernameTest_FORBIDDEN() throws Exception {

        client
                .perform(get(REQUEST_MAPPING + "/username=" + member.getUsername()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void getByRoleTest_AUTHORIZED() throws Exception {
        when(memberService.getMemberDtoListByRole(any(MemberRole.class)))
                .thenReturn(new ResponseDto<>());

        MemberRole role = MemberRole.A1;

        client
                .perform(get(REQUEST_MAPPING + "/role=" + member.getRole()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService, times(1)).getMemberDtoListByRole(any(MemberRole.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getByRoleTest_FORBIDDEN() throws Exception {

        client
                .perform(get(REQUEST_MAPPING + "/role=" + member.getRole()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void getByNameTest_AUTORIZED() throws Exception {
        when(memberService.getByFirstName(anyString()))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/name=" + member.getFirstName()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService, times(1)).getByFirstName(anyString());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getByNameTest_FORBIDDEN() throws Exception {

        client
                .perform(get(REQUEST_MAPPING + "/name=" + member.getFirstName()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_ADMIN, value = BEAN_USERNAME_ADMIN)
    void getBySurnameTest_AUTORIZED() throws Exception {
        when(memberService.getByLastName(anyString()))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/surname=" + member.getLastName()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(memberService, times(1)).getByLastName(anyString());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getBySurnameTest_FORBIDDEN() throws Exception {

        client
                .perform(get(REQUEST_MAPPING + "/surname=" + member.getLastName()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }
}
