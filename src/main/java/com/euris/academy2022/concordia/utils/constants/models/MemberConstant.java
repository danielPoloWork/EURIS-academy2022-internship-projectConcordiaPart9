package com.euris.academy2022.concordia.utils.constants.models;

public interface MemberConstant {
    String TABLE_NAME = "Member";

    String COLUMN_UUID = "uuid";
    String COLUMN_ID_TRELLO_MEMBER = "idTrelloMember";
    String COLUMN_ROLE = "role";
    String COLUMN_USERNAME = "username";
    String COLUMN_PASSWORD = "password";
    String COLUMN_FIRST_NAME = "firstName";
    String COLUMN_LAST_NAME = "lastName";
    String COLUMN_DATE_CREATION = "dateCreation";
    String COLUMN_DATE_UPDATE = "dateUpdate";

    String PK = "pkMember";

    String JOIN_MAPPED_BY = "member";
}
