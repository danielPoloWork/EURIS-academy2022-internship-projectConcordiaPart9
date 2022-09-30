package com.euris.academy2022.concordia.businessLogics.impls;

import com.euris.academy2022.concordia.businessLogics.services.CommentService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataModels.User;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.CommentDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.responses.ResponseDto;
import com.euris.academy2022.concordia.jpaRepositories.CommentJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
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
        Optional<Member> optionalMember = memberJpaRepository.findById(comment.getMember().getId());
        Optional<Task> optionalTask = taskJpaRepository.findById(comment.getTask().getId());

        if(optionalMember.isEmpty() || optionalTask.isEmpty()) {
            response.setHttpRequest(HttpRequestType.GET);
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else{
            response.setHttpRequest(HttpRequestType.POST);
            Integer commentCreated = commentJpaRepository.insert(
                    comment.getText(),
                    LocalDateTime.now(),
                    comment.getTask().getId(),
                    comment.getMember().getId());

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

        Optional<Comment> optionalComment = commentJpaRepository.findByUuid(comment.getUuid());

        ResponseDto<Comment> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.PUT);
        Integer updated = commentJpaRepository.update(comment.getText(),comment.getUuid());

        if (updated==1) {


            response.setHttpResponse(HttpResponseType.CREATED);
            response.setCode(HttpResponseType.CREATED.getCode());
            response.setDesc(HttpResponseType.CREATED.getDesc());
            response.setBody(comment);
        } else {
            response.setHttpResponse(HttpResponseType.NOT_CREATED);
            response.setCode(HttpResponseType.NOT_CREATED.getCode());
            response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
        }
        return response;
    }


    @Override
    public ResponseDto<Comment> deleteByUuid(String uuid) {
        ResponseDto<Comment> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.DELETE);
        Integer commentDeleted = commentJpaRepository.removeByUuid(uuid);

        if (commentDeleted !=1) {
            response.setHttpRequest(HttpRequestType.GET);
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
