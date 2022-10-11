package com.euris.academy2022.concordia.businessLogics.services.trelloServices;

import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrelloCommentService {
    ResponseDto<List<TrelloCommentDto>> getCommentsByIdCard(String idCard);
    ResponseDto<List<TrelloCommentDto>> getAllComments();
}