package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.TrelloService;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TrelloCardDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TrelloCommentDto;
import com.euris.academy2022.concordia.utils.constants.TrelloConstant;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TrelloServiceImpl implements TrelloService {

    private final RestTemplate restTemplate;

    public TrelloServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseDto<List<TrelloCardDto>> getAllCards() {

        ResponseDto<List<TrelloCardDto>> response = new ResponseDto<>();

        response.setHttpRequest(HttpRequestType.GET);

        Map<String, String> urlParams = new HashMap<>();

        urlParams.put("id", TaskStatus.TO_DO.getTrelloListId());
        urlParams.put("trelloKey", TrelloConstant.KEY);
        urlParams.put("trelloToken", TrelloConstant.TOKEN);

        Optional<String> trelloResponse = Optional.ofNullable(
                restTemplate.getForObject(
                        TrelloConstant.URL_API +
                                "lists/{id}/cards?key={trelloKey}&token={trelloToken}",
                        String.class, urlParams));

        if (trelloResponse.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        } else {
            JSONArray jsonArray = new JSONArray(trelloResponse);

            List<TrelloCardDto> trelloCardDtoList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object = new JSONObject(jsonArray.get(i).toString());


                JSONArray labelsArray = new JSONArray(object.get("labels").toString());
                JSONObject label = new JSONObject(labelsArray.get(0).toString());


                TrelloCardDto task = TrelloCardDto.builder()
                        .id(object.getString("id"))
                        .desc(object.getString("desc"))
                        .idLabel(label.getString("id"))
                        .name(object.getString("name"))
                        .idBoard(object.getString("idBoard"))
                        .idList(TaskStatus.TO_DO.getTrelloListId())
                        .due(object.get("due").toString())
                        .dateLastActivity(object.getString("dateLastActivity"))
                        .build();
                trelloCardDtoList.add(task);
            }

            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(trelloCardDtoList);
        }

        return response;
    }

    public ResponseDto<List<TrelloCommentDto>> getAllCommentsOnCard(String cardId) {

        ResponseDto<List<TrelloCommentDto>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("cardId", cardId);
        urlParams.put("trelloKey", TrelloConstant.KEY);
        urlParams.put("trelloToken", TrelloConstant.TOKEN);

        Optional<String> trelloResponse = Optional.ofNullable(
                restTemplate.getForObject(
                        TrelloConstant.URL_API
                                + "cards/{cardId}/actions?filter=commentCard&key={trelloKey}&token={trelloToken}",
                        String.class, urlParams));

        if (trelloResponse.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        } else {
            JSONArray jsonArray = new JSONArray(trelloResponse);

            List<TrelloCommentDto> trelloCommentDtoList = new ArrayList<>();

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
                trelloCommentDtoList.add(trelloCommentDto);

            }

            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(trelloCommentDtoList);
        }

        return response;
    }


    public ResponseDto<List<TrelloCommentDto>> getAllCommentsOnToDoList() {

        ResponseDto<List<TrelloCommentDto>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        List<TrelloCommentDto> trelloCommentDtoList = new ArrayList<>();

        ResponseDto<List<TrelloCardDto>> trelloResponse = getAllCards();

        if (trelloResponse.getBody().isEmpty()) {

            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        } else {

            for (TrelloCardDto trelloCardDto : trelloResponse.getBody()) {
                trelloCommentDtoList.addAll(getAllCommentsOnCard(trelloCardDto.getId()).getBody());
            }

            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(trelloCommentDtoList);

        }

        return response;
    }


}
