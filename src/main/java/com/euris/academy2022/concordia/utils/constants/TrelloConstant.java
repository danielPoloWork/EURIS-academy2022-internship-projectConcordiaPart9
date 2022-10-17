package com.euris.academy2022.concordia.utils.constants;

public interface TrelloConstant {

    String IP_1 = "185.166.143.24";
    String IP_2 = "185.166.143.25";
    String IP_3 = "185.166.143.26";

    String CARD = "card";
    String DATA = "data";
    String DATE = "date";
    String DATE_LAST_EDITED = "dateLastEdited";
    String DATE_LAST_ACTIVITY = "dateLastActivity";
    String DESC = "desc";
    String DUE = "due";
    String FULL_NAME = "fullName";
    String ID = "id";
    String ID_BOARD = "idBoard";
    String ID_CARD = "idCard";
    String ID_COMMENT = "idComment";
    String ID_LABEL = "idLabel";
    String ID_LABELS = "idLabels";
    String LABELS = "labels";
    String ID_LIST = "idList";
    String MEMBER_ID = "memberId";
    String ID_MEMBER = "idMember";
    String MEMBER_CREATOR = "memberCreator";
    String ID_MEMBER_CREATOR = "idMemberCreator";
    String NAME = "name";
    String USERNAME = "username";
    String TEXT = "text";
    String TYPE = "type";

    String ID_BOARD_VALUE = "6331a6227f9f991a315ca097";

    String MEMBER_CRS_ID = "633c1268379fad02799a6eb3";
    String MEMBER_CRS_FULL_NAME = "Concordia Research Station";
    String MEMBER_CRS_USERNAME = "concordiaresearchstation";

    String KEY = "key";
    String TOKEN = "token";

    String KEY_VALUE = "5c9e2406452c6b8b303c7362e444dd3f";
    String TOKEN_VALUE = "3999110dcd7da4a6ebf75b0f67c8bb78b17e9b2706f65e0e33bb5fda8cb8810f";

    String URL_API_GET_MEMBERS_BY_ID_BOARD = "https://api.trello.com/1/boards/{idBoard}/members?key={key}&token={token}";
    String URL_API_GET_CARDS_BY_ID_BOARD = "https://api.trello.com/1/boards/{idBoard}/cards?key={key}&token={token}";
    String URL_API_GET_CARDS_BY_ID_LIST = "https://api.trello.com/1/lists/{idList}/cards?key={key}&token={token}";
    String URL_API_GET_CARD_BY_ID_CARD = "https://api.trello.com/1/cards/{idCard}?key={key}&token={token}";
    String URL_API_GET_COMMENTS_BY_ID_CARD = "https://api.trello.com/1/cards/{idCard}/actions?filter=commentCard&key={key}&token={token}";
    String URL_API_GET_COMMENTS_BY_ID_COMMENT = "https://api.trello.com/1/actions/{idComment}?key={key}&token={token}";
    String URL_API_GET_MEMBER_FULL_NAME_BY_MEMBER_ID = "https://api.trello.com/1/members/{idMember}?key={key}&token={token}";
    String URL_API_GET_CARD_BY_ID = "https://api.trello.com/1/cards/{idCard}?key={key}&token={token}";
    String URL_API_POST_COMMENT_BY_ID_CARD = "https://api.trello.com/1/cards/{idCard}/actions/comments?text={text}&key={key}&token={token}";
    String URL_API_POST_LABEL_ON_CARD_BY_ID_CARD = "https://api.trello.com/1/cards/{idCard}/idLabels?value={idLabel}&key={key}&token={token}";
    String URL_API_PUT_COMMENT_BY_ID_CARD_AND_ID_COMMENT_AND_TEXT = "https://api.trello.com/1/cards/{idCard}/actions/{idComment}/comments?text={text}&key={key}&token={token}";
    String URL_API_DELETE_COMMENT_BY_ID_CARD_AND_ID_COMMENT = "https://api.trello.com/1/cards/{idCard}/actions/{idComment}/comments?&key={key}&token={token}";
    String URL_API_DELETE_LABEL_ON_CARD_BY_ID_CARD = "https://api.trello.com/1/cards/{idCard}/idLabels/{idLabel}?key={key}&token={token}";
    String URL_API_PUT_ID_LIST_ON_CARD_BY_ID_CARD = "https://api.trello.com/1/cards/{idCard}?&idList={idList}&key={key}&token={token}";
    String URL_BOARD = "https://trello.com/b/13aqL2ga/concordia";
}
