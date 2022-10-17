package com.euris.academy2022.concordia.businessLogics.services.trelloServices;

import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCommentDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TrelloCommentService {

    ResponseDto<ResponseEntity<String>> addCommentByIdCardAndText(String idCard, String text);
    ResponseDto<ResponseEntity<String>> updateCommentByIdCardAndIdCommentAndText(String idCard, String idComment, String text);
    ResponseDto<ResponseEntity<String>> removeCommentByIdCardAndIdComment(String idCard, String idComment);
    ResponseDto<List<TrelloCommentDto>> getAllCommentsByIdCard(String idCard);
    ResponseDto<List<TrelloCommentDto>> getAllComments();
    ResponseDto<TrelloCommentDto> getCommentByIdComment(String idComment);
}