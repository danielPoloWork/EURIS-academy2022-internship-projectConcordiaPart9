package com.euris.academy2022.concordia.utils.constants.models;

public interface AssigneeConstant {
    String TABLE_NAME = "Assignee";

    String COLUMN_UUID = "uuid";
    String COLUMN_UUID_MEMBER = "uuidMember";
    String COLUMN_ID_TASK = "idTask";
    String COLUMN_DATE_CREATION = "dateCreation";

    String PK = "pkAssignee";
    String FK_MEMBER = "fkAssigneeMember";
    String FK_TASK = "fkAssigneeTask";
}
