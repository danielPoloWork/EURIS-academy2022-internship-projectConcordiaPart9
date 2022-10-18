package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.DTOs.CommentFromTrelloDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.comments.CommentPutRequest;
import com.euris.academy2022.concordia.dataPersistences.models.Comment;
import com.euris.academy2022.concordia.dataPersistences.DTOs.CommentDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {
    ResponseDto<CommentDto> insert(Comment comment);
    ResponseDto<CommentDto> insertFromTrello(Comment comment);
    ResponseDto<CommentDto> update(Comment comment);
    ResponseDto<CommentFromTrelloDto> updateTrelloCommentIdMissing(CommentFromTrelloDto comment);
    ResponseDto<CommentFromTrelloDto> updateFromTrello(CommentFromTrelloDto comment);
    ResponseDto<CommentDto> removeByUuid(String uuid);
    ResponseDto<List<CommentDto>> getAll();
    ResponseDto<CommentDto> getByUuid(String uuid);
    ResponseDto<CommentDto> getByIdTrelloComment(String idTrelloComment);
    ResponseDto<List<CommentDto>> getAllWhereIdTrelloTaskIsNotNull();
    ResponseDto<List<CommentDto>> getAllByIdTask(String idTask);
}