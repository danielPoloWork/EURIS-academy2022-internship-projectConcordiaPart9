package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.TrelloCardDto;

import java.util.List;

public interface TrelloPushService {

    public void updateComment(String idAction, String idCard, String text);

    public void postComment(String idCard, String text);

    public void deleteComment(String idCard, String idAction);

    public void pushTrelloCards(List<TrelloCardDto> cardsList);

    public void updateSingleCard(TrelloCardDto card);

}
