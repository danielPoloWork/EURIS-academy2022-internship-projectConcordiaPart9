package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.members.MemberPostRequest;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
public class MemberControllerTest {

    @Autowired
    private MockMvc client;

    @MockBean
    private MemberService memberService;

    private ObjectMapper objectMapper;
    private MemberDto member;
    private MemberPostRequest memberPost;
    private List<MemberDto> memberList;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        member = MemberDto.builder()
                .uuid("1")
                .idTrelloMember("1")
                .username("username1")
                .role(MemberRole.A1)
                .name("name1")
                .surname("surname1")
                .build();
        memberPost = MemberPostRequest.builder()
                .idTrelloMember("1")
                .username("username1")
                .role(MemberRole.A1)
                .name("name1")
                .surname("surname1")
                .build();

        memberList = new ArrayList<>();
        memberList.add(member);
    }

    private final String REQUEST_MAPPING = "/api/member";

    @Test
    @DisplayName("GIVEN MemberPostRequest WHEN insert() THEN response return POST, CREATED")
    void insertTest_ShouldBeCreated() throws Exception {
        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.POST);
        response.setHttpResponse(HttpResponseType.CREATED);
        response.setCode(HttpResponseType.CREATED.getCode());
        response.setDesc(HttpResponseType.CREATED.getDesc());
        response.setBody(member);

        Mockito.when(memberService.insert(Mockito.any(Member.class)))
                .thenReturn(response);

        client.perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberPost)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.POST.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.CREATED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.CREATED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.CREATED.getDesc()))
                .andExpect(jsonPath("$.body.uuid").value(member.getUuid()));
    }

    @Test
    @DisplayName("IF args are missing WHEN insert() THEN response return POST, NOT_CREATED")
    void insertTest_ShouldBeNotCreated() throws Exception {

        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.POST);
        response.setHttpResponse(HttpResponseType.NOT_CREATED);
        response.setCode(HttpResponseType.NOT_CREATED.getCode());
        response.setDesc(HttpResponseType.NOT_CREATED.getDesc());

        Mockito.when(memberService.insert(Mockito.any(Member.class)))
                .thenReturn(response);

        client.perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
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
    @DisplayName("GIVEN uuid, role, username, password WHEN update THEN response should be UPDATED")
    void updateTest_ShouldBeUpdated() throws Exception {
        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.PUT);
        response.setHttpResponse(HttpResponseType.UPDATED);
        response.setCode(HttpResponseType.UPDATED.getCode());
        response.setDesc(HttpResponseType.UPDATED.getDesc());
        response.setBody(member);

        Mockito
                .when(memberService.update(Mockito.any(Member.class)))
                .thenReturn(response);

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.PUT.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.UPDATED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.UPDATED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.UPDATED.getDesc()))
                .andExpect(jsonPath("$.body.uuid").value(member.getUuid()));
    }

    @Test
    @DisplayName("IF something goes wrong WHEN update THEN response should be NOT_UPDATED")
    void updateTest_ShouldBeNotUpdated() throws Exception {

        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.PUT);
        response.setHttpResponse(HttpResponseType.NOT_UPDATED);
        response.setCode(HttpResponseType.NOT_UPDATED.getCode());
        response.setDesc(HttpResponseType.NOT_UPDATED.getDesc());

        Mockito
                .when(memberService.update(Mockito.any(Member.class)))
                .thenReturn(response);

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.PUT.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_UPDATED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_UPDATED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_UPDATED.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN wrong uuid OR uuid==null  WHEN update THEN response should be NOT_FOUND")
    void updateTest_ShouldBeNotFound() throws Exception {

        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.PUT);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberService.update(Mockito.any(Member.class)))
                .thenReturn(response);

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.PUT.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN a right uuid WHEN deleteByUuid AND uuid is present THEN response should be DELETED")
    void deleteByUuidTest_ShouldBeDeleted() throws Exception {

        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.DELETE);
        response.setHttpResponse(HttpResponseType.DELETED);
        response.setCode(HttpResponseType.DELETED.getCode());
        response.setDesc(HttpResponseType.DELETED.getDesc());
        response.setBody(member);

        Mockito
                .when(memberService.removeByUuid(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(delete(REQUEST_MAPPING + "/" + member.getUuid()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.DELETE.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.DELETED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.DELETED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.DELETED.getDesc()))
                .andExpect(jsonPath("$.body.uuid").value(member.getUuid()));
    }

    @Test
    @DisplayName("GIVEN a uuid WHEN deleteById BUT couldn't delete THEN response should be NOT_DELETED")
    void deleteByIdTest_ShouldBeNotDeleted() throws Exception {

        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.DELETE);
        response.setHttpResponse(HttpResponseType.NOT_DELETED);
        response.setCode(HttpResponseType.NOT_DELETED.getCode());
        response.setDesc(HttpResponseType.NOT_DELETED.getDesc());

        Mockito
                .when(memberService.removeByUuid(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(delete(REQUEST_MAPPING + "/" + member.getUuid()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.DELETE.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_DELETED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_DELETED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_DELETED.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN a uuid WHEN deleteByUuid BUT couldn't find it THEN response should be NOT_FOUND")
    void deleteByUuidTest_ShouldBeNotFound() throws Exception {

        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.DELETE);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberService.removeByUuid(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(delete(REQUEST_MAPPING + "/" + member.getUuid()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.DELETE.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }


    @Test
    @DisplayName("GIVEN getAll() WHEN record are present THEN response should be FOUND")
    void getAllTest_ShouldBeFound() throws Exception {
        ResponseDto<List<MemberDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(memberList);

        Mockito
                .when(memberService.getAll())
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].uuid").value(memberList.get(0).getUuid()));
    }

    @Test
    @DisplayName("GIVEN getAll() WHEN table is empty THEN response should be FOUND")
    void getAllTest_ShouldBeNotFound() throws Exception {
        ResponseDto<List<MemberDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberService.getAll())
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN a right uuid WHEN getByUuid() THEN response should be FOUND")
    void getByUuidTest_ShouldBeFound() throws Exception{

        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(member);

        Mockito
                .when(memberService.getByUuid(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/" + member.getUuid()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body.uuid").value(member.getUuid()));
    }

    @Test
    @DisplayName("GIVEN a wrong uuid WHEN getByUuid() THEN response should be NOT_FOUND")
    void getByUuidTest_ShouldBeNotFound() throws Exception{
        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberService.getByUuid(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/wrongString"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN a right idTrelloMember WHEN getByIdTrelloMember() THEN response should be FOUND")
    void getByIdTrelloMemberTest_ShouldBeFound() throws Exception{

        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(member);

        Mockito
                .when(memberService.getByIdTrelloMember(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/idTrelloMember=" + member.getUuid()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body.uuid").value(member.getUuid()));
    }

    @Test
    @DisplayName("GIVEN a wrong idTrelloMember WHEN getByIdTrelloMember() THEN response should be NOT_FOUND")
    void getByIdTrelloMemberTest_ShouldBeNotFound() throws Exception{
        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberService.getByIdTrelloMember(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/idTrelloMember=WrongString"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN a right username WHEN getByUsername() THEN response should be FOUND")
    void getByUsernameTest_ShouldBeFound() throws Exception{

        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(member);

        Mockito
                .when(memberService.getByUsername(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/username=" + member.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body.uuid").value(member.getUuid()));
    }

    @Test
    @DisplayName("GIVEN a wrong username WHEN getByUsername() THEN response should be NOT_FOUND")
    void getByUsernameTest_ShouldBeNotFound() throws Exception{
        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberService.getByUsername(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/username=WrongUsername"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN a right role WHEN getByRole() THEN response should be FOUND")
    void getByRoleTest_ShouldBeFound() throws Exception{
        ResponseDto<List<MemberDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(memberList);

        Mockito
                .when(memberService.getByRole(Mockito.any(MemberRole.class)))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/role=" + MemberRole.A1.getLabel()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].uuid").value(memberList.get(0).getUuid()));
    }

    @Test
    @DisplayName("GIVEN a wrong role WHEN getByRole() THEN response should be NOT_FOUND")
    void getByRoleTest_ShouldBeNotFound() throws Exception{
        ResponseDto<List<MemberDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberService.getByRole(Mockito.any(MemberRole.class)))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/role=" + MemberRole.A1.getLabel()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN a right name WHEN getByName() THEN response should be FOUND")
    void getByNameTest_ShouldBeFound() throws Exception{

        ResponseDto<List<MemberDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(memberList);

        Mockito
                .when(memberService.getByName(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/name=" + member.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].uuid").value(memberList.get(0).getUuid()));
    }

    @Test
    @DisplayName("GIVEN a wrong name WHEN getByName() THEN response should be NOT_FOUND")
    void getByNameTest_ShouldBeNotFound() throws Exception{
        ResponseDto<List<MemberDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberService.getByName(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/name=WrongString"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }

    @Test
    @DisplayName("GIVEN a right surname WHEN getBySurname() THEN response should be FOUND")
    void getBySurnameTest_ShouldBeFound() throws Exception{

        ResponseDto<List<MemberDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(memberList);

        Mockito
                .when(memberService.getBySurname(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/surname=" + member.getSurname()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].uuid").value(memberList.get(0).getUuid()));
    }

    @Test
    @DisplayName("GIVEN a wrong surname WHEN getBySurname() THEN response should be NOT_FOUND")
    void getBySurnameTest_ShouldBeNotFound() throws Exception{
        ResponseDto<List<MemberDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberService.getBySurname(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/surname=WrongString"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
                .andExpect(jsonPath("$.body").isEmpty());
    }
}