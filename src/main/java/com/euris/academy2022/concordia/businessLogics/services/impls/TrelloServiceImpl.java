package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.TrelloService;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TrelloCardDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TrelloCommentDto;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrelloServiceImpl implements TrelloService {


    private RestTemplate restTemplate;

    TrelloServiceImpl() {
        restTemplate = new RestTemplate();
    }

    @Autowired
    private Environment env;

    public ResponseDto<List<TrelloCardDto>> getAllCards() {

        ResponseDto<List<TrelloCardDto>> responseDto = new ResponseDto<>();
        responseDto.setHttpRequest(HttpRequestType.GET);
        responseDto.setHttpResponse(HttpResponseType.FOUND);
        responseDto.setCode(HttpResponseType.FOUND.getCode());
        responseDto.setDesc(HttpResponseType.FOUND.getDesc());

        String trellokey = env.getProperty("trello.key");
        String trellotoken = env.getProperty("trello.token");
        String URL = "https://api.trello.com/1/lists/{id}/cards?key={trellokey}&token={trellotoken}";

        Map<String, String> urlParams = new HashMap<>();

        urlParams.put("id", TaskStatus.TO_DO.getTrelloListId());
        urlParams.put("trellokey", trellokey);
        urlParams.put("trellotoken", trellotoken);

        String response = restTemplate.getForObject(URL, String.class, urlParams);


        JSONArray jsonArray = new JSONArray(response);

        List<TrelloCardDto> trelloCardDtos = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject object = new JSONObject(jsonArray.get(i).toString());


            JSONArray labelsArray = new JSONArray(object.get("labels").toString());
            JSONObject label = new JSONObject(labelsArray.get(0).toString());


            TrelloCardDto task = TrelloCardDto.builder()
                    .id(object.getString("id"))
                    .desc(object.getString("desc"))
                    .idLabels(label.getString("id"))
                    .name(object.getString("name"))
                    .idBoard(object.getString("idBoard"))
                    .idList(TaskStatus.TO_DO.getTrelloListId())
                    .due(object.get("due").toString())
                    .dateLastActivity(object.getString("dateLastActivity"))
                    .build();
            trelloCardDtos.add(task);
        }

        responseDto.setBody(trelloCardDtos);
        return responseDto;
    }




    public ResponseDto<List<TrelloCommentDto>> getAllCommentsOnCard(String cardId) {

        ResponseDto<List<TrelloCommentDto>> responseDto = new ResponseDto<>();
        responseDto.setHttpRequest(HttpRequestType.GET);
        responseDto.setHttpResponse(HttpResponseType.FOUND);
        responseDto.setCode(HttpResponseType.FOUND.getCode());
        responseDto.setDesc(HttpResponseType.FOUND.getDesc());


        String trellokey = env.getProperty("trello.key");
        String trellotoken = env.getProperty("trello.token");
        String URL = "https://api.trello.com/1/cards/{cardId}/actions?filter=commentCard&key={trellokey}&token={trellotoken}";

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("cardId", cardId);
        urlParams.put("trellokey", trellokey);
        urlParams.put("trellotoken", trellotoken);

        String response = restTemplate.getForObject(URL, String.class, urlParams);
        JSONArray jsonArray = new JSONArray(response);

        List<TrelloCommentDto> trelloCommentDtos = new ArrayList<>();


        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject object = new JSONObject(jsonArray.get(i).toString());
            JSONObject data = new JSONObject(object.get("data").toString());
            JSONObject card = new JSONObject(data.get("card").toString());
            JSONObject member = new JSONObject(object.get("memberCreator").toString());

            TrelloCommentDto trelloCommentDto = TrelloCommentDto.builder()
                    .id(object.getString("id"))
                    .idCard(card.getString("id"))
                    .type(object.getString("type"))
                    .date(object.getString("date"))
                    .idMemberCreator(member.getString("id"))
                    .text(data.getString("text")).build();
            trelloCommentDtos.add(trelloCommentDto);

        }
        responseDto.setBody(trelloCommentDtos);

        return responseDto;
    }


    public ResponseDto<List<TrelloCommentDto>> getAllCommentsOnToDoList(){

        ResponseDto<List<TrelloCommentDto>> responseDto = new ResponseDto<>();
        responseDto.setHttpRequest(HttpRequestType.GET);
        responseDto.setHttpResponse(HttpResponseType.FOUND);
        responseDto.setCode(HttpResponseType.FOUND.getCode());
        responseDto.setDesc(HttpResponseType.FOUND.getDesc());

        List<TrelloCommentDto> trelloCommentDtos = new ArrayList<>();
        ResponseDto<List<TrelloCardDto>> trelloCardDtos = getAllCards();
        for(TrelloCardDto trelloCardDto : trelloCardDtos.getBody()){

            trelloCommentDtos.addAll(getAllCommentsOnCard(trelloCardDto.getId()).getBody());
        }

        responseDto.setBody(trelloCommentDtos);

        return responseDto;
    }



}
