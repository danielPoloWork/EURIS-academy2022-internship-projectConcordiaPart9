package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCardDto;
import org.springframework.stereotype.Service;

import java.util.List;
public interface TrelloPushService {

    void updateComment(String idAction, String idCard, String text);

    void postComment(String idCard, String text);

    void deleteComment(String idCard, String idAction);

    void pushTrelloCards(List<Task> taskList);

    void updateSingleCard(Task task);

}
