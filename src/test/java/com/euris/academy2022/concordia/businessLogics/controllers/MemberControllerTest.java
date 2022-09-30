package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.responses.ResponseDto;
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
    private MemberDto member1;
    private List<MemberDto> memberList;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        member1 = MemberDto.builder()
                .id("1")
                .name("name1")
                .surname("surname1")
                .role(MemberRole.A1)
                .build();

        memberList = new ArrayList<>();
        memberList.add(member1);

    }

    private final String REQUEST_MAPPING = "/api/member";

    @Test
    @DisplayName("GIVEN id, name, surname, role WHEN insert THEN response should be CREATED")
    void insertTest_ShouldBeCreated() throws Exception {
        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.POST);
        response.setHttpResponse(HttpResponseType.CREATED);
        response.setCode(HttpResponseType.CREATED.getCode());
        response.setDesc(HttpResponseType.CREATED.getDesc());
        response.setBody(member1);

        Mockito
                .when(memberService.insert(Mockito.any(Member.class)))
                .thenReturn(response);

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.POST.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.CREATED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.CREATED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.CREATED.getDesc()))
                .andExpect(jsonPath("$.body.id").value(member1.getId()))
                .andExpect(jsonPath("$.body.name").value(member1.getName()))
                .andExpect(jsonPath("$.body.surname").value(member1.getSurname()))
                .andExpect(jsonPath("$.body.role").value(member1.getRole().getLabel()));
    }

    @Test
    @DisplayName("IF args are missing WHEN insert THEN response should be NOT_CREATED")
    void insertTest_ShouldBeNotCreated() throws Exception {

        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.POST);
        response.setHttpResponse(HttpResponseType.NOT_CREATED);
        response.setCode(HttpResponseType.NOT_CREATED.getCode());
        response.setDesc(HttpResponseType.NOT_CREATED.getDesc());

        Mockito
                .when(memberService.insert(Mockito.any(Member.class)))
                .thenReturn(response);

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member1)))
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
        response.setBody(member1);

        Mockito
                .when(memberService.update(Mockito.any(Member.class)))
                .thenReturn(response);

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.PUT.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.UPDATED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.UPDATED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.UPDATED.getDesc()))
                .andExpect(jsonPath("$.body.id").value(member1.getId()))
                .andExpect(jsonPath("$.body.name").value(member1.getName()))
                .andExpect(jsonPath("$.body.surname").value(member1.getSurname()))
                .andExpect(jsonPath("$.body.role").value(member1.getRole().getLabel()));
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
                        .content(objectMapper.writeValueAsString(member1)))
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
                        .content(objectMapper.writeValueAsString(member1)))
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
    @DisplayName("GIVEN a right id WHEN deleteById AND id is present THEN response should be DELETED")
    void deleteByIdTest_ShouldBeDeleted() throws Exception {

        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.DELETE);
        response.setHttpResponse(HttpResponseType.DELETED);
        response.setCode(HttpResponseType.DELETED.getCode());
        response.setDesc(HttpResponseType.DELETED.getDesc());
        response.setBody(member1);

        Mockito
                .when(memberService.deleteById(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(delete(REQUEST_MAPPING + "/" + member1.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.DELETE.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.DELETED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.DELETED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.DELETED.getDesc()))
                .andExpect(jsonPath("$.body.id").value(member1.getId()))
                .andExpect(jsonPath("$.body.name").value(member1.getName()))
                .andExpect(jsonPath("$.body.surname").value(member1.getSurname()))
                .andExpect(jsonPath("$.body.role").value(member1.getRole().getLabel()));
    }

    @Test
    @DisplayName("GIVEN a id WHEN deleteById BUT couldn't delete THEN response should be NOT_DELETED")
    void deleteByIdTest_ShouldBeNotDeleted() throws Exception {

        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.DELETE);
        response.setHttpResponse(HttpResponseType.NOT_DELETED);
        response.setCode(HttpResponseType.NOT_DELETED.getCode());
        response.setDesc(HttpResponseType.NOT_DELETED.getDesc());

        Mockito
                .when(memberService.deleteById(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(delete(REQUEST_MAPPING + "/" + member1.getId()))
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
    @DisplayName("GIVEN a id WHEN deleteById BUT couldn't find it THEN response should be NOT_FOUND")
    void deleteByUuidTest_ShouldBeNotFound() throws Exception {

        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.DELETE);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberService.deleteById(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(delete(REQUEST_MAPPING + "/" + member1.getId()))
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
                .andExpect(jsonPath("$.body[0].id").value(memberList.get(0).getId()))
                .andExpect(jsonPath("$.body[0].name").value(memberList.get(0).getName()))
                .andExpect(jsonPath("$.body[0].surname").value(memberList.get(0).getSurname()))
                .andExpect(jsonPath("$.body[0].role").value(memberList.get(0).getRole().getLabel()));
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
    @DisplayName("GIVEN a right id WHEN getById() THEN response should be FOUND")
    void getByIdTest_ShouldBeFound() throws Exception{

        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(member1);

        Mockito
                .when(memberService.getById(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/" + member1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body.id").value(member1.getId()))
                .andExpect(jsonPath("$.body.name").value(member1.getName()))
                .andExpect(jsonPath("$.body.surname").value(member1.getSurname()))
                .andExpect(jsonPath("$.body.role").value(member1.getRole().getLabel()));
    }

    @Test
    @DisplayName("GIVEN a wrong id WHEN getById() THEN response should be NOT_FOUND")
    void getByIdTest_ShouldBeNotFound() throws Exception{
        ResponseDto<MemberDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberService.getById(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/anyString"))
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
                .perform(get(REQUEST_MAPPING + "/name=" + member1.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].id").value(memberList.get(0).getId()))
                .andExpect(jsonPath("$.body[0].name").value(memberList.get(0).getName()))
                .andExpect(jsonPath("$.body[0].surname").value(memberList.get(0).getSurname()))
                .andExpect(jsonPath("$.body[0].role").value(memberList.get(0).getRole().getLabel()));
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
                .perform(get(REQUEST_MAPPING + "/name=" + member1.getName()))
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
                .perform(get(REQUEST_MAPPING + "/surname=" + member1.getSurname()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body[0].id").value(memberList.get(0).getId()))
                .andExpect(jsonPath("$.body[0].name").value(memberList.get(0).getName()))
                .andExpect(jsonPath("$.body[0].surname").value(memberList.get(0).getSurname()))
                .andExpect(jsonPath("$.body[0].role").value(memberList.get(0).getRole().getLabel()));
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
                .perform(get(REQUEST_MAPPING + "/surname=" + member1.getName()))
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
                .andExpect(jsonPath("$.body[0].id").value(memberList.get(0).getId()))
                .andExpect(jsonPath("$.body[0].name").value(memberList.get(0).getName()))
                .andExpect(jsonPath("$.body[0].surname").value(memberList.get(0).getSurname()))
                .andExpect(jsonPath("$.body[0].role").value(memberList.get(0).getRole().getLabel()));
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

}
