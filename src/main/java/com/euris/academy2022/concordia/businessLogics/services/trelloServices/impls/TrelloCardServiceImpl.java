package com.euris.academy2022.concordia.businessLogics.services.trelloServices.impls;

import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCardService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCardDto;
import com.euris.academy2022.concordia.utils.ResponseUtil;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

    public ResponseDto<List<TrelloCardDto>> getCardsByIdBoard(String idBoard) {
        ResponseDto<List<TrelloCardDto>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            setListTrelloCardDtoResponseConnectionTimeOut(response);
        } else {
            String cards = restTemplate.getForObject(
                    URL_API_GET_CARDS_BY_ID_BOARD,
                    String.class,
                    getUrlParamsForCardActions(ID_BOARD, idBoard));

            setResponseForCardActions(response, cards);
        }
        return response;
    }

    public ResponseDto<List<TrelloCardDto>> getCardsByIdList(String idList) {
        ResponseDto<List<TrelloCardDto>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            setListTrelloCardDtoResponseConnectionTimeOut(response);
        } else {
            String cards = restTemplate.getForObject(
                    URL_API_GET_CARDS_BY_ID_LIST,
                    String.class,
                    getUrlParamsForCardActions(ID_LIST, idList));

            setResponseForCardActions(response, cards);
        }
        return response;
    }

    public ResponseDto<TrelloCardDto> getCardByIdCard(String idCard) {
        ResponseDto<TrelloCardDto> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            ResponseUtil.setTrelloCardDtoResponseConnectionTimeOut(response);
        } else {
            String card = restTemplate.getForObject(
                    URL_API_GET_CARD_BY_ID_CARD,
                    String.class,
                    getUrlParamsForCardActions(ID_CARD, idCard));

            setResponseForGetCardByIdCard(response, card);
        }
        return response;
    }

    private static Map<String, String> getUrlParamsForCardActions(String label, String id) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(label, id);
        urlParams.put(KEY, KEY_VALUE);
        urlParams.put(TOKEN, TOKEN_VALUE);
        return urlParams;
    }

    private static void setResponseForCardActions(ResponseDto<List<TrelloCardDto>> response, String cards) {
        if (Optional.ofNullable(cards).isEmpty()) {
            setListTrelloCardDtoResponseNotFound(response);
        } else {
            setListTrelloCardDtoResponseFound(response);

            JSONArray jsonArray = new JSONArray(cards);
            List<TrelloCardDto> cardList = new ArrayList<>();

            response.setBody(setCardListForCardActions(jsonArray, cardList));
        }
    }

    private static List<TrelloCardDto> setCardListForCardActions(JSONArray jsonArray, List<TrelloCardDto> cardList) {
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

    private static void setResponseForGetCardByIdCard(ResponseDto<TrelloCardDto> response, String card) {
        if (Optional.ofNullable(card).isEmpty()) {
            setTrelloCardDtoResponseNotFound(response);
        } else {
            setTrelloCardDtoResponseFound(response);

            JSONObject object = new JSONObject(card);
            JSONArray labelsArray = new JSONArray(object.get(LABELS).toString());
            JSONObject label = new JSONObject(labelsArray.get(0).toString());

            TrelloCardDto trelloCardDto = TrelloCardDto.builder()
                    .id(object.getString(ID))
                    .idBoard(object.getString(ID_BOARD))
                    .idList(object.getString(ID_LIST))
                    .idLabel(label.getString(ID))
                    .desc(object.getString(DESC))
                    .name(object.getString(NAME))
                    .due(object.get(DUE).toString())
                    .dateLastActivity(object.getString(DATE_LAST_ACTIVITY))
                    .build();

            response.setBody(trelloCardDto);
        }
    }

    public ResponseDto<ResponseEntity<String>> changeListByIdCardAndIdList(String idCard, String idList) {
        ResponseDto<ResponseEntity<String>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.PUT);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            ResponseUtil.setResponseEntityStringResponseConnectionTimeOut(response);
        } else {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
            URI uri = uriBuilder(URL_API_PUT_ID_LIST_ON_CARD_BY_ID_CARD, idCard, idList);
            ResponseEntity<String> card = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, String.class);

            setResponseForChangeListByIdCardAndIdList(response, card);
        }
        return response;
    }

    private static Map<String, String> getUrlParamsForChangeListByIdCardAndIdList(String idCard, String idList) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(ID_CARD, idCard);
        urlParams.put(ID_LIST, idList);
        urlParams.put(KEY, KEY_VALUE);
        urlParams.put(TOKEN, TOKEN_VALUE);
        return urlParams;
    }

    private static URI uriBuilder(String apiUrl, String idCard, String idList) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl);
        return builder.buildAndExpand(getUrlParamsForChangeListByIdCardAndIdList(idCard, idList)).toUri();
    }

    private static void setResponseForChangeListByIdCardAndIdList(ResponseDto<ResponseEntity<String>> response, ResponseEntity<String> card) {
        if (Optional.ofNullable(card).isEmpty()) {
            ResponseUtil.setResponseEntityStringResponseNotFound(response);
        } else {
            setResponseEntityStringResponseUpdated(response);
            response.setBody(card);
        }
    }
}
