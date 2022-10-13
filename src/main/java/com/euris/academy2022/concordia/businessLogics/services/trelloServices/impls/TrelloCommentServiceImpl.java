package com.euris.academy2022.concordia.businessLogics.services.trelloServices.impls;

import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCardService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCommentService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCardDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCommentDto;
import com.euris.academy2022.concordia.utils.ResponseUtil;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
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

@Service
public class TrelloCommentServiceImpl implements TrelloCommentService {

    private final RestTemplate restTemplate;
    private final TrelloCardService trelloCardService;

    public TrelloCommentServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.trelloCardService = new TrelloCardServiceImpl();
    }

    public ResponseDto<ResponseEntity<String>> addCommentByIdCardAndText(String idCard, String text) {
        ResponseDto<ResponseEntity<String>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.POST);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            ResponseUtil.setResponseEntityStringResponseConnectionTimeOut(response);
        } else {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
            URI uri = uriBuilderByIdCardAndText(URL_API_POST_COMMENT_BY_ID_CARD, idCard, text);
            ResponseEntity<String> card = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);

            setResponseForCard(response, card);
        }
        return response;
    }

    public ResponseDto<ResponseEntity<String>> updateCommentByIdCardAndIdCommentAndText(String idCard, String idComment, String text) {
        ResponseDto<ResponseEntity<String>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.PUT);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            ResponseUtil.setResponseEntityStringResponseConnectionTimeOut(response);
        } else {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
            URI uri = uriBuilderByIdCardAndIdCommentAndText(URL_API_PUT_COMMENT_BY_ID_CARD_AND_ID_COMMENT_AND_TEXT, idCard, idComment, text);
            ResponseEntity<String> card = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, String.class);

            setResponseForCard(response, card);
        }
        return response;
    }

    public ResponseDto<ResponseEntity<String>> removeCommentByIdCardAndIdComment(String idCard, String idComment) {
        ResponseDto<ResponseEntity<String>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.DELETE);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            ResponseUtil.setResponseEntityStringResponseConnectionTimeOut(response);
        } else {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
            URI uri = uriBuilderByIdCardAndIdComment(URL_API_DELETE_COMMENT_BY_ID_CARD_AND_ID_COMMENT, idCard, idComment);
            ResponseEntity<String> card = restTemplate.exchange(uri, HttpMethod.DELETE, requestEntity, String.class);

            setResponseForCard(response, card);
        }
        return response;
    }

    private static URI uriBuilderByIdCardAndText(String apiUrl, String idCard, String text) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl);
        return builder.buildAndExpand(getUrlParamsByIdCardAndText(idCard, text)).toUri();
    }

    private static URI uriBuilderByIdCardAndIdComment(String apiUrl, String idCard, String idComment) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl);
        return builder.buildAndExpand(getUrlParamsByIdCardAndIdComment(idCard, idComment)).toUri();
    }

    private static URI uriBuilderByIdCardAndIdCommentAndText(String apiUrl, String idCard, String idComment, String text) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl);
        return builder.buildAndExpand(getUrlParamsByIdCardAndIdCommentAndText(idCard, idComment, text)).toUri();
    }

    private static Map<String, String> getUrlParamsByIdCardAndText(String idCard, String text) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(ID_CARD, idCard);
        urlParams.put(TEXT, text);
        urlParams.put(KEY, KEY_VALUE);
        urlParams.put(TOKEN, TOKEN_VALUE);

        return urlParams;
    }

    private static Map<String, String> getUrlParamsByIdCardAndIdComment(String idCard, String idComment) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(ID_CARD, idCard);
        urlParams.put(ID_COMMENT, idComment);
        urlParams.put(KEY, KEY_VALUE);
        urlParams.put(TOKEN, TOKEN_VALUE);

        return urlParams;
    }

    private static Map<String, String> getUrlParamsByIdCardAndIdCommentAndText(String idCard, String idComment, String text) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(ID_CARD, idCard);
        urlParams.put(ID_COMMENT, idComment);
        urlParams.put(TEXT, text);
        urlParams.put(KEY, KEY_VALUE);
        urlParams.put(TOKEN, TOKEN_VALUE);

        return urlParams;
    }

    private static void setResponseForCard(ResponseDto<ResponseEntity<String>> response, ResponseEntity<String> card) {
        if (Optional.ofNullable(card).isEmpty()) {
            ResponseUtil.setResponseEntityStringResponseNotFound(response);
        } else {
            setResponseEntityStringResponseUpdated(response);
            response.setBody(card);
        }
    }

    public ResponseDto<List<TrelloCommentDto>> getAllComments() {
        ResponseDto<List<TrelloCommentDto>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            setListTrelloCommentDtoResponseConnectionTimeOut(response);
        } else {
            setListTrelloCommentDtoResponseFound(response);

            List<TrelloCommentDto> comments = new ArrayList<>();
            setCommentsByIdList(comments, TaskStatus.TO_DO.getTrelloListId());
            setCommentsByIdList(comments, TaskStatus.IN_PROGRESS.getTrelloListId());
            setCommentsByIdList(comments, TaskStatus.COMPLETED.getTrelloListId());

            response.setBody(comments);
        }
        return response;
    }

    private void setCommentsByIdList(List<TrelloCommentDto> comments, String idList) {
        ResponseDto<List<TrelloCardDto>> cardList = trelloCardService.getCardsByIdList(idList);

        if (Optional.ofNullable(cardList.getBody()).isPresent()) {
            for (TrelloCardDto card : cardList.getBody()) {
                comments.addAll(getAllCommentsByIdCard(card.getId()).getBody());
            }
        }
    }

    public ResponseDto<List<TrelloCommentDto>> getAllCommentsByIdCard(String idCard) {
        ResponseDto<List<TrelloCommentDto>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            setListTrelloCommentDtoResponseConnectionTimeOut(response);
        } else {

            String comments = restTemplate.getForObject(
                    URL_API_GET_COMMENTS_BY_ID_CARD,
                    String.class,
                    getUrlParamsByIdCard(idCard));

            if (Optional.ofNullable(comments).isPresent()) {
                setResponseForGetAllCommentsByIdCard(response, comments);
            }
        }
        return response;
    }

    private static Map<String, String> getUrlParamsByIdCard(String idCard) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(ID_CARD, idCard);
        urlParams.put(KEY, KEY_VALUE);
        urlParams.put(TOKEN, TOKEN_VALUE);

        return urlParams;
    }

    private static void setResponseForGetAllCommentsByIdCard(ResponseDto<List<TrelloCommentDto>> response, String comments) {
        if (Optional.ofNullable(comments).isEmpty()) {
            setListTrelloCommentDtoResponseNotFound(response);
        } else {
            setListTrelloCommentDtoResponseFound(response);

            JSONArray jsonArray = new JSONArray(comments);
            List<TrelloCommentDto> commentList = new ArrayList<>();

            response.setBody(setCommentListForGetCommentsByIdCard(jsonArray, commentList));
        }
    }

    private static List<TrelloCommentDto> setCommentListForGetCommentsByIdCard(JSONArray jsonArray, List<TrelloCommentDto> commentList) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = new JSONObject(jsonArray.get(i).toString());
                JSONObject data = new JSONObject(object.get(DATA).toString());
                JSONObject card = new JSONObject(data.get(CARD).toString());
                JSONObject member = new JSONObject(object.get(MEMBER_CREATOR).toString());

                JSONObject dataObj = object.getJSONObject(DATA);
                String dateLastEdited;
                if (dataObj.has(DATE_LAST_EDITED)) {
                    dateLastEdited = dataObj.getString(DATE_LAST_EDITED);
                } else {
                    dateLastEdited = "0000-01-01T00:00:00.000Z";
                }

                TrelloCommentDto comment = TrelloCommentDto.builder()
                        .id(object.getString(ID))
                        .idCard(card.getString(ID))
                        .type(object.getString(TYPE))
                        .dateLastEdited(dateLastEdited)
                        .idMemberCreator(member.getString(ID))
                        .text(data.getString(TEXT))
                        .build();
                commentList.add(comment);

            } catch (JSONException e) {
                System.out.printf("%s  [fetchComment  ] executed at %s  WARN : %s [%s]: %s\n",
                        Thread.currentThread().getName(),
                        new Date(),
                        e,
                        i,
                        HttpResponseType.NOT_FOUND.getLabel());
                i++;
            }
        }
        return commentList;
    }

    public ResponseDto<TrelloCommentDto> getCommentByIdComment(String idComment) {
        ResponseDto<TrelloCommentDto> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            ResponseUtil.setTrelloCommentDtoResponseConnectionTimeOut(response);
        } else {
            String comment = restTemplate.getForObject(
                    URL_API_GET_COMMENTS_BY_ID_COMMENT,
                    String.class,
                    getUrlParamsByIdComment(idComment));

            setResponseForGetCommentByIdComment(response, comment);
        }
        return response;
    }

    private static Map<String, String> getUrlParamsByIdComment(String idComment) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(ID_COMMENT, idComment);
        urlParams.put(KEY, KEY_VALUE);
        urlParams.put(TOKEN, TOKEN_VALUE);

        return urlParams;
    }

    private static void setResponseForGetCommentByIdComment(ResponseDto<TrelloCommentDto> response, String comment) {
        if (Optional.ofNullable(comment).isEmpty()) {
            setTrelloCommentDtoResponseNotFound(response);
        } else {
            setTrelloCommentDtoResponseFound(response);

            JSONObject object = new JSONObject(comment);
            JSONObject data = new JSONObject(object.get(DATA).toString());
            JSONObject card = new JSONObject(data.get(CARD).toString());
            JSONObject member = new JSONObject(object.get(MEMBER_CREATOR).toString());

            JSONObject dataObj = object.getJSONObject(DATA);
            String dateLastEdited;
            if (dataObj.has(DATE_LAST_EDITED)) {
                dateLastEdited = dataObj.getString(DATE_LAST_EDITED);
            } else {
                dateLastEdited = "0000-01-01T00:00:00.000Z";
            }

            TrelloCommentDto trelloCommentDto = TrelloCommentDto.builder()
                    .id(object.getString(ID))
                    .idCard(card.getString(ID))
                    .type(object.getString(TYPE))
                    .dateLastEdited(dateLastEdited)
                    .idMemberCreator(member.getString(ID))
                    .text(data.getString(TEXT))
                    .build();

            response.setBody(trelloCommentDto);

        }
    }
}
