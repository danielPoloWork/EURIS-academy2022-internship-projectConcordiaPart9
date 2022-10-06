package com.euris.academy2022.concordia.utils;

import com.euris.academy2022.concordia.utils.enums.TaskPriority;

public class TaskPriorityUtil {

    public static TaskPriority getEnumByLabelId(String trelloLabelId) {
        return TaskPriority.HIGH.getTrelloLabelId().equals(trelloLabelId) ? TaskPriority.HIGH
                : TaskPriority.EXPIRING.getTrelloLabelId().equals(trelloLabelId) ? TaskPriority.EXPIRING
                : TaskPriority.MEDIUM.getTrelloLabelId().equals(trelloLabelId) ? TaskPriority.MEDIUM
                : TaskPriority.LOW.getTrelloLabelId().equals(trelloLabelId) ? TaskPriority.LOW
                : TaskPriority.DONE.getTrelloLabelId().equals(trelloLabelId) ? TaskPriority.DONE
                : TaskPriority.ARCHIVED.getTrelloLabelId().equals(trelloLabelId) ? TaskPriority.ARCHIVED
                : null;
    }
}