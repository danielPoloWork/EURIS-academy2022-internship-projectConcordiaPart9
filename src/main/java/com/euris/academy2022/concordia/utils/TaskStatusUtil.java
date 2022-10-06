package com.euris.academy2022.concordia.utils;

import com.euris.academy2022.concordia.utils.enums.TaskStatus;

public class TaskStatusUtil {

    public static TaskStatus getEnumByListId(String trelloListId) {
        return TaskStatus.TO_DO.getTrelloListId().equals(trelloListId) ? TaskStatus.TO_DO
                : TaskStatus.IN_PROGRESS.getTrelloListId().equals(trelloListId) ? TaskStatus.IN_PROGRESS
                : TaskStatus.COMPLETED.getTrelloListId().equals(trelloListId) ? TaskStatus.COMPLETED
                : null;
    }
}