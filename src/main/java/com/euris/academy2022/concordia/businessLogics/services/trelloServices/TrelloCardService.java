package com.euris.academy2022.concordia.businessLogics.services.trelloServices;

import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCardDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TrelloCardService {
    ResponseDto<List<TrelloCardDto>> getCardsByIdBoard(String idBoard);
    ResponseDto<List<TrelloCardDto>> getCardsByIdList(String idList);
    ResponseDto<TrelloCardDto> getCardByIdCard(String idCard);
    ResponseDto<ResponseEntity<String>> changeListByIdCardAndIdList(String idCard, String idList);
}
