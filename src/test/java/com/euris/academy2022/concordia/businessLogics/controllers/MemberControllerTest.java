package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
public class MemberControllerTest {

    @Autowired
    private MockMvc client;
    @MockBean
    private MemberService memberService;
    private ObjectMapper objectMapper;
    private Member member;
    private final String REQUEST_MAPPING = "/api/member";
    private ResponseDto<MemberDto> modelResponse;
    private ResponseDto<List<MemberDto>> listResponse;

    @BeforeEach
    void init() {

        objectMapper = new ObjectMapper();

        member = Member.builder()
                .uuid("uuidMember")
                .idTrelloMember("idMember")
                .username("user")
                .role(MemberRole.A1)
                .firstName("name")
                .lastName("surname")
                .build();

        modelResponse = new ResponseDto<>();
        listResponse = new ResponseDto<>();
    }

    @Test
    void insertTest() throws Exception {
        Mockito
                .when(memberService.insert(Mockito.any(Member.class)))
                .thenReturn(modelResponse);

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(memberService, Mockito.times(1)).insert(Mockito.any(Member.class));
    }

    @Test
    void updateTest() throws Exception {
        Mockito
                .when(memberService.update(Mockito.any(Member.class)))
                .thenReturn(modelResponse);

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(memberService, Mockito.times(1)).update(Mockito.any(Member.class));
    }

    @Test
    void removeByUuidTest() throws Exception {
        Mockito
                .when(memberService.removeByUuid(Mockito.anyString()))
                .thenReturn(modelResponse);

        client
                .perform(delete(REQUEST_MAPPING + "/" + member.getUuid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(memberService, Mockito.times(1)).removeByUuid(Mockito.anyString());
    }

    @Test
    void getAllTest() throws Exception {
        Mockito
                .when(memberService.getAll())
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(memberService, Mockito.times(1)).getAll();
    }

    @Test
    void getByUuidTest() throws Exception {
        Mockito
                .when(memberService.getByUuid(Mockito.anyString()))
                .thenReturn(modelResponse);

        client
                .perform(get(REQUEST_MAPPING + "/" + member.getUuid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(memberService, Mockito.times(1)).getByUuid(Mockito.anyString());
    }

    @Test
    void getByIdTrelloMemberTest() throws Exception {
        Mockito
                .when(memberService.getByIdTrelloMember(Mockito.anyString()))
                .thenReturn(modelResponse);

        client
                .perform(get(REQUEST_MAPPING + "/idTrelloMember=" + member.getIdTrelloMember())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(memberService, Mockito.times(1)).getByIdTrelloMember(Mockito.anyString());
    }

    @Test
    void getUsernameTest() throws Exception {
        Mockito
                .when(memberService.getByUsername(Mockito.anyString()))
                .thenReturn(modelResponse);

        client
                .perform(get(REQUEST_MAPPING + "/username=" + member.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(memberService, Mockito.times(1)).getByUsername(Mockito.anyString());
    }

    @Test
    void getByRoleTest() throws Exception {
        Mockito
                .when(memberService.getByRole(Mockito.any(MemberRole.class)))
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING + "/role=" + member.getRole())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(memberService, Mockito.times(1)).getByRole(Mockito.any(MemberRole.class));
    }

    @Test
    void getByNameTest() throws Exception {
        Mockito
                .when(memberService.getByFirstName(Mockito.anyString()))
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING + "/name=" + member.getFirstName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(memberService, Mockito.times(1)).getByFirstName(Mockito.anyString());
    }

    @Test
    void getBySurnameTest() throws Exception {
        Mockito
                .when(memberService.getByLastName(Mockito.anyString()))
                .thenReturn(listResponse);

        client
                .perform(get(REQUEST_MAPPING + "/surname=" + member.getLastName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(memberService, Mockito.times(1)).getByLastName(Mockito.anyString());
    }
}
