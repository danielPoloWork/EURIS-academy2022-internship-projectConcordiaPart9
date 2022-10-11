package com.euris.academy2022.concordia.utils;

import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCardDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCommentDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloMemberDto;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;

import java.util.List;

public class ResponseUtil {

    public static void setMemberResponseConnectionTimeOut(ResponseDto<List<TrelloMemberDto>> response) {
        response.setHttpResponse(HttpResponseType.CONNECTION_TIMED_OUT);
        response.setCode(HttpResponseType.CONNECTION_TIMED_OUT.getCode());
        response.setDesc(HttpResponseType.CONNECTION_TIMED_OUT.getDesc());
    }
    public static void setMemberResponseNotFound(ResponseDto<List<TrelloMemberDto>> response) {
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    }
    public static void setMemberResponseFound(ResponseDto<List<TrelloMemberDto>> response) {
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
    }

    public static void setCardResponseConnectionTimeOut(ResponseDto<List<TrelloCardDto>> response) {
        response.setHttpResponse(HttpResponseType.CONNECTION_TIMED_OUT);
        response.setCode(HttpResponseType.CONNECTION_TIMED_OUT.getCode());
        response.setDesc(HttpResponseType.CONNECTION_TIMED_OUT.getDesc());
    }
    public static void setCardResponseNotFound(ResponseDto<List<TrelloCardDto>> response) {
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    }
    public static void setCardResponseFound(ResponseDto<List<TrelloCardDto>> response) {
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
    }

    public static void setCommentResponseConnectionTimeOut(ResponseDto<List<TrelloCommentDto>> response) {
        response.setHttpResponse(HttpResponseType.CONNECTION_TIMED_OUT);
        response.setCode(HttpResponseType.CONNECTION_TIMED_OUT.getCode());
        response.setDesc(HttpResponseType.CONNECTION_TIMED_OUT.getDesc());
    }
    public static void setCommentResponseNotFound(ResponseDto<List<TrelloCommentDto>> response) {
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    }
    public static void setCommentResponseFound(ResponseDto<List<TrelloCommentDto>> response) {
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
    }

    public static void setStringResponseConnectionTimeOut(ResponseDto<String> response) {
        response.setHttpResponse(HttpResponseType.CONNECTION_TIMED_OUT);
        response.setCode(HttpResponseType.CONNECTION_TIMED_OUT.getCode());
        response.setDesc(HttpResponseType.CONNECTION_TIMED_OUT.getDesc());
    }
}