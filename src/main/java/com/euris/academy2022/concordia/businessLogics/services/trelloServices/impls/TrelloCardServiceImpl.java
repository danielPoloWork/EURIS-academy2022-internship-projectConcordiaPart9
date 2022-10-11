package com.euris.academy2022.concordia.businessLogics.services.trelloServices.impls;

import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCardService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCardDto;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.euris.academy2022.concordia.utils.PingUtil.isHttpURLConnectionReached;
import static com.euris.academy2022.concordia.utils.ResponseUtil.*;
import static com.euris.academy2022.concordia.utils.constants.TrelloConstant.*;
import static com.euris.academy2022.concordia.utils.constants.TrelloConstant.DATE_LAST_ACTIVITY;

@Service
public class TrelloCardServiceImpl implements TrelloCardService {

    private final RestTemplate restTemplate;

    public TrelloCardServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseDto<List<TrelloCardDto>> getCardsByIdList(String idList) {
        ResponseDto<List<TrelloCardDto>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            setCardResponseConnectionTimeOut(response);
        } else {
            String cards = restTemplate.getForObject(
                    URL_API_GET_CARDS_BY_ID_LIST,
                    String.class,
                    getUrlParamsForGetCardsByIdList(idList));

            setResponseForGetCardsByIdList(response, cards);
        }
        return response;
    }
    private static Map<String, String> getUrlParamsForGetCardsByIdList(String idList) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(ID_LIST, idList);
        urlParams.put(KEY, KEY_VALUE);
        urlParams.put(TOKEN, TOKEN_VALUE);
        return urlParams;
    }
    private static void setResponseForGetCardsByIdList(ResponseDto<List<TrelloCardDto>> response, String cards) {
        if (Optional.ofNullable(cards).isEmpty()) {
            setCardResponseNotFound(response);
        } else {
            setCardResponseFound(response);

            JSONArray jsonArray = new JSONArray(cards);
            List<TrelloCardDto> cardList = new ArrayList<>();

            response.setBody(setCardListForGetCardsByIdList(jsonArray, cardList));
        }
    }
    private static List<TrelloCardDto> setCardListForGetCardsByIdList(JSONArray jsonArray, List<TrelloCardDto> cardList) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = new JSONObject(jsonArray.get(i).toString());
                JSONArray labelsArray = new JSONArray(object.get(LABELS).toString());
                JSONObject label = new JSONObject(labelsArray.get(0).toString());

                TrelloCardDto card = TrelloCardDto.builder()
                        .id(object.getString(ID))
                        .idBoard(object.getString(ID_BOARD))
                        .idList(object.getString(ID_LIST))
                        .idLabel(label.getString(ID))
                        .desc(object.getString(DESC))
                        .name(object.getString(NAME))
                        .due(object.get(DUE).toString())
                        .dateLastActivity(object.getString(DATE_LAST_ACTIVITY))
                        .build();
                cardList.add(card);

            } catch (JSONException e) {
                System.out.printf("%s  [fetchCard     ] executed at %s  WARN : |→ %s\n",
                        Thread.currentThread().getName(),
                        new Date(),
                        e);
                i++;
            }
        }
        return cardList;
    }

}
