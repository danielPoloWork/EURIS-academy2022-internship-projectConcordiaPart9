package com.euris.academy2022.concordia.businessLogics.services.trelloServices;

import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCardDto;

import java.util.List;

public interface TrelloCardService {
    ResponseDto<List<TrelloCardDto>> getCardsByIdList(String idList);
}
