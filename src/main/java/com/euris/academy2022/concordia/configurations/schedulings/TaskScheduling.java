package com.euris.academy2022.concordia.configurations.schedulings;

import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCardService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.*;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.tasks.TaskPostRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.tasks.TaskPutRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCardDto;
import com.euris.academy2022.concordia.utils.TimeUtil;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TaskScheduling {
    public static void fetchAndPull(TrelloCardService trelloCardService, TaskService taskService, TaskStatus trelloListId) {
        try {
            ResponseDto<List<TrelloCardDto>> response = trelloCardService.getCardsByIdList(trelloListId.getTrelloListId());

            for (TrelloCardDto card : response.getBody()) {
                ResponseDto<TaskDto> taskFound = taskService.getByIdTrelloTask(card.getId());

                if (Optional.ofNullable(taskFound.getBody()).isEmpty()) {
                    TaskPostRequest taskNew = TaskPostRequest.builder()
                            .id(card.getId())
                            .title(card.getName())
                            .description(card.getDesc())
                            .priority(TaskPriority.getEnumByLabelId(card.getIdLabel()))
                            .status(TaskStatus.getEnumByListId(card.getIdList()))
                            .deadLine(TimeUtil.parseDue(card.getDue()))
                            .dateCreation(LocalDateTime.now())
                            .dateUpdate(TimeUtil.parseToLocalDateTime(card.getDateLastActivity()))
                            .build();

                    ResponseDto<TaskDto> taskCreated = taskService.insertFromTrello(taskNew.toModel());

                    System.out.printf("%s  [pullCard      ] executed at %s  INFO : %s : %s → %s\n",
                            Thread.currentThread().getName(),
                            new Date(),
                            card.getId(),
                            HttpResponseType.NOT_FOUND.getLabel(),
                            taskCreated.getHttpResponse().getLabel());
                } else {
                    if (TimeUtil.parseToLocalDateTime(card.getDateLastActivity()).isAfter(taskFound.getBody().getDateUpdate().truncatedTo(ChronoUnit.SECONDS))) {

                        TaskPutRequest taskOld = TaskPutRequest.builder()
                                .id(taskFound.getBody().getId())
                                .title(card.getName())
                                .description(card.getDesc())
                                .priority(TaskPriority.getEnumByLabelId(card.getIdLabel()))
                                .status(TaskStatus.getEnumByListId(card.getIdList()))
                                .deadLine(TimeUtil.parseDue(card.getDue()))
                                .dateUpdate(TimeUtil.parseToLocalDateTime(card.getDateLastActivity()))
                                .build();

                        ResponseDto<TaskDto> taskUpdated = taskService.updateFromTrello(taskOld.toModel());

                        System.out.printf("%s  [pullCard      ] executed at %s  INFO : %s : %s → %s\n",
                                Thread.currentThread().getName(),
                                new Date(),
                                card.getId(),
                                HttpResponseType.FOUND.getLabel(),
                                taskUpdated.getHttpResponse().getLabel());
                    } else {
                        System.out.printf("%s  [pullCard      ] executed at %s  INFO : %s : %s → %s\n",
                                Thread.currentThread().getName(),
                                new Date(),
                                card.getId(),
                                HttpResponseType.FOUND.getLabel(),
                                HttpResponseType.OK.getLabel());
                    }
                }
            }
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.printf("%s  [fetchTrello   ] executed at %s  WARN : INTERRUPTED MANUALLY\n",
                    Thread.currentThread().getName(),
                    new Date());
        } catch (NullPointerException e) {
            System.out.printf("%s  [fetchTrello   ] executed at %s  WARN : RECORD IS NULL\n",
                    Thread.currentThread().getName(),
                    new Date());
        }
    }
}
