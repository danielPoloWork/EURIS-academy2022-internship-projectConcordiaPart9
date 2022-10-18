package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.configurations.TestSecurityCfg;
import com.euris.academy2022.concordia.businessLogics.services.CommentService;
import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.configurations.SecurityCfg;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.comments.CommentPostRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.comments.CommentPutRequest;
import com.euris.academy2022.concordia.dataPersistences.models.Comment;
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

import static com.euris.academy2022.concordia.utils.constants.SecurityConstant.BEAN_BASIC_MEMBER;
import static com.euris.academy2022.concordia.utils.constants.SecurityConstant.BEAN_USERNAME_BASIC_MEMBER;
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
@WebMvcTest(CommentController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
public class CommentControllerTest {

    @Autowired
    private MockMvc client;
    @Autowired
    private UserDetailsManager beanUdmBasicMember;

    @MockBean
    private CommentService commentService;
    @MockBean
    private MemberService memberService;

    private ObjectMapper objectMapper;
    private Comment comment;
    private final String REQUEST_MAPPING = "/api/comment";
    private CommentPostRequest commentPostRequest;
    private CommentPutRequest commentPutRequest;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        comment = Comment.builder().uuid("uuidComment").build();
        commentPostRequest = CommentPostRequest.builder().uuidMember("uuidMember").idTask("idTask").build();
        commentPutRequest = CommentPutRequest.builder().uuid("uuid").build();
    }


    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void insertTest() throws Exception {
        when(commentService.insert(any(Comment.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(post(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentPostRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commentService, times(1)).insert(any(Comment.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void updateTest() throws Exception {
        when(commentService.update(any(Comment.class)))
                .thenReturn(new ResponseDto<>());

        client
                .perform(put(REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentPutRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commentService, times(1)).update(any(Comment.class));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void removeByUuidTest() throws Exception {
        when(commentService.removeByUuid(anyString()))
                .thenReturn(new ResponseDto<>());

        client
                .perform(delete(REQUEST_MAPPING + "/" + comment.getUuid()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commentService, times(1)).removeByUuid(anyString());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getAllTest() throws Exception {
        when(commentService.getAll())
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commentService, times(1)).getAll();
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = BEAN_BASIC_MEMBER, value = BEAN_USERNAME_BASIC_MEMBER)
    void getByUuidTest() throws Exception {
        when(commentService.getByUuid(anyString()))
                .thenReturn(new ResponseDto<>());

        client
                .perform(get(REQUEST_MAPPING + "/" + comment.getUuid()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commentService, times(1)).getByUuid(anyString());
    }


}
