package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Task;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TrelloCardDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TrelloPushService {

    public void updateComment(String idAction, String idCard, String text);

    public void postComment(String idCard, String text);

    public void deleteComment(String idCard, String idAction);

    public void pushTrelloCards(List<Task> taskList);

    public void updateSingleCard(Task task);

}
