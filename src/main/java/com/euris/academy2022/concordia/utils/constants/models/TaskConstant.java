package com.euris.academy2022.concordia.utils.constants.models;

public interface TaskConstant {
    String TABLE_NAME = "Task";

    String COLUMN_ID = "id";
    String COLUMN_TITLE = "title";
    String COLUMN_DESCRIPTION = "description";
    String COLUMN_PRIORITY = "priority";
    String COLUMN_STATUS = "status";
    String COLUMN_DEADLINE = "deadline";
    String COLUMN_DATE_CREATION = "dateCreation";
    String COLUMN_DATE_UPDATE = "dateUpdate";

    String PK = "pkTask";

    String JOIN_MAPPED_BY = "task";
}
