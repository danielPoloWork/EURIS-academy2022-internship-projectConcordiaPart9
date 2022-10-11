package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.TrelloPushService;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCardDto;
import com.euris.academy2022.concordia.utils.constants.TrelloConstant;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrelloPushServiceImpl implements TrelloPushService {

    private final RestTemplate restTemplate;

    public TrelloPushServiceImpl(){
        restTemplate = new RestTemplate();
    }

    public void updateComment(String idAction, String idCard, String text) {

        String trellokey = TrelloConstant.KEY;
        String trellotoken = TrelloConstant.TOKEN;

        String URL = "https://api.trello.com/1/cards/{idCard}/actions/{idAction}/comments?text={text}&key={key}&token={token}";


        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("idCard", idCard);
        urlParams.put("idAction", idAction);
        urlParams.put("text", text);
        urlParams.put("trellokey", trellokey);
        urlParams.put("trellotoken", trellotoken);


        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity<>(headers);

        restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.PUT, requestEntity, Void.class);


    }


    public void postComment(String idCard, String text) {

        String trellokey = TrelloConstant.KEY;
        String trellotoken = TrelloConstant.TOKEN;

        String URL = "https://api.trello.com/1/cards/{idCard}/actions/comments?text={text}&key={trellokey}&token={trellotoken}";


        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("idCard", idCard);
        urlParams.put("text", text);
        urlParams.put("trellokey", trellokey);
        urlParams.put("trellotoken", trellotoken);


        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity<>(headers);

        restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.POST, requestEntity, Void.class);

    }


    public void deleteComment(String idCard, String idAction) {

        String trellokey = TrelloConstant.KEY;
        String trellotoken = TrelloConstant.TOKEN;

        String URL = "https://api.trello.com/1/cards/{idCard}/actions/{idAction}/comments?&key={trellokey}&token={trellotoken}";


        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("idCard", idCard);
        urlParams.put("idAction", idAction);
        urlParams.put("trellokey", trellokey);
        urlParams.put("trellotoken", trellotoken);


        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity<>(headers);

        restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.DELETE, requestEntity, Void.class);


    }


    private void updateListFromCards(List<Task> taskList) {

        String trellokey = TrelloConstant.KEY;
        String trellotoken = TrelloConstant.TOKEN;
        String URLLISTUPDATE = "https://api.trello.com/1/cards/{cardID}?&idList={idList}&key={trellokey}&token={trellotoken}";

        Map<String, String> urlParams = new HashMap<>();

        urlParams.put("trellokey", trellokey);
        urlParams.put("trellotoken", trellotoken);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        for (Task task : taskList) {
            urlParams.put("cardID", task.getId());
            urlParams.put("idList", task.getStatus().getTrelloListId());
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URLLISTUPDATE);
            restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.PUT, requestEntity, Void.class);
        }

    }


    private void updateLabelsFromCards(List<Task> taskList) {
        String trellokey = TrelloConstant.KEY;
        String trellotoken = TrelloConstant.TOKEN;
        String URL = "https://api.trello.com/1/cards/{idCard}?key={trellokey}&token={trellotoken}";

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("trellokey", trellokey);
        urlParams.put("trellotoken", trellotoken);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        for (Task task : taskList) {

            urlParams.put("idCard", task.getId());
            String response = restTemplate.getForObject(URL, String.class, urlParams);


            JSONObject object = new JSONObject(response);

            JSONArray labelsArray = new JSONArray(object.get("labels").toString());
            JSONObject label = new JSONObject(labelsArray.get(0).toString());
            String idLabel = label.getString("id");
            urlParams.put("idLabel", idLabel);
            String URLDELETE = "https://api.trello.com/1/cards/{idCard}/idLabels/{idLabel}?key={trellokey}&token={trellotoken}";

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URLDELETE);
            restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.DELETE, requestEntity, Void.class);

            String URLPOSTLABEL = "https://api.trello.com/1/cards/{idCard}/idLabels?value={idLabel}&key={trellokey}&token={trellotoken}";
            urlParams.put("idLabel", task.getPriority().getTrelloLabelId());

            builder = UriComponentsBuilder.fromUriString(URLPOSTLABEL);
            restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.POST, requestEntity, Void.class);

        }

    }




    public void pushTrelloCards(List<Task> taskList){
        updateListFromCards(taskList);
        updateLabelsFromCards(taskList);
    }



    private void updateListFromCard(Task task) {

        String trellokey = TrelloConstant.KEY;
        String trellotoken = TrelloConstant.TOKEN;
        String URLLISTUPDATE = "https://api.trello.com/1/cards/{cardID}?&idList={idList}&key={trellokey}&token={trellotoken}";

        Map<String, String> urlParams = new HashMap<>();

        urlParams.put("trellokey", trellokey);
        urlParams.put("trellotoken", trellotoken);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        urlParams.put("cardID", task.getId());
        urlParams.put("idList", task.getStatus().getTrelloListId());
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URLLISTUPDATE);
        restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.PUT, requestEntity, Void.class);

    }




    private void updateLabelFromCard(Task task) {
        String trellokey = TrelloConstant.KEY;
        String trellotoken = TrelloConstant.TOKEN;
        String URL = "https://api.trello.com/1/cards/{idCard}?key={trellokey}&token={trellotoken}";

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("trellokey", trellokey);
        urlParams.put("trellotoken", trellotoken);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);


        urlParams.put("idCard", task.getId());
        String response = restTemplate.getForObject(URL, String.class, urlParams);

        JSONObject object = new JSONObject(response);

        JSONArray labelsArray = new JSONArray(object.get("labels").toString());
        JSONObject label = new JSONObject(labelsArray.get(0).toString());
        String idLabel = label.getString("id");
        urlParams.put("idLabel", idLabel);
        String URLDELETE = "https://api.trello.com/1/cards/{idCard}/idLabels/{idLabel}?key={trellokey}&token={trellotoken}";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URLDELETE);
        restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.DELETE, requestEntity, Void.class);

        String URLPOSTLABEL = "https://api.trello.com/1/cards/{idCard}/idLabels?value={idLabel}&key={trellokey}&token={trellotoken}";
        urlParams.put("idLabel", task.getPriority().getTrelloLabelId());

        builder = UriComponentsBuilder.fromUriString(URLPOSTLABEL);
        restTemplate.exchange(builder.buildAndExpand(urlParams).toUri(), HttpMethod.POST, requestEntity, Void.class);


    }




    public void updateSingleCard(Task task){
        updateListFromCard(task);
        updateLabelFromCard(task);
    }




}
