package com.euris.academy2022.concordia.businessLogics.services.trelloServices.impls;

import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloMemberService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloMemberDto;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.euris.academy2022.concordia.utils.PingUtil.isHttpURLConnectionReached;
import static com.euris.academy2022.concordia.utils.ResponseUtil.*;
import static com.euris.academy2022.concordia.utils.constants.TrelloConstant.*;
import static com.euris.academy2022.concordia.utils.constants.TrelloConstant.USERNAME;

@Service
public class TrelloMemberServiceImpl implements TrelloMemberService {

    private final RestTemplate restTemplate;

    public TrelloMemberServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public ResponseDto<List<TrelloMemberDto>> getMembersByBoardId(String idBoard) {
        ResponseDto<List<TrelloMemberDto>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            setMemberResponseConnectionTimeOut(response);
        } else {
            String members = restTemplate.getForObject(
                    URL_API_GET_MEMBERS_BY_ID_BOARD,
                    String.class,
                    getUrlParamsForGetMembersByBoardId(idBoard));

            setResponseForGetMembersByBoardId(response, members);
        }
        return response;
    }

    private static Map<String, String> getUrlParamsForGetMembersByBoardId(String idBoard) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(ID_BOARD, idBoard);
        urlParams.put(KEY, KEY_VALUE);
        urlParams.put(TOKEN, TOKEN_VALUE);

        return urlParams;
    }

    private static void setResponseForGetMembersByBoardId(ResponseDto<List<TrelloMemberDto>> response, String members) {
        if (Optional.ofNullable(members).isEmpty()) {
            setMemberResponseNotFound(response);
        } else {
            setMemberResponseFound(response);

            JSONArray jsonArray = new JSONArray(members);
            List<TrelloMemberDto> memberList = new ArrayList<>();

            response.setBody(setMemberListForGetMembersByBoardId(jsonArray, memberList));
        }
    }

    private static List<TrelloMemberDto> setMemberListForGetMembersByBoardId(JSONArray jsonArray, List<TrelloMemberDto> memberList) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = new JSONObject(jsonArray.get(i).toString());

                TrelloMemberDto member = TrelloMemberDto.builder()
                        .id(object.getString(ID))
                        .fullName(object.getString(FULL_NAME))
                        .username(object.getString(USERNAME))
                        .build();
                memberList.add(member);

            } catch (JSONException e) {
                System.out.printf("%s  [fetchMember] executed at %s  WARN : %s [%s]: %s\n",
                        Thread.currentThread().getName(),
                        new Date(),
                        e,
                        i,
                        HttpResponseType.NOT_FOUND.getLabel());
                i++;
            }
        }
        return memberList;
    }
}
