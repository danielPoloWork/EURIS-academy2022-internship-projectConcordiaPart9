package com.euris.academy2022.concordia.configurations.schedulings;

import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCardService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloLabelService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.*;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.tasks.TaskPostRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.tasks.TaskPutRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCardDto;
import com.euris.academy2022.concordia.utils.TimeUtil;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TaskScheduling {

    // download da trello
    // controllo se il task esiste in DB
    // se non esiste lo inserisco su local
    // se esiste
    // contorllo se la data su trello è più recente
    // se è più recente faccio update su local
    // se la data è uguale ritorno OK
    // controllo se il DB local ha un task con trello id che non esiste su trello
    // se esiste in local va eliminato
    // se non esiste neanche in local ritorno OK
    public static void fetchAndPull(TrelloCardService trelloCardService, TaskService taskService, TaskStatus trelloListId) {
        try {
            ResponseDto<List<TrelloCardDto>> trelloCardList = trelloCardService.getCardsByIdList(trelloListId.getTrelloListId());

            for (TrelloCardDto trelloCard : trelloCardList.getBody()) {
                ResponseDto<TaskDto> taskFound = taskService.getByIdTrelloTask(trelloCard.getId());

                if (Optional.ofNullable(taskFound.getBody()).isEmpty()) {
                    TaskPostRequest taskNew = TaskPostRequest.builder()
                            .id(trelloCard.getId())
                            .title(trelloCard.getName())
                            .description(trelloCard.getDesc())
                            .priority(TaskPriority.getEnumByLabelId(trelloCard.getIdLabel()))
                            .status(TaskStatus.getEnumByListId(trelloCard.getIdList()))
                            .deadLine(TimeUtil.parseDue(trelloCard.getDue()))
                            .dateCreation(LocalDateTime.now())
                            .dateUpdate(TimeUtil.parseToLocalDateTime(trelloCard.getDateLastActivity()))
                            .build();

                    ResponseDto<TaskDto> taskCreated = taskService.insertFromTrello(taskNew.toModel());

                    System.out.printf("%s  [pullCard      ] executed at %s  INFO : %s : %s → %s\n",
                            Thread.currentThread().getName(),
                            new Date(),
                            trelloCard.getId(),
                            HttpResponseType.NOT_FOUND.getLabel(),
                            taskCreated.getHttpResponse().getLabel());
                } else {
                    if (TimeUtil.parseToLocalDateTime(trelloCard.getDateLastActivity()).isAfter(taskFound.getBody().getDateUpdate().truncatedTo(ChronoUnit.SECONDS))) {

                        TaskPutRequest taskOld = TaskPutRequest.builder()
                                .id(taskFound.getBody().getId())
                                .title(trelloCard.getName())
                                .description(trelloCard.getDesc())
                                .priority(TaskPriority.getEnumByLabelId(trelloCard.getIdLabel()))
                                .status(TaskStatus.getEnumByListId(trelloCard.getIdList()))
                                .deadLine(TimeUtil.parseDue(trelloCard.getDue()))
                                .dateUpdate(TimeUtil.parseToLocalDateTime(trelloCard.getDateLastActivity()))
                                .build();

                        ResponseDto<TaskDto> taskUpdated = taskService.updateFromTrello(taskOld.toModel());

                        System.out.printf("%s  [pullCard      ] executed at %s  INFO : %s : %s → %s\n",
                                Thread.currentThread().getName(),
                                new Date(),
                                trelloCard.getId(),
                                HttpResponseType.FOUND.getLabel(),
                                taskUpdated.getHttpResponse().getLabel());
                    } else {
                        System.out.printf("%s  [pullCard      ] executed at %s  INFO : %s : %s → %s\n",
                                Thread.currentThread().getName(),
                                new Date(),
                                trelloCard.getId(),
                                HttpResponseType.FOUND.getLabel(),
                                HttpResponseType.OK.getLabel());
                    }
                }
            }
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.printf("%s  [fetchTrello   ] executed at %s  WARN : **********TaskScheduling : INTERRUPTED MANUALLY\n",
                    Thread.currentThread().getName(),
                    new Date());
        } catch (NullPointerException e) {
            System.out.printf("%s  [fetchTrello   ] executed at %s  WARN : **********TaskScheduling : NULL POINTER\n",
                    Thread.currentThread().getName(),
                    new Date());
        }
    }


    // contorllo l'id della lista di trello
    // se la lista di trello è diversa devo aggiornare il task su trello
    // se è uguale ritorno OK
    /* devo fare questa operazioen per gestire i passaggi da una lista all'alltra*/

    // controllo se la label è diversa
    // se è diversa aggiorno il task su trello
    // se è uguale ritorno OK

    // Upload su trello
    public static void fetchAndPush(TrelloCardService trelloCardService, TrelloLabelService trelloLabelService, TaskService taskService) {
        try {
            ResponseDto<List<TaskDto>> taskList = taskService.getAll();
            forEachTaskFetchAndPull(taskList, trelloCardService, trelloLabelService);
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.printf("%s  [fetchConcordia] executed at %s  WARN : **********TaskScheduling : INTERRUPTED MANUALLY\n",
                    Thread.currentThread().getName(),
                    new Date());
        } catch (NullPointerException e) {
            System.out.printf("%s  [fetchConcordia] executed at %s  WARN : **********TaskScheduling : NULL POINTER\n",
                    Thread.currentThread().getName(),
                    new Date());
        }
    }

    private static void forEachTaskFetchAndPull(ResponseDto<List<TaskDto>> taskList, TrelloCardService trelloCardService, TrelloLabelService trelloLabelService) {
        for (TaskDto task : taskList.getBody()) {
            ResponseDto<TrelloCardDto> cardFound = trelloCardService.getCardByIdCard(task.getId());
            ifFoundExecuteChangeListAndChangeLabel(cardFound, task, trelloCardService, trelloLabelService);
        }
    }

    private static void ifFoundExecuteChangeListAndChangeLabel(ResponseDto<TrelloCardDto> cardFound, TaskDto task, TrelloCardService trelloCardService, TrelloLabelService trelloLabelService) {
        if (Optional.ofNullable(cardFound.getBody()).isEmpty()) {
            ifNeededChangeList(cardFound, task, trelloCardService);
            ifNeededChangeLabel(cardFound, task, trelloLabelService);
        } else {
            System.out.printf("%s  [pushCard      ] executed at %s  INFO : %s : %s\n",
                    Thread.currentThread().getName(),
                    new Date(),
                    task.getId(),
                    HttpResponseType.NOT_FOUND.getLabel());
        }
    }

    private static void ifNeededChangeList(ResponseDto<TrelloCardDto> cardFound, TaskDto task, TrelloCardService trelloCardService) {
        if (!cardFound.getBody().getIdList().equals(task.getId())) {
            ResponseDto<ResponseEntity<String>> switched = trelloCardService.changeListByIdCardAndIdList(task.getId(), task.getStatus().getTrelloListId());
            if (switched.getBody().getStatusCode().is2xxSuccessful()) {
                System.out.printf("%s  [pushCard      ] executed at %s  INFO : %s : %s → %s\n",
                        Thread.currentThread().getName(),
                        new Date(),
                        task.getId(),
                        HttpResponseType.FOUND.getLabel(),
                        HttpResponseType.UPDATED.getLabel());
            } else {
                System.out.printf("%s  [pushCard      ] executed at %s  INFO : %s : %s → %s\n",
                        Thread.currentThread().getName(),
                        new Date(),
                        task.getId(),
                        HttpResponseType.FAILED.getLabel(),
                        HttpResponseType.NOT_UPDATED.getLabel());
            }
        } else {
            System.out.printf("%s  [pushCard      ] executed at %s  INFO : %s : %s → %s\n",
                    Thread.currentThread().getName(),
                    new Date(),
                    task.getId(),
                    HttpResponseType.FOUND.getLabel(),
                    HttpResponseType.OK.getLabel());
        }
    }

    private static void ifNeededChangeLabel(ResponseDto<TrelloCardDto> cardFound, TaskDto task, TrelloLabelService trelloLabelService) {
        if (!cardFound.getBody().getIdLabel().equals(task.getPriority().getTrelloLabelId())) {
            ResponseDto<ResponseEntity<String>> removed = trelloLabelService.removeLabelFromCardByIdCardAndIdLabel(task.getId(), cardFound.getBody().getIdLabel());
            if (removed.getBody().getStatusCode().is2xxSuccessful()) {
                System.out.printf("%s  [pushCard      ] executed at %s  INFO : %s : %s → %s\n",
                        Thread.currentThread().getName(),
                        new Date(),
                        cardFound.getBody().getIdLabel(),
                        HttpResponseType.FOUND.getLabel(),
                        HttpResponseType.DELETED.getLabel());
                ResponseDto<ResponseEntity<String>> added = trelloLabelService.addLabelOnCardByIdCardAndIdLabel(task.getId(), task.getPriority().getTrelloLabelId());
                if (added.getBody().getStatusCode().is2xxSuccessful()) {
                    System.out.printf("%s  [pushCard      ] executed at %s  INFO : %s : NEW LABEL → %s\n",
                            Thread.currentThread().getName(),
                            new Date(),
                            task.getId(),
                            HttpResponseType.UPDATED.getLabel());
                } else {
                    System.out.printf("%s  [pushCard      ] executed at %s  INFO : %s : NEW LABEL → %s\n",
                            Thread.currentThread().getName(),
                            new Date(),
                            task.getId(),
                            HttpResponseType.NOT_UPDATED.getLabel());
                }
            } else {
                System.out.printf("%s  [pushCard      ] executed at %s  INFO : %s : %s → %s\n",
                        Thread.currentThread().getName(),
                        new Date(),
                        cardFound.getBody().getIdLabel(),
                        HttpResponseType.FOUND.getLabel(),
                        HttpResponseType.NOT_DELETED.getLabel());
            }
        } else {
            System.out.printf("%s  [pushCard      ] executed at %s  INFO : %s : %s → %s\n",
                    Thread.currentThread().getName(),
                    new Date(),
                    cardFound.getBody().getIdLabel(),
                    HttpResponseType.FOUND.getLabel(),
                    HttpResponseType.OK.getLabel());
        }
    }
}
