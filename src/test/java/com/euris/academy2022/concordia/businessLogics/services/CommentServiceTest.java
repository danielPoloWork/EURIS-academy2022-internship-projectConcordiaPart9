package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.businessLogics.services.impls.CommentServiceImpl;
import com.euris.academy2022.concordia.dataPersistences.DTOs.CommentDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.Comment;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.jpaRepositories.CommentJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application.test.properties")
public class CommentServiceTest {

    @MockBean
    private MemberJpaRepository memberJpaRepository;

    @MockBean
    private CommentJpaRepository commentJpaRepository;

    @MockBean
    private TaskJpaRepository taskJpaRepository;

    private CommentService commentService;

    private Comment comment;
    private List<Comment> commentList;
    private Task task;
    private Member member;

    @BeforeEach
    void init() {

        commentService = new CommentServiceImpl(commentJpaRepository, memberJpaRepository, taskJpaRepository);

        task = Task.builder()
                .id("idTask")
                .build();

        member = Member.builder()
                .uuid("uuidMember")
                .build();

        comment = Comment.builder()
                .uuid("uuidComment")
                .idTrelloComment("idTrello")
                .task(task)
                .member(member)
                .text("text")
                .build();

        commentList = new ArrayList<>();
        commentList.add(comment);
    }

    @Test
    void insertTest_NOT_FOUND() {

        when(taskJpaRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<CommentDto> response = commentService.insert(comment);

        verify(taskJpaRepository, times(1)).findById(anyString());

        assertEquals(HttpRequestType.POST, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void insertTest_CREATED() {

        when(taskJpaRepository.findById(anyString()))
                .thenReturn(Optional.of(task));

        when(commentJpaRepository.insert(
                anyString(),
                anyString(),
                anyString(),
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(1);

        ResponseDto<CommentDto> response = commentService.insert(comment);

        verify(taskJpaRepository, times(1)).findById(anyString());
        verify(commentJpaRepository, times(1)).insert(
                anyString(),
                anyString(),
                anyString(),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        );

        assertEquals(HttpRequestType.POST, response.getHttpRequest());
        assertEquals(HttpResponseType.CREATED, response.getHttpResponse());
        assertEquals(HttpResponseType.CREATED.getCode(), response.getCode());
        assertEquals(HttpResponseType.CREATED.getDesc(), response.getDesc());
        assertEquals(comment.getUuid(), response.getBody().getUuid());
        assertEquals(comment.getTask().getId(), response.getBody().getTaskDto().getId());
        assertEquals(comment.getMember().getUuid(), response.getBody().getMemberDto().getUuid());
        assertEquals(comment.getText(), response.getBody().getText());
    }

    @Test
    void insertTest_NOT_CREATED() {
        when(taskJpaRepository.findById(anyString()))
                .thenReturn(Optional.of(task));

        when(commentJpaRepository.insert(
                anyString(),
                anyString(),
                anyString(),
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(0);

        ResponseDto<CommentDto> response = commentService.insert(comment);

        verify(taskJpaRepository, times(1)).findById(anyString());
        verify(commentJpaRepository, times(1)).insert(
                anyString(),
                anyString(),
                anyString(),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        );

        assertEquals(HttpRequestType.POST, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_CREATED, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_CREATED.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_CREATED.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void updateTest_NOT_FOUND() {
        when(commentJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<CommentDto> response = commentService.update(comment);

        verify(commentJpaRepository, times(1)).findByUuid(anyString());

        assertEquals(HttpRequestType.PUT, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void updateTest_UPDATED() {
        when(commentJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(comment));

        when(commentJpaRepository.update(
                anyString(),
                anyString(),
                any(LocalDateTime.class)))
                .thenReturn(1);

        ResponseDto<CommentDto> response = commentService.update(comment);

        verify(commentJpaRepository, times(1)).findByUuid(anyString());
        verify(commentJpaRepository, times(1)).update(
                anyString(),
                anyString(),
                any(LocalDateTime.class)
        );

        assertEquals(HttpRequestType.PUT, response.getHttpRequest());
        assertEquals(HttpResponseType.UPDATED, response.getHttpResponse());
        assertEquals(HttpResponseType.UPDATED.getCode(), response.getCode());
        assertEquals(HttpResponseType.UPDATED.getDesc(), response.getDesc());
        assertEquals(comment.getUuid(), response.getBody().getUuid());
        assertEquals(comment.getTask().getId(), response.getBody().getTaskDto().getId());
        assertEquals(comment.getMember().getUuid(), response.getBody().getMemberDto().getUuid());
        assertEquals(comment.getText(), response.getBody().getText());
    }

    @Test
    void updateTest_NOT_UPDATED() {
        when(commentJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(comment));

        when(commentJpaRepository.update(
                anyString(),
                anyString(),
                any(LocalDateTime.class)))
                .thenReturn(0);

        ResponseDto<CommentDto> response = commentService.update(comment);

        verify(commentJpaRepository, times(1)).findByUuid(anyString());
        verify(commentJpaRepository, times(1)).update(
                anyString(),
                anyString(),
                any(LocalDateTime.class)
        );

        assertEquals(HttpRequestType.PUT, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_UPDATED, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_UPDATED.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_UPDATED.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void removeByUuidTest_NOT_FOUND() {

        when(commentJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<CommentDto> response = commentService.removeByUuid(comment.getUuid());

        verify(commentJpaRepository, times(1)).findByUuid(anyString());

        assertEquals(HttpRequestType.DELETE, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void removeByUuidTest_DELETED() {

        when(commentJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(comment));

        when(commentJpaRepository.removeByUuid(anyString()))
                .thenReturn(1);

        ResponseDto<CommentDto> response = commentService.removeByUuid(comment.getUuid());

        verify(commentJpaRepository, times(1)).findByUuid(anyString());
        verify(commentJpaRepository, times(1)).removeByUuid(anyString());

        assertEquals(HttpRequestType.DELETE, response.getHttpRequest());
        assertEquals(HttpResponseType.DELETED, response.getHttpResponse());
        assertEquals(HttpResponseType.DELETED.getCode(), response.getCode());
        assertEquals(HttpResponseType.DELETED.getDesc(), response.getDesc());
        assertEquals(comment.getUuid(), response.getBody().getUuid());
        assertEquals(comment.getTask().getId(), response.getBody().getTaskDto().getId());
        assertEquals(comment.getMember().getUuid(), response.getBody().getMemberDto().getUuid());
        assertEquals(comment.getText(), response.getBody().getText());
    }

    @Test
    void removeByUuidTest_NOT_DELETED() {

        when(commentJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(comment));

        when(commentJpaRepository.removeByUuid(anyString()))
                .thenReturn(0);

        ResponseDto<CommentDto> response = commentService.removeByUuid(comment.getUuid());

        verify(commentJpaRepository, times(1)).findByUuid(anyString());
        verify(commentJpaRepository, times(1)).removeByUuid(anyString());

        assertEquals(HttpRequestType.DELETE, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_DELETED, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_DELETED.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_DELETED.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getByUuid_FOUND() {

        when(commentJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(comment));

        ResponseDto<CommentDto> response = commentService.getByUuid(comment.getUuid());

        verify(commentJpaRepository, times(1)).findByUuid(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.FOUND.getDesc(), response.getDesc());
        assertEquals(comment.getUuid(), response.getBody().getUuid());
    }

    @Test
    void getByUuid_NOT_FOUND() {

        when(commentJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<CommentDto> response = commentService.getByUuid(comment.getUuid());

        verify(commentJpaRepository, times(1)).findByUuid(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getAllTest_FOUND() {
        when(commentJpaRepository.findAll())
                .thenReturn(commentList);

        ResponseDto<List<CommentDto>> response = commentService.getAll();

        verify(commentJpaRepository, times(1)).findAll();

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.FOUND.getDesc(), response.getDesc());
        assertEquals(commentList.size(), response.getBody().size());
        assertEquals(comment.getUuid(), response.getBody().get(0).getUuid());
    }

    @Test
    void getAllTest_NOT_FOUND() {
        when(commentJpaRepository.findAll())
                .thenReturn(new ArrayList<>());

        ResponseDto<List<CommentDto>> response = commentService.getAll();

        verify(commentJpaRepository, times(1)).findAll();

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getByIdTrelloCommentTest_FOUND() {

        when(commentJpaRepository.findByIdTrelloComment(anyString()))
                .thenReturn(Optional.of(comment));

        ResponseDto<CommentDto> response = commentService.getByIdTrelloComment(comment.getIdTrelloComment());

        verify(commentJpaRepository, times(1)).findByIdTrelloComment(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.FOUND.getDesc(), response.getDesc());
        assertEquals(comment.getUuid(), response.getBody().getUuid());
        assertEquals(comment.getIdTrelloComment(), response.getBody().getIdTrelloComment());
    }

    @Test
    void getByIdTrelloCommentTest_NOT_FOUND() {

        when(commentJpaRepository.findByIdTrelloComment(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<CommentDto> response = commentService.getByIdTrelloComment(comment.getIdTrelloComment());

        verify(commentJpaRepository, times(1)).findByIdTrelloComment(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getAllWhereIdTrelloTaskIsNotNullTest_FOUND() {

        when(commentJpaRepository.findAllWhereIdTrelloCommentIsNotNull())
                .thenReturn(commentList);

        ResponseDto<List<CommentDto>> response = commentService.getAllWhereIdTrelloTaskIsNotNull();

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.FOUND.getDesc(), response.getDesc());
        assertEquals(commentList.size(), response.getBody().size());
        assertEquals(comment.getUuid(), response.getBody().get(0).getUuid());
        assertEquals(comment.getIdTrelloComment(), response.getBody().get(0).getIdTrelloComment());
    }

    @Test
    void getAllWhereIdTrelloTaskIsNotNullTest_NOT_FOUND() {
        when(commentJpaRepository.findAllWhereIdTrelloCommentIsNotNull())
                .thenReturn(new ArrayList<>());

        ResponseDto<List<CommentDto>> response = commentService.getAllWhereIdTrelloTaskIsNotNull();

        verify(commentJpaRepository, times(1)).findAllWhereIdTrelloCommentIsNotNull();

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }
}
