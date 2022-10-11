package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.CommentService;
import com.euris.academy2022.concordia.dataPersistences.models.Comment;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.dataPersistences.DTOs.CommentDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.jpaRepositories.CommentJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentJpaRepository commentJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final TaskJpaRepository taskJpaRepository;

    public CommentServiceImpl(CommentJpaRepository commentJpaRepository, MemberJpaRepository memberJpaRepository, TaskJpaRepository taskJpaRepository) {
        this.commentJpaRepository = commentJpaRepository;
        this.memberJpaRepository = memberJpaRepository;
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public ResponseDto<CommentDto> insert(Comment comment) {
        ResponseDto<CommentDto> response = new ResponseDto<>();
        Optional<Task> optionalTask = taskJpaRepository.findById(comment.getTask().getId());
        response.setHttpRequest(HttpRequestType.POST);

        if(optionalTask.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else{
            Integer commentCreated = commentJpaRepository.insert(
                    comment.getIdTrelloComment(),
                    comment.getTask().getId(),
                    comment.getMember().getUuid(),
                    comment.getText(),
                    LocalDateTime.now(),
                    LocalDateTime.now());

            if (commentCreated==1) {
                response.setHttpResponse(HttpResponseType.CREATED);
                response.setCode(HttpResponseType.CREATED.getCode());
                response.setDesc(HttpResponseType.CREATED.getDesc());
                response.setBody(comment.toDto());
            } else {
                response.setHttpResponse(HttpResponseType.NOT_CREATED);
                response.setCode(HttpResponseType.NOT_CREATED.getCode());
                response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
            }
        }
        return response;
    }

    @Override
    public ResponseDto<CommentDto> insertFromTrello(Comment comment) {
        ResponseDto<CommentDto> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.POST);

        Integer commentCreated = commentJpaRepository.insert(
                comment.getIdTrelloComment(),
                comment.getTask().getId(),
                comment.getMember().getUuid(),
                comment.getText(),
                comment.getDateCreation(),
                comment.getDateUpdate());

        if (commentCreated == 1) {
            response.setHttpResponse(HttpResponseType.CREATED);
            response.setCode(HttpResponseType.CREATED.getCode());
            response.setDesc(HttpResponseType.CREATED.getDesc());
            response.setBody(comment.toDto());
        } else {
            response.setHttpResponse(HttpResponseType.NOT_CREATED);
            response.setCode(HttpResponseType.NOT_CREATED.getCode());
            response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
        }
        return response;
    }


    @Override
    public ResponseDto<CommentDto> update(Comment comment) {
        ResponseDto<CommentDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.PUT);

        Optional<Comment> commentFound = commentJpaRepository.findByUuid(comment.getUuid());

        if (commentFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            Integer updated = commentJpaRepository.update(
                    comment.getUuid(),
                    comment.getText(),
                    LocalDateTime.now());

            if (updated != 1) {
                response.setHttpResponse(HttpResponseType.NOT_CREATED);
                response.setCode(HttpResponseType.NOT_CREATED.getCode());
                response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
            } else {
                response.setHttpResponse(HttpResponseType.CREATED);
                response.setCode(HttpResponseType.CREATED.getCode());
                response.setDesc(HttpResponseType.CREATED.getDesc());
                response.setBody(comment.toDto());
            }
        }
        return response;
    }

    @Override
    public ResponseDto<CommentDto> updateFromTrello(Comment comment) {
        ResponseDto<CommentDto> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.PUT);

        Optional<Comment> commentFound = commentJpaRepository.findByIdTrelloComment(comment.getIdTrelloComment());

        if (commentFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            Integer updated = commentJpaRepository.updateFromTrello(
                    comment.getUuid(),
                    comment.getIdTrelloComment(),
                    comment.getText(),
                    comment.getDateUpdate());

            if (updated != 1) {
                response.setHttpResponse(HttpResponseType.NOT_CREATED);
                response.setCode(HttpResponseType.NOT_CREATED.getCode());
                response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
            } else {
                response.setHttpResponse(HttpResponseType.CREATED);
                response.setCode(HttpResponseType.CREATED.getCode());
                response.setDesc(HttpResponseType.CREATED.getDesc());
                response.setBody(comment.toDto());
            }
        }
        return response;
    }

    @Override
    public ResponseDto<CommentDto> removeByUuid(String uuid) {
        ResponseDto<CommentDto> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.DELETE);
        Integer commentDeleted = commentJpaRepository.removeByUuid(uuid);

        if (commentDeleted !=1) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {

            response.setHttpResponse(HttpResponseType.DELETED);
            response.setCode(HttpResponseType.DELETED.getCode());
            response.setDesc(HttpResponseType.DELETED.getDesc());

        }

        return response;
    }

    @Override
    public ResponseDto<CommentDto> getByUuid(String uuid) {

        ResponseDto<CommentDto> response = new ResponseDto<>();
        Optional<Comment> commentFound = commentJpaRepository.findById(uuid);

        response.setHttpRequest(HttpRequestType.GET);

        if (commentFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(commentFound.get().toDto());
        }
        return response;
    }


    @Override
    public ResponseDto<List<CommentDto>> getAll() {
        ResponseDto<List<CommentDto>> response = new ResponseDto<>();
        List<Comment> commentListFound = commentJpaRepository.findAll();

        response.setHttpRequest(HttpRequestType.GET);

        if (commentListFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(commentListFound.stream()
                    .map(Comment::toDto)
                    .collect(Collectors.toList()));
        }

        return response;
    }

    @Override
    public ResponseDto<CommentDto> getByIdTrelloComment(String idTrelloComment) {
        ResponseDto<CommentDto> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        Optional<Comment> commentFound = commentJpaRepository.findByIdTrelloComment(idTrelloComment);

        if (commentFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(commentFound.get().toDto());
        }

        return response;
    }
}
