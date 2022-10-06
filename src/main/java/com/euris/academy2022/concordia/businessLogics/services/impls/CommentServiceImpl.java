package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.CommentService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import com.euris.academy2022.concordia.jpaRepositories.CommentJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public ResponseDto<Comment> getByUuid(String uuid) {

        ResponseDto<Comment> response = new ResponseDto<>();
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
            response.setBody(commentFound.get());
        }
        return response;
    }


    @Override
    public ResponseDto<List<Comment>> getAll() {
        ResponseDto<List<Comment>> response = new ResponseDto<>();
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
            response.setBody(commentListFound);
        }

        return response;
    }

    @Override
    public ResponseDto<Comment> insert(Comment comment) {
        ResponseDto<Comment> response = new ResponseDto<>();
        Optional<Member> optionalMember = memberJpaRepository.findById(comment.getMember().getUuid());
        Optional<Task> optionalTask = taskJpaRepository.findById(comment.getTask().getId());
        response.setHttpRequest(HttpRequestType.POST);


        if(optionalMember.isEmpty() || optionalTask.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else{
            Integer commentCreated = commentJpaRepository.insert(
                    comment.getText(),
                    LocalDateTime.now(),
                    comment.getTask().getId(),
                    comment.getMember().getUuid());

            if (commentCreated==1) {

                response.setHttpResponse(HttpResponseType.CREATED);
                response.setCode(HttpResponseType.CREATED.getCode());
                response.setDesc(HttpResponseType.CREATED.getDesc());
                response.setBody(comment);
            } else {
                response.setHttpResponse(HttpResponseType.NOT_CREATED);
                response.setCode(HttpResponseType.NOT_CREATED.getCode());
                response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
            }
        }


        return response;
    }


    @Override
    public ResponseDto<Comment> update(Comment comment) {
        ResponseDto<Comment> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.PUT);

        Optional<Comment> commentFound = commentJpaRepository.findByUuid(comment.getUuid());
        Optional<Member> memberFound = memberJpaRepository.findByUuid(comment.getMember().getUuid());
        Optional<Task> taskFound = taskJpaRepository.findById(comment.getTask().getId());

        if (commentFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            if (memberFound.isEmpty() || taskFound.isEmpty()) {
                response.setHttpResponse(HttpResponseType.NOT_FOUND);
                response.setCode(HttpResponseType.NOT_FOUND.getCode());
                response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
            } else {
                Integer updated = commentJpaRepository.update(
                        comment.getText(),
                        LocalDateTime.now(),
                        comment.getUuid());

                if (updated != 1) {
                    response.setHttpResponse(HttpResponseType.NOT_CREATED);
                    response.setCode(HttpResponseType.NOT_CREATED.getCode());
                    response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
                } else {
                    response.setHttpResponse(HttpResponseType.CREATED);
                    response.setCode(HttpResponseType.CREATED.getCode());
                    response.setDesc(HttpResponseType.CREATED.getDesc());
                    response.setBody(comment);
                }
            }
        }
        return response;
    }

    @Override
    public ResponseDto<Comment> deleteByUuid(String uuid) {
        ResponseDto<Comment> response = new ResponseDto<>();
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


}
