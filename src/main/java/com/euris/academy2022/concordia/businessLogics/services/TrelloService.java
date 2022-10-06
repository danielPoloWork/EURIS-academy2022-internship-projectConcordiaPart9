package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TrelloCardDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TrelloCommentDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public interface TrelloService {

    public ResponseDto<List<TrelloCardDto>> getAllCards();

    public ResponseDto<List<TrelloCommentDto>> getAllCommentsOnCard(String cardId);

    public ResponseDto<List<TrelloCommentDto>> getAllCommentsOnToDoList();


}
