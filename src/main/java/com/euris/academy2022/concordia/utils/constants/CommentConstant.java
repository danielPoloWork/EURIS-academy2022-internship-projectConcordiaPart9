package com.euris.academy2022.concordia.utils.constants;

public interface CommentConstant {
    String TABLE_NAME = "Comment";

    String COLUMN_UUID = "uuid";
    String COLUMN_ID_TRELLO_COMMENT = "idTrelloComment";
    String COLUMN_ID_TASK = "idTask";
    String COLUMN_ID_TRELLO_MEMBER = "idTrelloMember";
    String COLUMN_UUID_MEMBER = "uuidMember";
    String COLUMN_TEXT = "text";
    String COLUMN_DATE_CREATION = "dateCreation";
    String COLUMN_DATE_UPDATE = "dateUpdate";



    String PK = "pkComment";
    String FK_MEMBER = "fkCommentMember";
    String FK_TASK = "fkCommentTask";
}
