package com.euris.academy2022.concordia.utils;

import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCardDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCommentDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloMemberDto;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseUtil {

    public static void setListTrelloMemberDtoResponseConnectionTimeOut(ResponseDto<List<TrelloMemberDto>> response) {
        response.setHttpResponse(HttpResponseType.CONNECTION_TIMED_OUT);
        response.setCode(HttpResponseType.CONNECTION_TIMED_OUT.getCode());
        response.setDesc(HttpResponseType.CONNECTION_TIMED_OUT.getDesc());
    }
    public static void setListTrelloMemberDtoResponseNotFound(ResponseDto<List<TrelloMemberDto>> response) {
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    }
    public static void setListTrelloMemberDtoResponseFound(ResponseDto<List<TrelloMemberDto>> response) {
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
    }

    public static void setListTrelloCardDtoResponseConnectionTimeOut(ResponseDto<List<TrelloCardDto>> response) {
        response.setHttpResponse(HttpResponseType.CONNECTION_TIMED_OUT);
        response.setCode(HttpResponseType.CONNECTION_TIMED_OUT.getCode());
        response.setDesc(HttpResponseType.CONNECTION_TIMED_OUT.getDesc());
    }
    public static void setListTrelloCardDtoResponseNotFound(ResponseDto<List<TrelloCardDto>> response) {
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    }
    public static void setListTrelloCardDtoResponseFound(ResponseDto<List<TrelloCardDto>> response) {
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
    }

    public static void setListTrelloCommentDtoResponseConnectionTimeOut(ResponseDto<List<TrelloCommentDto>> response) {
        response.setHttpResponse(HttpResponseType.CONNECTION_TIMED_OUT);
        response.setCode(HttpResponseType.CONNECTION_TIMED_OUT.getCode());
        response.setDesc(HttpResponseType.CONNECTION_TIMED_OUT.getDesc());
    }
    public static void setListTrelloCommentDtoResponseNotFound(ResponseDto<List<TrelloCommentDto>> response) {
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    }
    public static void setListTrelloCommentDtoResponseFound(ResponseDto<List<TrelloCommentDto>> response) {
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
    }

    public static void setResponseEntityStringResponseConnectionTimeOut(ResponseDto<ResponseEntity<String>> response) {
        response.setHttpResponse(HttpResponseType.CONNECTION_TIMED_OUT);
        response.setCode(HttpResponseType.CONNECTION_TIMED_OUT.getCode());
        response.setDesc(HttpResponseType.CONNECTION_TIMED_OUT.getDesc());
    }
    public static void setResponseEntityStringResponseNotFound(ResponseDto<ResponseEntity<String>> response) {
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    }
    public static void setResponseEntityStringResponseRemoved(ResponseDto<ResponseEntity<String>> response) {
        response.setHttpResponse(HttpResponseType.DELETED);
        response.setCode(HttpResponseType.DELETED.getCode());
        response.setDesc(HttpResponseType.DELETED.getDesc());
    }
    public static void setResponseEntityStringResponseAdded(ResponseDto<ResponseEntity<String>> response) {
        response.setHttpResponse(HttpResponseType.CREATED);
        response.setCode(HttpResponseType.CREATED.getCode());
        response.setDesc(HttpResponseType.CREATED.getDesc());
    }
    public static void setResponseEntityStringResponseUpdated(ResponseDto<ResponseEntity<String>> response) {
        response.setHttpResponse(HttpResponseType.UPDATED);
        response.setCode(HttpResponseType.UPDATED.getCode());
        response.setDesc(HttpResponseType.UPDATED.getDesc());
    }

    public static void setStringResponseConnectionTimeOut(ResponseDto<String> response) {
        response.setHttpResponse(HttpResponseType.CONNECTION_TIMED_OUT);
        response.setCode(HttpResponseType.CONNECTION_TIMED_OUT.getCode());
        response.setDesc(HttpResponseType.CONNECTION_TIMED_OUT.getDesc());
    }

    public static void setTrelloCardDtoResponseConnectionTimeOut(ResponseDto<TrelloCardDto> response) {
        response.setHttpResponse(HttpResponseType.CONNECTION_TIMED_OUT);
        response.setCode(HttpResponseType.CONNECTION_TIMED_OUT.getCode());
        response.setDesc(HttpResponseType.CONNECTION_TIMED_OUT.getDesc());
    }
    public static void setTrelloCardDtoResponseNotFound(ResponseDto<TrelloCardDto> response) {
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    }
    public static void setTrelloCardDtoResponseFound(ResponseDto<TrelloCardDto> response) {
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
    }

    public static void setTrelloCommentDtoResponseConnectionTimeOut(ResponseDto<TrelloCommentDto> response) {
        response.setHttpResponse(HttpResponseType.CONNECTION_TIMED_OUT);
        response.setCode(HttpResponseType.CONNECTION_TIMED_OUT.getCode());
        response.setDesc(HttpResponseType.CONNECTION_TIMED_OUT.getDesc());
    }
    public static void setTrelloCommentDtoResponseNotFound(ResponseDto<TrelloCommentDto> response) {
        response.setHttpResponse(HttpResponseType.NOT_FOUND);
        response.setCode(HttpResponseType.NOT_FOUND.getCode());
        response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    }
    public static void setTrelloCommentDtoResponseFound(ResponseDto<TrelloCommentDto> response) {
        response.setHttpResponse(HttpResponseType.FOUND);
        response.setCode(HttpResponseType.FOUND.getCode());
        response.setDesc(HttpResponseType.FOUND.getDesc());
    }
}