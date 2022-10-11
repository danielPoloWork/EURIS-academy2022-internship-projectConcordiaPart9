package com.euris.academy2022.concordia.businessLogics.services.trelloServices;

import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloMemberDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrelloMemberService {

    ResponseDto<List<TrelloMemberDto>> getMembersByBoardId(String idBoard);
}