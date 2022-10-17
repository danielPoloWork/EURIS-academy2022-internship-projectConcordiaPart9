package com.euris.academy2022.concordia.businessLogics.services.trelloServices.impls;

import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCardService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloLabelService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
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
public class TrelloLabelServiceImpl implements TrelloLabelService {

    private final RestTemplate restTemplate;
    private final TrelloCardService trelloCardService;

    public TrelloLabelServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.trelloCardService = new TrelloCardServiceImpl();
    }

    public ResponseDto<ResponseEntity<String>> addLabelOnCardByIdCardAndIdLabel(String idCard, String idLabel) {
        ResponseDto<ResponseEntity<String>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.POST);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            setResponseEntityStringResponseConnectionTimeOut(response);
        } else {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
            URI uri = uriBuilder(URL_API_POST_LABEL_ON_CARD_BY_ID_CARD, idCard, idLabel);
            ResponseEntity<String> label = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);

            setResponseForAddLabelFromCard(response, label);
        }
        return response;
    }

    public ResponseDto<ResponseEntity<String>> removeLabelFromCardByIdCardAndIdLabel(String idCard, String idLabel) {
        ResponseDto<ResponseEntity<String>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.DELETE);

        if (!isHttpURLConnectionReached(URL_BOARD)) {
            setResponseEntityStringResponseConnectionTimeOut(response);
        } else {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
            URI uri = uriBuilder(URL_API_DELETE_LABEL_ON_CARD_BY_ID_CARD, idCard, idLabel);
            ResponseEntity<String> label = restTemplate.exchange(uri, HttpMethod.DELETE, requestEntity, String.class);

            setResponseForRemoveLabelFromCard(response, label);
        }
        return response;
    }

    private static Map<String, String> getUrlParamsForLabelActionOnCard(String idCard, String idLabel) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(ID_CARD, idCard);
        urlParams.put(ID_LABEL, idLabel);
        urlParams.put(KEY, KEY_VALUE);
        urlParams.put(TOKEN, TOKEN_VALUE);
        return urlParams;
    }

    private static URI uriBuilder(String apiUrl, String idCard, String idLabel) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl);
        return builder.buildAndExpand(getUrlParamsForLabelActionOnCard(idCard, idLabel)).toUri();
    }

    private static void setResponseForAddLabelFromCard(ResponseDto<ResponseEntity<String>> response, ResponseEntity<String> label) {
        if (Optional.ofNullable(label).isEmpty()) {
            setResponseEntityStringResponseNotFound(response);
        } else {
            setResponseEntityStringResponseAdded(response);
            response.setBody(label);
        }
    }

    private static void setResponseForRemoveLabelFromCard(ResponseDto<ResponseEntity<String>> response, ResponseEntity<String> label) {
        if (Optional.ofNullable(label).isEmpty()) {
            setResponseEntityStringResponseNotFound(response);
        } else {
            setResponseEntityStringResponseRemoved(response);
            response.setBody(label);
        }
    }
}