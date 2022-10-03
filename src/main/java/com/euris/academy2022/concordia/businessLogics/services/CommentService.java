package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Comment;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    ResponseDto<Comment> getByUuid(String id);
    ResponseDto<List<Comment>> getAll();
    ResponseDto<Comment> insert(Comment comment);
    ResponseDto<Comment> update(Comment comment);
    ResponseDto<Comment> deleteByUuid(String id);
}