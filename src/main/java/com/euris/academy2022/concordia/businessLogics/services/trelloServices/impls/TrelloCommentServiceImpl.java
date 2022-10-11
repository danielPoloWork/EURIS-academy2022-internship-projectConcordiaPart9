package com.euris.academy2022.concordia.businessLogics.services.trelloServices.impls;

import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCardService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCommentService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCardDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCommentDto;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public ResponseDto<List<TrelloCommentDto>> getCommentsByIdCard(String idCard) {
        ResponseDto<List<TrelloCommentDto>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            setCommentResponseConnectionTimeOut(response);
        } else {

            String comments = restTemplate.getForObject(
                    URL_API_GET_COMMENTS_BY_CARD_ID,
                    String.class,
                    getUrlParamsForGetCommentsByIdCard(idCard));

            setResponseForGetCommentsByIdCard(response, comments);
        }
        return response;
    }
    private static Map<String, String> getUrlParamsForGetCommentsByIdCard(String idCard) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(ID_CARD, idCard);
        urlParams.put(KEY, KEY_VALUE);
        urlParams.put(TOKEN, TOKEN_VALUE);

        return urlParams;
    }
    private static void setResponseForGetCommentsByIdCard(ResponseDto<List<TrelloCommentDto>> response, String comments) {
        if (Optional.ofNullable(comments).isEmpty()) {
            setCommentResponseNotFound(response);
        } else {
            setCommentResponseFound(response);

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

                TrelloCommentDto comment = TrelloCommentDto.builder()
                        .id(object.getString(ID))
                        .idCard(card.getString(ID))
                        .type(object.getString(TYPE))
                        .date(object.getString(DATE))
                        .idMemberCreator(member.getString(ID))
                        .text(data.getString(TEXT))
                        .build();
                commentList.add(comment);

            } catch (JSONException e) {
                System.out.printf("%s  [fetchComment] executed at %s  WARN : %s [%s]: %s\n",
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

    public ResponseDto<List<TrelloCommentDto>> getAllComments(){
        ResponseDto<List<TrelloCommentDto>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            setCommentResponseConnectionTimeOut(response);
        } else {
            setCommentResponseFound(response);

            List<TrelloCommentDto> comments = new ArrayList<>();
            setCommentsByIdList(comments, TaskStatus.TO_DO.getTrelloListId());
            setCommentsByIdList(comments, TaskStatus.IN_PROGRESS.getTrelloListId());
            setCommentsByIdList(comments, TaskStatus.COMPLETED.getTrelloListId());

            response.setBody(comments);
        }
        return response;
    }
    private void setCommentsByIdList(List<TrelloCommentDto> comments, String idList) {
        ResponseDto<List<TrelloCardDto>> cards = trelloCardService.getCardsByIdList(idList);

        if (Optional.ofNullable(cards.getBody()).isPresent()) {
            for(TrelloCardDto card : cards.getBody()){
                comments.addAll(getCommentsByIdCard(card.getId()).getBody());
            }
        }
    }
}
