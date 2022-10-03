package com.euris.academy2022.concordia.businessLogics.controllers;


import com.euris.academy2022.concordia.businessLogics.services.CommentService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
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
@WebMvcTest(CommentController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
public class CommentControllerTest {

    @Autowired
    private MockMvc client;

    @MockBean
    private CommentService commentService;

    private ObjectMapper objectMapper;

    private Member member1;
    private Task task1;
    private Comment comment1;
    private Comment comment2;
    private Comment comment3;
    private Comment comment4;
    private List<Comment> commentList;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        member1 = Member.builder()
                .id("a").build();

        task1 = Task.builder()
                .id("a").build();

        comment1 = Comment.builder()
                .task(task1)
                .uuid("c")
                .member(member1)
                .text("bla")
                .build();
        comment2 = Comment.builder()
                .task(task1)
                .uuid("a")
                .member(member1)
                .text("bli")
                .build();
        comment3 = Comment.builder()
                .task(task1)
                .uuid("b")
                .member(member1)
                .text("qqq")
                .build();
        comment4 = Comment.builder()
                .task(task1)
                .uuid("d")
                .member(member1)
                .text("dede")
                .build();

        commentList = new ArrayList<>();
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment3);
        commentList.add(comment4);

    }


    private final String REQUEST_MAPPING = "/api/comments";

    @Test
    @DisplayName("GIVEN text,idTask,idMember WHEN insert THEN response should be CREATED")
    void insertTest_ShouldBeCreated() throws Exception {
        ResponseDto<Comment> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.POST);
        response.setHttpResponse(HttpResponseType.CREATED);
        response.setCode(HttpResponseType.CREATED.getCode());
        response.setDesc(HttpResponseType.CREATED.getDesc());
        response.setBody(comment1);

        Mockito
                .when(commentService.insert(Mockito.any(Comment.class)))
                .thenReturn(response);

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.POST.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.CREATED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.CREATED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.CREATED.getDesc()))
                .andExpect(jsonPath("$.body.uuid").value(comment1.getUuid()))
                .andExpect(jsonPath("$.body.text").value(comment1.getText()));

    }


    @Test
    @DisplayName("IF args are missing WHEN insert THEN response should be NOT_CREATED")
    void insertTest_ShouldBeNotCreated() throws Exception {
        ResponseDto<Comment> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.POST);
        response.setHttpResponse(HttpResponseType.NOT_CREATED);
        response.setCode(HttpResponseType.NOT_CREATED.getCode());
        response.setDesc(HttpResponseType.NOT_CREATED.getDesc());

        Mockito
                .when(commentService.insert(Mockito.any(Comment.class)))
                .thenReturn(response);

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment1)))
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
    @DisplayName("GIVEN uuid, text WHEN update THEN response should be UPDATED")
    void updateTest_ShouldBeUpdated() throws Exception {

        ResponseDto<Comment> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.PUT);
        response.setHttpResponse(HttpResponseType.UPDATED);
        response.setCode(HttpResponseType.UPDATED.getCode());
        response.setDesc(HttpResponseType.UPDATED.getDesc());
        response.setBody(comment1);

        Mockito
                .when(commentService.update(Mockito.any(Comment.class)))
                .thenReturn(response);

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.PUT.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.UPDATED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.UPDATED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.UPDATED.getDesc()))
                .andExpect(jsonPath("$.body.uuid").value(comment1.getUuid()))
                .andExpect(jsonPath("$.body.text").value(comment1.getText()));
    }


    @Test
    @DisplayName("IF something goes wrong WHEN update THEN response should be NOT_UPDATED")
    void updateTest_ShouldBeNotUpdated() throws Exception {

        ResponseDto<Comment> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.PUT);
        response.setHttpResponse(HttpResponseType.NOT_UPDATED);
        response.setCode(HttpResponseType.NOT_UPDATED.getCode());
        response.setDesc(HttpResponseType.NOT_UPDATED.getDesc());

        Mockito
                .when(commentService.update(Mockito.any(Comment.class)))
                .thenReturn(response);

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment2)))
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
    @DisplayName("GIVEN a right uuid WHEN deleteByUuid AND uuid is present THEN response should be DELETED")
    void deleteByUuidTest_ShouldBeDeleted() throws Exception {

        ResponseDto<Comment> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.DELETE);
        response.setHttpResponse(HttpResponseType.DELETED);
        response.setCode(HttpResponseType.DELETED.getCode());
        response.setDesc(HttpResponseType.DELETED.getDesc());
        response.setBody(comment3);

        Mockito
                .when(commentService.deleteByUuid(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(delete(REQUEST_MAPPING + "/" + comment3.getUuid()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.DELETE.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.DELETED.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.DELETED.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.DELETED.getDesc()))
                .andExpect(jsonPath("$.body.uuid").value(comment3.getUuid()))
                .andExpect(jsonPath("$.body.text").value(comment3.getText()));
    }




    @Test
    @DisplayName("GIVEN a uuid WHEN deleteByUuid BUT couldn't delete THEN response should be NOT_DELETED")
    void deleteByUuidTest_ShouldBeNotDeleted() throws Exception {

        ResponseDto<Comment> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.DELETE);
        response.setHttpResponse(HttpResponseType.NOT_DELETED);
        response.setCode(HttpResponseType.NOT_DELETED.getCode());
        response.setDesc(HttpResponseType.NOT_DELETED.getDesc());

        Mockito
                .when(commentService.deleteByUuid(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(delete(REQUEST_MAPPING + "/" + comment4.getUuid()))
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

        ResponseDto<Comment> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.DELETE);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(commentService.deleteByUuid(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(delete(REQUEST_MAPPING + "/" + comment3.getUuid()))
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
        ResponseDto<List<Comment>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(commentList);

        Mockito
                .when(commentService.getAll())
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
                .andExpect(jsonPath("$.body[0].uuid").value(commentList.get(0).getUuid()))
                .andExpect(jsonPath("$.body[0].text").value(commentList.get(0).getText()))
                .andExpect(jsonPath("$.body[1].uuid").value(commentList.get(1).getUuid()))
                .andExpect(jsonPath("$.body[1].text").value(commentList.get(1).getText()));
    }





    @Test
    @DisplayName("GIVEN getAll() WHEN table is empty THEN response should be FOUND")
    void getAllTest_ShouldBeNotFound() throws Exception {
        ResponseDto<List<Comment>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(commentService.getAll())
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

        ResponseDto<Comment> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
        response.setBody(comment1);

        Mockito
                .when(commentService.getByUuid(Mockito.anyString()))
                .thenReturn(response);

        client
                .perform(get(REQUEST_MAPPING + "/" + comment1.getUuid()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
                .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
                .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
                .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
                .andExpect(jsonPath("$.body.uuid").value(comment1.getUuid()))
                .andExpect(jsonPath("$.body.text").value(comment1.getText()));
    }



    @Test
    @DisplayName("GIVEN a wrong uuid WHEN getByUuid() THEN response should be NOT_FOUND")
    void getByUuidTest_ShouldBeNotFound() throws Exception{
        ResponseDto<Comment> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(commentService.getByUuid(Mockito.anyString()))
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






}
